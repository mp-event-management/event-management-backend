package com.eventmanagement.EventManagementBackend.usecase.events.impl;

import com.eventmanagement.EventManagementBackend.common.exceptions.DataNotFoundException;
import com.eventmanagement.EventManagementBackend.entity.Category;
import com.eventmanagement.EventManagementBackend.entity.City;
import com.eventmanagement.EventManagementBackend.entity.Event;
import com.eventmanagement.EventManagementBackend.entity.UsersAccount;
import com.eventmanagement.EventManagementBackend.infrastructure.categories.repository.CategoryRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.cities.repository.CityRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.*;
import com.eventmanagement.EventManagementBackend.infrastructure.events.mapper.EventMapper;
import com.eventmanagement.EventManagementBackend.infrastructure.events.repository.EventsRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.events.specification.FilterEventSpecifications;
import com.eventmanagement.EventManagementBackend.infrastructure.users.repository.UsersAccountRepository;
import com.eventmanagement.EventManagementBackend.usecase.events.EventsPublicUsecase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class EventsPublicUsecaseImpl implements EventsPublicUsecase {
    private final EventsRepository eventsRepository;
    private final UsersAccountRepository usersRepository;
    private final CategoryRepository categoryRepository;
    private final CityRepository cityRepository;

    public EventsPublicUsecaseImpl(EventsRepository eventsRepository, UsersAccountRepository usersRepository, CategoryRepository categoryRepository, CityRepository cityRepository) {
        this.eventsRepository = eventsRepository;
        this.usersRepository = usersRepository;
        this.categoryRepository = categoryRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public PaginatedEventResponseDTO<EventDTO> getAllEvents(FilterEventRequestDTO filterRequest) {
        Pageable pageable = PageRequest.of(filterRequest.getPage(), filterRequest.getSize(), Sort.by("eventId").ascending());

        // Specification dynamically
        Specification<Event> specification = Specification.where(null);

        if (filterRequest.getCategoryId() != null) {
            specification = specification.and(FilterEventSpecifications.hasCategory(filterRequest.getCategoryId()));
        }

        if (filterRequest.getCityId() != null) {
            specification = specification.and(FilterEventSpecifications.hasCity(filterRequest.getCityId()));
        }
        if (filterRequest.getSearch() != null && !filterRequest.getSearch().isEmpty()) {
            specification = specification.and(FilterEventSpecifications.hasTitleLike(filterRequest.getSearch()));
        }

        // If no filters are applied, specifications null, and fetch all events
        Page<Event> eventPage = eventsRepository.findAll(specification, pageable);

        return new PaginatedEventResponseDTO<>(
                eventPage.getContent().stream().map(EventMapper::mapToEventDto).collect(Collectors.toList()),
                eventPage.getTotalElements(),
                eventPage.getTotalPages(),
                eventPage.getNumber()
        );
    }

    @Override
    public EventDTO getEventById(Integer eventId) {
        Event event = eventsRepository.findById(eventId).orElseThrow(() -> new DataNotFoundException("Event not found"));

        return EventMapper.mapToEventDto(event);
    }

    @Override
    public CreateEventResponseDTO createEvent(CreateEventRequestDTO createEventRequestDTO) {
        // Fetch the related entities using the IDs
        UsersAccount userOrganizer = usersRepository.findById(createEventRequestDTO.getUserOrganizerId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Organizer ID"));

        // Check if the user's role is organizer ( role_id == 2 )
        if (userOrganizer.getRole().getRoleId() != 2) {
            throw new IllegalArgumentException("Only users with the Organizer role can create an event");
        }

        Category category = categoryRepository.findById(createEventRequestDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Category ID"));
        City city = cityRepository.findById(createEventRequestDTO.getCityId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid City ID"));

        // Convert DTO to Entity
        Event newEvent = createEventRequestDTO.toEntity(userOrganizer, category, city);

        // Save the Event entity
        Event savedEvent = eventsRepository.save(newEvent);

        // Return the response DTO
        return new CreateEventResponseDTO(
                savedEvent.getUserOrganizer().getUserId(),
                savedEvent.getTitle(),
                savedEvent.getDescription(),
                savedEvent.getCategory().getCategoryId(),
                savedEvent.getEventImagesUrl(),
                savedEvent.getStartDate(),
                savedEvent.getEndDate(),
                savedEvent.getTicketPrice(),
                savedEvent.getTotalTicket(),
                savedEvent.getAvailableTicket(),
                savedEvent.getEventStatus(),
                savedEvent.getCity().getCityId(),
                savedEvent.getAddress()
        );
    }

    @Override
    public UpdateEventRequestDTO updateEvent(Integer id, UpdateEventRequestDTO updateEventRequestDTO) {
        // Fetch the existing event ID
        Event existingEvent = eventsRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Event not found"));

        // Fetch related entities if IDs are provided in the update DTO
        if (updateEventRequestDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(updateEventRequestDTO.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Category ID"));
            existingEvent.setCategory(category);
        }

        if (updateEventRequestDTO.getCityId() != null) {
            City city = cityRepository.findById(updateEventRequestDTO.getCityId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid City ID"));
            existingEvent.setCity(city);
        }

        // Update fields of the existing event
        if (updateEventRequestDTO.getTitle() != null) {
            existingEvent.setTitle(updateEventRequestDTO.getTitle());
        }

        if (updateEventRequestDTO.getDescription() != null) {
            existingEvent.setDescription(updateEventRequestDTO.getDescription());
        }

        if (updateEventRequestDTO.getEventImagesUrl() != null) {
            existingEvent.setEventImagesUrl(updateEventRequestDTO.getEventImagesUrl());
        }

        if (updateEventRequestDTO.getStartDate() != null) {
            existingEvent.setStartDate(updateEventRequestDTO.getStartDate());
        }

        if (updateEventRequestDTO.getEndDate() != null) {
            existingEvent.setEndDate(updateEventRequestDTO.getEndDate());
        }

        if (updateEventRequestDTO.getTicketPrice() != null) {
            existingEvent.setTicketPrice(updateEventRequestDTO.getTicketPrice());
        }

        if (updateEventRequestDTO.getTotalTicket() != null) {
            existingEvent.setTotalTicket(updateEventRequestDTO.getTotalTicket());
        }

        if (updateEventRequestDTO.getAvailableTicket() != null) {
            existingEvent.setAvailableTicket(updateEventRequestDTO.getAvailableTicket());
        }

        if (updateEventRequestDTO.getEventStatus() != null) {
            existingEvent.setEventStatus(updateEventRequestDTO.getEventStatus());
        }

        if (updateEventRequestDTO.getAddress() != null) {
            existingEvent.setAddress(updateEventRequestDTO.getAddress());
        }

        // Save the updated event
        Event updatedEvent = eventsRepository.save(existingEvent);

        // Convert the updated entity back to the DTO and return the data
        return new UpdateEventRequestDTO(
                updatedEvent.getEventId(),
                updatedEvent.getTitle(),
                updatedEvent.getDescription(),
                updatedEvent.getCategory().getCategoryId(),
                updatedEvent.getEventImagesUrl(),
                updatedEvent.getStartDate(),
                updatedEvent.getEndDate(),
                updatedEvent.getTicketPrice(),
                updatedEvent.getTotalTicket(),
                updatedEvent.getAvailableTicket(),
                updatedEvent.getEventStatus(),
                updatedEvent.getCity().getCityId(),
                updatedEvent.getAddress()
        );
    }

    @Override
    public void deleteEvent(Integer id) {
        eventsRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Event not found"));
        eventsRepository.deleteById(id);
    }
}
