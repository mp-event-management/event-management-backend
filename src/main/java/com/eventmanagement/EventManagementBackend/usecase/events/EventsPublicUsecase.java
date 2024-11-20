package com.eventmanagement.EventManagementBackend.usecase.events;

import com.eventmanagement.EventManagementBackend.entity.Event;
import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.CreateEventRequestDTO;

import java.util.List;

public interface EventsPublicUsecase {
    List<Event> getAllEvents();
    Event getEventById(Long id);
    Event createEvent(CreateEventRequestDTO createEventRequestDTO);
    Event updateEvent(Event event);
    void deleteEvent(Long id);
}
