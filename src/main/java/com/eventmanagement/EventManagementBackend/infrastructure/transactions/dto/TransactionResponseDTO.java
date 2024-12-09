package com.eventmanagement.EventManagementBackend.infrastructure.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDTO {
    private Integer transactionId;
    private Integer eventId;
    private Integer customerId;
    private Integer promotionId;
    private Integer ticketQuantity;
    private BigDecimal ticketPrice;
    private BigDecimal discountPercentage;
    private BigDecimal totalDiscount;
    private BigDecimal referralPointsUsed;
    private BigDecimal finalPrice;
    private String paymentStatus;
    private String invoiceCode;
}
