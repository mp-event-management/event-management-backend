package com.eventmanagement.EventManagementBackend.usecase.eventReviews;

import com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.dto.EventReviewsRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.dto.EventReviewsResponseDTO;

public interface EventReviewsPublicUsecase {

    EventReviewsResponseDTO createEventReview(EventReviewsRequestDTO request);
}
