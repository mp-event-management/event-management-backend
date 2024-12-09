package com.eventmanagement.EventManagementBackend.infrastructure.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {
    private Integer customerId;
    private Integer eventId;
    private Integer ticketQuantity;
    private String promoCode;
    private Boolean isUsePoints;
}
