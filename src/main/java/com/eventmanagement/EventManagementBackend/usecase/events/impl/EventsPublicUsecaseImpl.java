package com.eventmanagement.EventManagementBackend.usecase.events.impl;

import com.eventmanagement.EventManagementBackend.common.exceptions.DataNotFoundException;
import com.eventmanagement.EventManagementBackend.entity.Category;
import com.eventmanagement.EventManagementBackend.entity.City;
import com.eventmanagement.EventManagementBackend.entity.Event;
import com.eventmanagement.EventManagementBackend.entity.UsersAccount;
import com.eventmanagement.EventManagementBackend.infrastructure.categories.repository.CategoryRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.cities.repository.CityRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.CreateEventRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.events.repository.EventsRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.users.repository.UsersRepository;
import com.eventmanagement.EventManagementBackend.usecase.events.EventsPublicUsecase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventsPublicUsecaseImpl implements EventsPublicUsecase {
    private final EventsRepository eventsRepository;
    private final UsersRepository usersRepository;
    private final CategoryRepository categoryRepository;
    private final CityRepository cityRepository;

    public EventsPublicUsecaseImpl(EventsRepository eventsRepository, UsersRepository usersRepository, CategoryRepository categoryRepository, CityRepository cityRepository) {
        this.eventsRepository = eventsRepository;
        this.usersRepository = usersRepository;
        this.categoryRepository = categoryRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public List<Event> getAllEvents() {
        return eventsRepository.findAll();
    }

    @Override
    public Event getEventById(Long id) {
        return eventsRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Event not found"));
    }

    @Override
    public Event createEvent(CreateEventRequestDTO createEventRequestDTO) {
        // Fetch the related entities using the IDs
        UsersAccount userOrganizer = usersRepository.findById(createEventRequestDTO.getUserOrganizerId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Organizer ID"));
        Category category = categoryRepository.findById(createEventRequestDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Category ID"));
        City city = cityRepository.findById(createEventRequestDTO.getCityId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid City ID"));

        // Create the Event entity
        Event event = new Event();
        event.setUserOrganizer(userOrganizer);
        event.setTitle(createEventRequestDTO.getTitle());
        event.setDescription(createEventRequestDTO.getDescription());
        event.setCategory(category);
        event.setEventImagesUrl(createEventRequestDTO.getEventImagesUrl());
        event.setStartDate(createEventRequestDTO.getStartDate());
        event.setEndDate(createEventRequestDTO.getEndDate());
        event.setTicketPrice(createEventRequestDTO.getTicketPrice());
        event.setTotalTicket(createEventRequestDTO.getTotalTicket());
        event.setAvailableTicket(createEventRequestDTO.getAvailableTicket());
        event.setEventStatus(createEventRequestDTO.getEventStatus());
        event.setCity(city);
        event.setAddress(createEventRequestDTO.getAddress());

        return eventsRepository.save(event);
    }

    @Override
    public Event updateEvent(Event event) {
        return null;
    }

    @Override
    public void deleteEvent(Long id) {

    }
}
