package com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.controller;

import com.eventmanagement.EventManagementBackend.common.response.ApiResponse;
import com.eventmanagement.EventManagementBackend.infrastructure.auth.Claims;
import com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.dto.EventReviewsRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.dto.EventReviewsResponseDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.dto.FilterEventReviewsDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.dto.PaginatedEventReviewsDTO;
import com.eventmanagement.EventManagementBackend.usecase.eventReviews.EventReviewsPublicUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
public class EventReviewsPublicController {

    private final EventReviewsPublicUsecase eventReviewsPublicUsecase;


    public EventReviewsPublicController(EventReviewsPublicUsecase eventReviewsPublicUsecase) {
        this.eventReviewsPublicUsecase = eventReviewsPublicUsecase;
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<?> getAllReviewsByEventId(
            @PathVariable Integer eventId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        // Validate page and size to ensure they are positive
        if (page < 0) page = 0;
        if (size <= 0) size = 10;

        FilterEventReviewsDTO filterEventReviewDTO = new FilterEventReviewsDTO();
        filterEventReviewDTO.setPage(page);
        filterEventReviewDTO.setSize(size);

        // Use case method to fetch the paginated transactions for the customer
        PaginatedEventReviewsDTO<EventReviewsResponseDTO> paginatedEventReviews =
                eventReviewsPublicUsecase.getAllReviewsByEventId(eventId, filterEventReviewDTO);

        return ApiResponse.successfulResponse("Get all event reviews by event id successful", paginatedEventReviews);
    }

    @PostMapping
    public ResponseEntity<?> createReviews(@RequestBody EventReviewsRequestDTO request) {
        Long customerId = Claims.getUserIdFromJwt();
        request.setCustomerId(customerId.intValue());
        EventReviewsResponseDTO createReview = eventReviewsPublicUsecase.createEventReview(request);
        return ApiResponse.successfulResponse("Successful sent your rating and reviews", createReview);
    }
}
