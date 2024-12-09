package com.eventmanagement.EventManagementBackend.infrastructure.transactions.utils;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Utils {

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
