package com.eventmanagement.EventManagementBackend.usecase.events.impl;

import com.eventmanagement.EventManagementBackend.entity.Event;
import com.eventmanagement.EventManagementBackend.infrastructure.events.repository.EventsRepository;
import com.eventmanagement.EventManagementBackend.usecase.events.GetEventsUsecase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetEventsUsecaseImpl implements GetEventsUsecase {
    private final EventsRepository eventsRepository;

    public GetEventsUsecaseImpl(EventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }

    @Override
    public List<Event> getAllEvents() {
        return eventsRepository.findAll();
    }

    @Override
    public Event getEventById(Long id) {
        return null;
    }
}
