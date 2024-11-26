package com.eventmanagement.EventManagementBackend.infrastructure.events.controller;

import com.eventmanagement.EventManagementBackend.common.response.ApiResponse;
import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.*;
import com.eventmanagement.EventManagementBackend.usecase.events.EventsPublicUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
public class EventsPublicController {
    private final EventsPublicUsecase eventsPublicUsecase;


    public EventsPublicController(EventsPublicUsecase eventsPublicUsecase) {
        this.eventsPublicUsecase = eventsPublicUsecase;
    }

    @GetMapping
    public ResponseEntity<?> getAllEvents(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer cityId,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        FilterEventRequestDTO filterRequest = new FilterEventRequestDTO();
        filterRequest.setCategoryId(categoryId);
        filterRequest.setCityId(cityId);
        filterRequest.setSearch(search);
        filterRequest.setPage(page);
        filterRequest.setSize(size);

        PaginatedEventResponseDTO<EventDTO> events = eventsPublicUsecase.getAllEvents(filterRequest);
        return ApiResponse.successfulResponse("Get all events success", events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Integer id) {
        return ApiResponse.successfulResponse("Get event details success", eventsPublicUsecase.getEventById(id));
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody CreateEventRequestDTO createEventRequestDTO) {
        CreateEventResponseDTO createdEvent = eventsPublicUsecase.createEvent(createEventRequestDTO);
        return ApiResponse.successfulResponse("Create an event success", createdEvent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Integer id, @Validated @RequestBody UpdateEventRequestDTO updateEventRequestDTO) {
        // Call the eventId in the DTO to update the event
        UpdateEventRequestDTO updatedEvent = eventsPublicUsecase.updateEvent(id, updateEventRequestDTO);

        // Return the updated event as the response
        return ApiResponse.successfulResponse("Update event success", updatedEvent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Integer id) {
        eventsPublicUsecase.deleteEvent(id);
        return ApiResponse.successfulResponse("Delete event success", null);
    }
}
