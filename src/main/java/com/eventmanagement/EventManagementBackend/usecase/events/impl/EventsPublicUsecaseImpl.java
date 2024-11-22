package com.eventmanagement.EventManagementBackend.usecase.events.impl;

import com.eventmanagement.EventManagementBackend.common.exceptions.DataNotFoundException;
import com.eventmanagement.EventManagementBackend.entity.Category;
import com.eventmanagement.EventManagementBackend.entity.City;
import com.eventmanagement.EventManagementBackend.entity.Event;
import com.eventmanagement.EventManagementBackend.entity.UsersAccount;
import com.eventmanagement.EventManagementBackend.infrastructure.categories.dto.CategoryDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.categories.repository.CategoryRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.cities.dto.CityDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.cities.repository.CityRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.CreateEventRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.GetEventsResponseDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.UpdateEventRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.events.repository.EventsRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.users.repository.UsersRepository;
import com.eventmanagement.EventManagementBackend.usecase.events.EventsPublicUsecase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<GetEventsResponseDTO> getAllEvents() {
        return eventsRepository.findAllEvents().stream().map(event -> GetEventsResponseDTO.builder()
                .eventId(event.getEventId())
                .userOrganizerId(event.getUserOrganizer().getUserId())
                .title(event.getTitle())
                .description(event.getDescription())
                .category(CategoryDTO.builder()
                        .categoryId(event.getCategory().getCategoryId())
                        .name(event.getCategory().getName())
                        .iconUrl(event.getCategory().getIconUrl())
                        .build())
                .eventImagesUrl(event.getEventImagesUrl())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .ticketPrice(event.getTicketPrice())
                .totalTicket(event.getTotalTicket())
                .availableTicket(event.getAvailableTicket())
                .eventStatus(event.getEventStatus())
                .city(CityDTO.builder()
                        .cityId(event.getCity().getCityId())
                        .cityName(event.getCity().getName())
                        .build())
                .address(event.getAddress())
                .build()).collect(Collectors.toList());
    }

    @Override
    public GetEventsResponseDTO getEventById(Integer eventId) {
        Event event = eventsRepository.findById(eventId).orElseThrow(() -> new DataNotFoundException("Event not found"));

        return GetEventsResponseDTO.builder()
                .eventId(event.getEventId())
                .userOrganizerId(event.getUserOrganizer().getUserId())
                .title(event.getTitle())
                .description(event.getDescription())
                .category(CategoryDTO.builder()
                        .categoryId(event.getCategory().getCategoryId())
                        .name(event.getCategory().getName())
                        .iconUrl(event.getCategory().getIconUrl())
                        .build())
                .eventImagesUrl(event.getEventImagesUrl())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .ticketPrice(event.getTicketPrice())
                .totalTicket(event.getTotalTicket())
                .availableTicket(event.getAvailableTicket())
                .eventStatus(event.getEventStatus())
                .city(CityDTO.builder()
                        .cityId(event.getCity().getCityId())
                        .cityName(event.getCity().getName())
                        .build())
                .address(event.getAddress())
                .build();
    }

    @Override
    public CreateEventRequestDTO createEvent(CreateEventRequestDTO createEventRequestDTO) {
        // Fetch the related entities using the IDs
        UsersAccount userOrganizer = usersRepository.findById(createEventRequestDTO.getUserOrganizerId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Organizer ID"));
        Category category = categoryRepository.findById(createEventRequestDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Category ID"));
        City city = cityRepository.findById(createEventRequestDTO.getCityId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid City ID"));

        // Convert DTO to Entity
        Event newEvent = createEventRequestDTO.toEntity(userOrganizer, category, city);

        // Save the Event entity
        Event savedEvent = eventsRepository.save(newEvent);

        // Return the response DTO
        return CreateEventRequestDTO.builder()
                .userOrganizerId(savedEvent.getUserOrganizer().getUserId())
                .title(savedEvent.getTitle())
                .description(savedEvent.getDescription())
                .categoryId(savedEvent.getCategory().getCategoryId())
                .eventImagesUrl(savedEvent.getEventImagesUrl())
                .startDate(savedEvent.getStartDate())
                .endDate(savedEvent.getEndDate())
                .ticketPrice(savedEvent.getTicketPrice())
                .totalTicket(savedEvent.getTotalTicket())
                .availableTicket(savedEvent.getAvailableTicket())
                .eventStatus(savedEvent.getEventStatus())
                .cityId(savedEvent.getCity().getCityId())
                .address(savedEvent.getAddress())
                .build();
    }

    @Override
    public UpdateEventRequestDTO updateEvent(Event event) {
        return null;
    }

    @Override
    public void deleteEvent(Integer id) {
        if (!eventsRepository.existsById(id)) throw new DataNotFoundException("Event not found");
        eventsRepository.deleteById(id);
    }
}
