package com.eventmanagement.EventManagementBackend.usecase.events.impl;

import com.eventmanagement.EventManagementBackend.common.exceptions.DataNotFoundException;
import com.eventmanagement.EventManagementBackend.entity.Category;
import com.eventmanagement.EventManagementBackend.entity.City;
import com.eventmanagement.EventManagementBackend.entity.Event;
import com.eventmanagement.EventManagementBackend.entity.UsersAccount;
import com.eventmanagement.EventManagementBackend.infrastructure.categories.repository.CategoryRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.cities.repository.CityRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.CreateEventRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.EventDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.UpdateEventRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.events.mapper.EventMapper;
import com.eventmanagement.EventManagementBackend.infrastructure.events.repository.EventsRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.users.repository.UsersAccountRepository;
import com.eventmanagement.EventManagementBackend.usecase.events.EventsPublicUsecase;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
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
    public List<EventDTO> getAllEvents() {
        List<Event> events = eventsRepository.findAll();

        if (events.isEmpty()) {
            return Collections.emptyList();
        }

        return events.stream().map(EventMapper::mapToEventDto).collect(Collectors.toList());
//        return events.stream().map(event -> GetEventsResponseDTO.builder()
//                .eventId(event.getEventId())
//                .userOrganizerId(event.getUserOrganizer().getUserId())
//                .title(event.getTitle())
//                .description(event.getDescription())
//                .category(CategoryDTO.builder()
//                        .categoryId(event.getCategory().getCategoryId())
//                        .name(event.getCategory().getName())
//                        .iconUrl(event.getCategory().getIconUrl())
//                        .build())
//                .eventImagesUrl(event.getEventImagesUrl())
//                .startDate(event.getStartDate())
//                .endDate(event.getEndDate())
//                .ticketPrice(event.getTicketPrice())
//                .totalTicket(event.getTotalTicket())
//                .availableTicket(event.getAvailableTicket())
//                .eventStatus(event.getEventStatus())
//                .city(CityDTO.builder()
//                        .cityId(event.getCity().getCityId())
//                        .cityName(event.getCity().getName())
//                        .build())
//                .address(event.getAddress())
//                .build()).collect(Collectors.toList());
    }

    @Override
    public EventDTO getEventById(Integer eventId) {
        Event event = eventsRepository.findById(eventId).orElseThrow(() -> new DataNotFoundException("Event not found"));

        return EventMapper.mapToEventDto(event);
//        return GetEventsResponseDTO.builder()
//                .eventId(event.getEventId())
//                .userOrganizerId(event.getUserOrganizer().getUserId())
//                .title(event.getTitle())
//                .description(event.getDescription())
//                .category(CategoryDTO.builder()
//                        .categoryId(event.getCategory().getCategoryId())
//                        .name(event.getCategory().getName())
//                        .iconUrl(event.getCategory().getIconUrl())
//                        .build())
//                .eventImagesUrl(event.getEventImagesUrl())
//                .startDate(event.getStartDate())
//                .endDate(event.getEndDate())
//                .ticketPrice(event.getTicketPrice())
//                .totalTicket(event.getTotalTicket())
//                .availableTicket(event.getAvailableTicket())
//                .eventStatus(event.getEventStatus())
//                .city(CityDTO.builder()
//                        .cityId(event.getCity().getCityId())
//                        .cityName(event.getCity().getName())
//                        .build())
//                .address(event.getAddress())
//                .build();
    }

    @Override
    public CreateEventRequestDTO createEvent(CreateEventRequestDTO createEventRequestDTO) {
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
        return new CreateEventRequestDTO(
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
//        return CreateEventRequestDTO.builder()
//                .userOrganizerId(savedEvent.getUserOrganizer().getUserId())
//                .title(savedEvent.getTitle())
//                .description(savedEvent.getDescription())
//                .categoryId(savedEvent.getCategory().getCategoryId())
//                .eventImagesUrl(savedEvent.getEventImagesUrl())
//                .startDate(savedEvent.getStartDate())
//                .endDate(savedEvent.getEndDate())
//                .ticketPrice(savedEvent.getTicketPrice())
//                .totalTicket(savedEvent.getTotalTicket())
//                .availableTicket(savedEvent.getAvailableTicket())
//                .eventStatus(savedEvent.getEventStatus())
//                .cityId(savedEvent.getCity().getCityId())
//                .address(savedEvent.getAddress())
//                .build();
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
//        return UpdateEventRequestDTO.builder()
//                .eventId(updatedEvent.getEventId())
//                .title(updatedEvent.getTitle())
//                .description(updatedEvent.getDescription())
//                .categoryId(updatedEvent.getCategory().getCategoryId())
//                .cityId(updatedEvent.getCity().getCityId())
//                .eventImagesUrl(updatedEvent.getEventImagesUrl())
//                .startDate(updatedEvent.getStartDate())
//                .endDate(updatedEvent.getEndDate())
//                .ticketPrice(updatedEvent.getTicketPrice())
//                .totalTicket(updatedEvent.getTotalTicket())
//                .availableTicket(updatedEvent.getAvailableTicket())
//                .eventStatus(updatedEvent.getEventStatus())
//                .address(updatedEvent.getAddress())
//                .build();
//    }

    @Override
    public void deleteEvent(Integer id) {
        eventsRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Event not found"));
        eventsRepository.deleteById(id);
    }
}
