package com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.controller;

import com.eventmanagement.EventManagementBackend.common.response.ApiResponse;
import com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.dto.EventReviewsRequestDTO;
import com.eventmanagement.EventManagementBackend.usecase.eventReviews.EventReviewsPublicUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reviews")
public class EventReviewsPublicController {

    private final EventReviewsPublicUsecase eventReviewsPublicUsecase;

    public EventReviewsPublicController(EventReviewsPublicUsecase eventReviewsPublicUsecase) {
        this.eventReviewsPublicUsecase = eventReviewsPublicUsecase;
    }

    @PostMapping
    public ResponseEntity<?> createEventReview(@RequestBody EventReviewsRequestDTO request) {
        return ApiResponse.successfulResponse("Successfully give rating and review", eventReviewsPublicUsecase.createEventReview(request));
    }
}
