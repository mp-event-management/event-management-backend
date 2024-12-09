package com.eventmanagement.EventManagementBackend.infrastructure.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterTransactionDTO {
    private String search;

    // Define default value
    private int page = 0;
    private int size = 5;
}
