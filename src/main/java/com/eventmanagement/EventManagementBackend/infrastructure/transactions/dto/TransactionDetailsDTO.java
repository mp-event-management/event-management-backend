package com.eventmanagement.EventManagementBackend.infrastructure.transactions.dto;

import com.eventmanagement.EventManagementBackend.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailsDTO {
    private Integer transactionId;
    private Integer eventId;
    private Integer customerId;
    private String customerName;
    private String eventName;
    private OffsetDateTime eventStartDate;
    private OffsetDateTime eventEndDate;
    private String eventCityLocation;
    private String eventAddress;
    private Integer promotionId;
    private Integer ticketQuantity;
    private BigDecimal ticketPrice;
    private BigDecimal discountPercentage;
    private BigDecimal totalDiscount;
    private BigDecimal referralPointsUsed;
    private BigDecimal finalPrice;
    private String paymentStatus;
    private String invoiceCode;

    public TransactionDetailsDTO mapToDTO(Transaction transaction) {
        TransactionDetailsDTO transactionDetailsDTO = new TransactionDetailsDTO();

        transactionDetailsDTO.setTransactionId(transaction.getId());
        transactionDetailsDTO.setEventId(transaction.getEvent().getEventId());
        transactionDetailsDTO.setCustomerId(transaction.getCustomer().getUserId());
        transactionDetailsDTO.setCustomerName(transaction.getCustomer().getName());
        transactionDetailsDTO.setEventName(transaction.getEvent().getTitle());
        transactionDetailsDTO.setEventStartDate(transaction.getEvent().getStartDate());
        transactionDetailsDTO.setEventEndDate(transaction.getEvent().getEndDate());
        transactionDetailsDTO.setEventCityLocation(transaction.getEvent().getCity().getName());
        transactionDetailsDTO.setEventAddress(transaction.getEvent().getAddress());
        transactionDetailsDTO.setPromotionId(transaction.getPromotion().getPromotionId());
        transactionDetailsDTO.setTicketQuantity(transaction.getTicketQuantity());
        transactionDetailsDTO.setTicketPrice(transaction.getTicketPrice());
        transactionDetailsDTO.setDiscountPercentage(transaction.getDiscountPercentage());
        transactionDetailsDTO.setTotalDiscount(transaction.getTotalDiscount());
        transactionDetailsDTO.setReferralPointsUsed(transaction.getReferralPointsUsed());
        transactionDetailsDTO.setFinalPrice(transaction.getFinalPrice());
        transactionDetailsDTO.setPaymentStatus(transaction.getPaymentStatus());
        transactionDetailsDTO.setInvoiceCode(transaction.getInvoiceCode());

        return transactionDetailsDTO;
    }
}
