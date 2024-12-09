package com.eventmanagement.EventManagementBackend.infrastructure.tickets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedTicketResponseDTO<T> {
    private List<T> tickets;
    private long totalElements;
    private int totalPages;
    private int currentPage;
}

