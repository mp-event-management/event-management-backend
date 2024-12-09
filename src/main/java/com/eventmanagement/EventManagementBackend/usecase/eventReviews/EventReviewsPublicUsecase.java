package com.eventmanagement.EventManagementBackend.usecase.eventReviews;

import com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.dto.EventReviewsRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.dto.EventReviewsResponseDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.dto.FilterEventReviewsDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.dto.PaginatedEventReviewsDTO;

public interface EventReviewsPublicUsecase {

    EventReviewsResponseDTO createEventReview(EventReviewsRequestDTO request);

    PaginatedEventReviewsDTO<EventReviewsResponseDTO> getAllReviewsByEventId(Integer eventId, FilterEventReviewsDTO filter);
}
