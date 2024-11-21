package com.eventmanagement.EventManagementBackend.usecase.events;

import com.eventmanagement.EventManagementBackend.entity.Event;
import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.CreateEventRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.GetEventsRequestDTO;

import java.util.List;

public interface EventsPublicUsecase {
    List<GetEventsRequestDTO> getAllEvents();
    GetEventsRequestDTO getEventById(Integer eventId);
    CreateEventRequestDTO createEvent(CreateEventRequestDTO createEventRequestDTO);
    Event updateEvent(Event event);
    void deleteEvent(Integer id);
}
