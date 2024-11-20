package com.eventmanagement.EventManagementBackend.infrastructure.events.controller;

import com.eventmanagement.EventManagementBackend.common.response.ApiResponse;
import com.eventmanagement.EventManagementBackend.usecase.events.GetEventsUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
public class EventsController {
    private final GetEventsUsecase getEventsUsecase;


    public EventsController(GetEventsUsecase getEventsUsecase) {
        this.getEventsUsecase = getEventsUsecase;
    }

    @GetMapping
    public ResponseEntity<?> getAllEvents() {
        return ApiResponse.successfulResponse("Get all events success", getEventsUsecase.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@RequestParam Long id) {
        return null;
    }
}
