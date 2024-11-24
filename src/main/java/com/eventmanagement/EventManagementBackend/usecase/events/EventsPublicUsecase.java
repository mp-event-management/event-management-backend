package com.eventmanagement.EventManagementBackend.usecase.events;

import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.CreateEventRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.GetEventsResponseDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.UpdateEventRequestDTO;

import java.util.List;

public interface EventsPublicUsecase {
    List<GetEventsResponseDTO> getAllEvents();

    GetEventsResponseDTO getEventById(Integer eventId);

    CreateEventRequestDTO createEvent(CreateEventRequestDTO createEventRequestDTO);

    UpdateEventRequestDTO updateEvent(UpdateEventRequestDTO event);

    void deleteEvent(Integer id);
}
