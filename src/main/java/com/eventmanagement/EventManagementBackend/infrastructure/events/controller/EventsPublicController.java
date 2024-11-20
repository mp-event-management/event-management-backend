package com.eventmanagement.EventManagementBackend.infrastructure.events.controller;

import com.eventmanagement.EventManagementBackend.common.response.ApiResponse;
import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.CreateEventRequestDTO;
import com.eventmanagement.EventManagementBackend.usecase.events.EventsPublicUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
public class EventsPublicController {
    private final EventsPublicUsecase eventsPublicUsecase;


    public EventsPublicController(EventsPublicUsecase eventsPublicUsecase) {
        this.eventsPublicUsecase = eventsPublicUsecase;
    }

    @GetMapping
    public ResponseEntity<?> getAllEvents() {
        return ApiResponse.successfulResponse("Get all events success", eventsPublicUsecase.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        return ApiResponse.successfulResponse("Get event details success", eventsPublicUsecase.getEventById(id));
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody CreateEventRequestDTO createEventRequestDTO) {
        return ApiResponse.successfulResponse("Create an event success", eventsPublicUsecase.createEvent(createEventRequestDTO));
    }
}
