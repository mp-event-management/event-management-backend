package com.eventmanagement.EventManagementBackend.usecase.events;

import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.CreateEventRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.CreateEventResponseDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.EventDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.UpdateEventRequestDTO;

import java.util.List;

public interface EventsPublicUsecase {
    //    List<GetEventsResponseDTO> getAllEvents();
    List<EventDTO> getAllEvents();

    //    GetEventsResponseDTO getEventById(Integer eventId);
    EventDTO getEventById(Integer eventId);

    CreateEventResponseDTO createEvent(CreateEventRequestDTO createEventRequestDTO);

    UpdateEventRequestDTO updateEvent(Integer id, UpdateEventRequestDTO event);

    void deleteEvent(Integer id);
}
