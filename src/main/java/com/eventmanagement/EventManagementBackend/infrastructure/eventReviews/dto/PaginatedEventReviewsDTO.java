package com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedEventReviewsDTO<T> {
    private List<T> eventReviews;
    private long totalElements;
    private int totalPages;
    private int currentPage;
}
