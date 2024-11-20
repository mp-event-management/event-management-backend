package com.eventmanagement.EventManagementBackend.usecase.events;

import com.eventmanagement.EventManagementBackend.entity.Event;

import java.util.List;

public interface GetEventsUsecase {
    List<Event> getAllEvents();
    Event getEventById(Long id);
}
