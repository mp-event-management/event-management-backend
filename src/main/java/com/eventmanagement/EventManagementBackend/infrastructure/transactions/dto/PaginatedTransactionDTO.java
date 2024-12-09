package com.eventmanagement.EventManagementBackend.infrastructure.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedTransactionDTO<T> {
    private List<T> transactions;
    private long totalElements;
    private int totalPages;
    private int currentPage;
}
