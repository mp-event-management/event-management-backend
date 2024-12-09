package com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventReviewsRequestDTO {

    private Integer eventId;
    private Integer customerId;
    private BigDecimal rating;
    private String reviewText;
}
