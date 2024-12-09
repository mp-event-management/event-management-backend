package com.eventmanagement.EventManagementBackend.usecase.transactions.impl;

import com.eventmanagement.EventManagementBackend.common.exceptions.DataNotFoundException;
import com.eventmanagement.EventManagementBackend.entity.*;
import com.eventmanagement.EventManagementBackend.infrastructure.events.repository.EventsRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.promotion.repository.PromotionRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.referralPoints.repository.ReferralPointsRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.tickets.repository.TicketsRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.transactions.dto.TransactionRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.transactions.dto.TransactionResponseDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.transactions.repository.TransactionRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.transactions.utils.Utils;
import com.eventmanagement.EventManagementBackend.infrastructure.users.repository.UsersAccountRepository;
import com.eventmanagement.EventManagementBackend.usecase.transactions.TransactionsPublicUsecase;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TransactionsPublicUsecaseImpl implements TransactionsPublicUsecase {

    private final EventsRepository eventsRepository;
    private final TransactionRepository transactionRepository;
    private final UsersAccountRepository usersAccountRepository;
    private final PromotionRepository promotionRepository;
    private final ReferralPointsRepository referralPointsRepository;
    private final TicketsRepository ticketsRepository;


    public TransactionsPublicUsecaseImpl(EventsRepository eventsRepository, TransactionRepository transactionRepository, UsersAccountRepository usersAccountRepository, PromotionRepository promotionRepository, ReferralPointsRepository referralPointsRepository, TicketsRepository ticketsRepository) {
        this.eventsRepository = eventsRepository;
        this.transactionRepository = transactionRepository;
        this.usersAccountRepository = usersAccountRepository;
        this.promotionRepository = promotionRepository;
        this.referralPointsRepository = referralPointsRepository;
        this.ticketsRepository = ticketsRepository;
    }

    @Transactional
    @Override
    public TransactionResponseDTO createTransaction(TransactionRequestDTO req) {
        // Check if the user exist
        UsersAccount user = usersAccountRepository.findById(req.getCustomerId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        // Validate user role (only customer)
        if (!"CUSTOMER".equals(user.getRole().getName())) {
            throw new DataNotFoundException("Only customer can buy tickets");
        }

        // Check if the event exists
        Event event = eventsRepository.findById(req.getEventId())
                .orElseThrow(() -> new DataNotFoundException("Event not found"));

        // check if the event still ongoing or not done
        if (event.getEndDate().isBefore(OffsetDateTime.now())) {
            throw new IllegalStateException("Event already completed");
        }

        // check if the tickets still available
        if (event.getAvailableTicket() <= 0) {
            throw new IllegalStateException("Ticket already sold out");
        }

        // Check if booking tickets are still available in availableTicket
        if (event.getAvailableTicket() < req.getTicketQuantity()) {
            throw new IllegalStateException("Available tickets are not enough for your order, try to decrease the quantity");
        }

        // DEFINED VARIABLES
        BigDecimal ticketPrice = event.getTicketPrice();
        Promotion promoCode = null;
        BigDecimal referralPointsApplied = BigDecimal.ZERO;
        BigDecimal promoDiscountPercentage = BigDecimal.ZERO;


        // CASE IF CUSTOMER USING PROMOTION CODE
        if (req.getPromoCode() != null && !req.getPromoCode().trim().isEmpty()) {

            // Check if promotion code exist
            promoCode = promotionRepository.findByPromotionCode(req.getPromoCode())
                    .orElseThrow(() -> new DataNotFoundException("Promotion code not found"));

            // Check if promotion code is related to the event
            if (!promoCode.getEvent().getEventId().equals(event.getEventId())) {
                throw new DataNotFoundException("Promotion code is not valid for this event");
            }

            // Check if promotion code start and end dates are within the event's start and end dates
            if (promoCode.getStartDate().isBefore(event.getStartDate()) || promoCode.getStartDate().isAfter(event.getEndDate())) {
                throw new IllegalStateException("Promotion code start date is not within the event's valid date range");
            }

            // Check if the promotion's end date is not later than the event's end date
            if (promoCode.getEndDate().isAfter(event.getEndDate())) {
                throw new IllegalStateException("Promotion code end date cannot be later than the event's end date");
            }

            // Check if promotion code still valid
            OffsetDateTime now = OffsetDateTime.now();
            if (promoCode.getStartDate().isBefore(event.getStartDate()) || promoCode.getEndDate().isAfter(event.getEndDate())) {
                throw new IllegalStateException("Promotion code's date range is outside of the event's date range");
            }

            // Check promotion code type 'DATEBASED' or 'VOUCHER'
            if ("DATEBASED".equals(promoCode.getPromotionType())) {
                promoDiscountPercentage = promoCode.getDiscountPercentage();

            } else if ("VOUCHER".equals(promoCode.getPromotionType())) {
                if (promoCode.getAvailableUses() <= 0)
                    throw new IllegalStateException("Promotion code usage limit reached");

                promoDiscountPercentage = promoCode.getDiscountPercentage();
                promoCode.setAvailableUses(promoCode.getAvailableUses() - 1);
                promotionRepository.save(promoCode);
            } else {
                throw new IllegalStateException("Invalid promotion code");
            }
        }

        BigDecimal totalTicketAmount = ticketPrice.multiply(BigDecimal.valueOf(req.getTicketQuantity())); // Total ticket amount
        BigDecimal discountAmount = totalTicketAmount.multiply(promoDiscountPercentage); // Apply promo code discount
        totalTicketAmount = totalTicketAmount.subtract(discountAmount); // Subtract discount from total amount
        Integer promotionId = promoCode != null ? promoCode.getPromotionId() : null; // IF promoCode is null, do not set the promotionId in the response

        // CASE IF CUSTOMER USING REFERRALS POINTS TO PAY
        if (req.getIsUsePoints()) {
            List<ReferralPoint> validReferralPoints = referralPointsRepository.findByUser_UserId(user.getUserId()); // Required type: Iterable <java. lang. Integer> provided Integer

            // Filter referral points sort by ascending expired date
            List<ReferralPoint> sortedReferralPoints = validReferralPoints.stream()
                    .filter(rp -> rp.getExpiryDate().isAfter(OffsetDateTime.now())) // only valid points
                    .sorted(Comparator.comparing(ReferralPoint::getExpiryDate))
                    .toList();

            // Check if no valid points are found
            if (sortedReferralPoints.isEmpty()) throw new IllegalStateException("No valid referral points available for use");

            // Iterate over sorted points and apply to the total amount
            for (ReferralPoint referralPoint : sortedReferralPoints) {
                // Calculate the points value and check if it can be applied
                BigDecimal pointsValue = BigDecimal.valueOf(referralPoint.getPoints());

                // if points value can fully cover the remaining ticket price
                if (totalTicketAmount.compareTo(pointsValue) <= 0) {
                    referralPointsApplied = referralPointsApplied.add(totalTicketAmount);
                    referralPoint.setPoints(referralPoint.getPoints() - totalTicketAmount.intValue());
                    totalTicketAmount = BigDecimal.ZERO;
                    break;
                } else {
                    referralPointsApplied = referralPointsApplied.add(pointsValue);
                    totalTicketAmount = totalTicketAmount.subtract(pointsValue);
                    referralPoint.setPoints(0);
                }

                // Save updated referral points and remove fully used points
                referralPointsRepository.saveAll(sortedReferralPoints);
            }
        }

        // Check if the final price is zero or negative, and handle it
        if (ticketPrice.compareTo(BigDecimal.ZERO) < 0) {
            ticketPrice = BigDecimal.ZERO;
        }

        // Process transaction and handle payment
        boolean paymentSuccessful = Utils.processPayment(ticketPrice);

        if (!paymentSuccessful) {
            throw new IllegalStateException("Payment processing failed");
//            return new TransactionResponseDTO(
//                    null,
//                    event.getEventId(),
//                    user.getUserId(),
//                    null,
//                    1,
//                    ticketPrice,
//                    promotionDiscount.add(referralPointsApplied),
//                    promotionDiscount.add(referralPointsApplied),
//                    referralPointsApplied,
//                    ticketPrice,
//                    "FAILED",
//                    null);
        }

        // Create the transaction record
        Transaction transaction = new Transaction();
        transaction.setEvent(event);
        transaction.setCustomer(user);
        transaction.setTicketQuantity(req.getTicketQuantity());
        transaction.setTicketPrice(ticketPrice);
        transaction.setDiscountPercentage(promoDiscountPercentage);
        transaction.setTotalDiscount(discountAmount);
        transaction.setReferralPointsUsed(referralPointsApplied);
        transaction.setFinalPrice(totalTicketAmount);
        transaction.setInvoiceCode(Utils.generateInvoiceCode());
        transaction.setPaymentStatus("SUCCESS");
        transactionRepository.save(transaction);

        // Generate ticket for customer
        List<Ticket> tickets = new ArrayList<>();

        for (int i = 0; i < transaction.getTicketQuantity(); i++) {
            Ticket ticket = new Ticket();
            ticket.setTransaction(transaction);
            ticket.setCustomer(transaction.getCustomer());
            ticket.setEvent(transaction.getEvent());
            ticket.setStatus("VALID");
            ticket.setIssuedate(OffsetDateTime.now());
            ticket.setValidUntil(transaction.getEvent().getEndDate());

            tickets.add(ticket);
        }
        ticketsRepository.saveAll(tickets);

        // Reduce ticket available
        event.setAvailableTicket(event.getAvailableTicket() - req.getTicketQuantity());
        eventsRepository.save(event);

        return new TransactionResponseDTO(
                transaction.getId(),
                event.getEventId(),
                user.getUserId(),
                promotionId,
                req.getTicketQuantity(),
                ticketPrice,
                promoDiscountPercentage,
                discountAmount,
                referralPointsApplied,
                totalTicketAmount,
                "SUCCESS",
                transaction.getInvoiceCode()
        );
    }
}
