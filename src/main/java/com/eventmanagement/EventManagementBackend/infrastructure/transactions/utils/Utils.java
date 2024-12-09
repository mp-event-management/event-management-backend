package com.eventmanagement.EventManagementBackend.infrastructure.transactions.utils;

import com.eventmanagement.EventManagementBackend.entity.Event;
import com.eventmanagement.EventManagementBackend.entity.Promotion;
import com.eventmanagement.EventManagementBackend.entity.ReferralPoint;
import com.eventmanagement.EventManagementBackend.entity.UsersAccount;
import com.eventmanagement.EventManagementBackend.infrastructure.promotion.repository.PromotionRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.referralPoints.repository.ReferralPointsRepository;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Utils {

    // Method to apply valid referral points to the ticket price
    public static BigDecimal applyReferralPoints(ReferralPointsRepository referralPoints, UsersAccount user, BigDecimal ticketPrice) {
        // Fetch all valid referral points that haven't expired
        List<ReferralPoint> validReferralPoints = referralPoints.findByUser_UserIdAndExpiryDateAfter(user.getUserId(), OffsetDateTime.now());

        if (validReferralPoints.isEmpty()) {
            return BigDecimal.ZERO;
        }

        // Calculate the total discount from valid referral points
        BigDecimal totalDiscount = BigDecimal.ZERO;
        BigDecimal remainingPrice = ticketPrice;

        // Sort the referral points by the expiry date (closest expiry date first)
        validReferralPoints.sort(Comparator.comparing(ReferralPoint::getExpiryDate));

        for (ReferralPoint referralPoint : validReferralPoints) {
            if (remainingPrice.compareTo(BigDecimal.ZERO) <= 0) {
                break; // stop if the ticket price is fully covered
            }

            // Apply the points from the referral point
            BigDecimal pointsToApply = BigDecimal.valueOf(referralPoint.getPoints());

            if (pointsToApply.compareTo(remainingPrice) > 0) {
                // If the points are more than the remaining price, use only the required amount
                pointsToApply = remainingPrice;
            }

            totalDiscount = totalDiscount.add(pointsToApply);
            remainingPrice = remainingPrice.subtract(pointsToApply);

            // After applying points, delete the referral points
            referralPoints.delete(referralPoint);
        }

        return totalDiscount;
    }

    // Method to apply promo codes for the specific event
    public static BigDecimal applyPromoCodes(PromotionRepository promotionRepository, Event event, List<String> promoCodes) {
        BigDecimal totalDiscount = BigDecimal.ZERO;

        // Fetch all valid promo codes for the event
        List<Promotion> validPromoCodes = promotionRepository.findAllByEventAndPromotionCodeIn(event, promoCodes);

        if (validPromoCodes.isEmpty()) {
            return BigDecimal.ZERO; // No valid promo codes found
        }

        for (Promotion promoCode : validPromoCodes) {
            // validate the promo code based on its type
            if (!isValidPromoCodes(promoCode)) {
                continue; // skip invalid promo codes
            }

            if (promoCode.getAvailableUses() <= 0) {
                continue; // skip promo codes with no remaining uses
            }

            // Apply the promo code discount if valid
            totalDiscount = totalDiscount.add(promoCode.getDiscountPercentage());
            promoCode.setAvailableUses(promoCode.getAvailableUses() - 1);
            promotionRepository.save(promoCode);
        }
        return totalDiscount;
    }

    private static boolean isValidPromoCodes(Promotion promoCode) {
        OffsetDateTime now = OffsetDateTime.now();

        // Validate for 'DATEBASED' promo codes
        if ("DATEBASED".equals(promoCode.getPromotionType())) {
            return promoCode.getEndDate().isAfter(now);
        }

        // Validate for 'VOUCHER' promo codes
        if ("VOUCHER".equals(promoCode.getPromotionType())) {
            return promoCode.getEndDate().isAfter(now) && promoCode.getAvailableUses() > 0;
        }

        return false;
    }

    public static boolean processPayment(BigDecimal amount) {
        // Placeholder for payment gateway integration
        // Here you would integrate your payment system to process the transaction
        return true; // Assume payment is successful for now
    }

    public static String generateInvoiceCode() {
        final String PREFIX = "INV";
        final String DATE_FORMAT = "yyyyMMdd";

        String datePart = new SimpleDateFormat(DATE_FORMAT).format(new Date());
        String uuidPart = UUID.randomUUID().toString().substring(0, 8);

        return PREFIX + datePart + "-" + uuidPart;
    }
}
