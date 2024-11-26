package com.eventmanagement.EventManagementBackend.usecase.events;

import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.*;

public interface EventsPublicUsecase {
    //    List<GetEventsResponseDTO> getAllEvents();
    PaginatedEventResponseDTO<EventDTO> getAllEvents(FilterEventRequestDTO filterRequest);

    //    GetEventsResponseDTO getEventById(Integer eventId);
    EventDTO getEventById(Integer eventId);

    CreateEventResponseDTO createEvent(CreateEventRequestDTO createEventRequestDTO);

    UpdateEventRequestDTO updateEvent(Integer id, UpdateEventRequestDTO event);

    void deleteEvent(Integer id);
}
