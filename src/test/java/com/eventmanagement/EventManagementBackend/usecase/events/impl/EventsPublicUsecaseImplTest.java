package com.eventmanagement.EventManagementBackend.usecase.events.impl;


import com.eventmanagement.EventManagementBackend.common.exceptions.DataNotFoundException;
import com.eventmanagement.EventManagementBackend.entity.*;
import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.*;
import com.eventmanagement.EventManagementBackend.infrastructure.events.repository.EventsRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.users.repository.UsersAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EventsPublicUsecaseImplTest {

    @Mock
    private EventsRepository eventsRepository;

    @Mock
    private UsersAccountRepository usersAccountRepository;

    @InjectMocks
    private EventsPublicUsecaseImpl eventsPublicUsecase;

    private UsersAccount userOrganizer;
    private Category category;
    private City city;
    private Event event;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userOrganizer = new UsersAccount();
        userOrganizer.setUserId(1);
        userOrganizer.setRole(new Role()); // Role "ORGANIZER"

        category = new Category();
        category.setCategoryId(1);
        category.setName("Music");

        city = new City();
        city.setCityId(1);
        city.setName("New York");

        event = new Event();
        event.setEventId(1);
        event.setTitle("Concert");
        event.setCategory(category);
        event.setCity(city);
        event.setUserOrganizer(userOrganizer);
        event.setTotalTicket(100);
        event.setAvailableTicket(100);
        event.setEventImagesUrl("https://placehold.co/500x500");
        event.setAddress("Jakarta street");
    }

    @Test
    void getAllEvents_WithValidFilter_ShouldReturnPaginatedResponse() {
        // Given
        FilterEventRequestDTO filterRequest = new FilterEventRequestDTO();
        filterRequest.setPage(0);
        filterRequest.setSize(10);
        filterRequest.setSearch("Concert");

        when(eventsRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0));


        // When
        PaginatedEventResponseDTO<EventDTO> response = eventsPublicUsecase.getAllEvents(filterRequest);

        // Then
        assertNotNull(response);
        verify(eventsRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void getEventById_WithExistingEventId_ShouldReturnEventDTO() {
        // Given
        when(eventsRepository.findById(1)).thenReturn(Optional.of(event));

        // When
        EventDTO eventDTO = eventsPublicUsecase.getEventById(1);

        // Then
        assertNotNull(eventDTO);
        assertEquals("Concert", eventDTO.getTitle());
        verify(eventsRepository, times(1)).findById(1);
    }

    @Test
    void getEventById_WithNonExistingEventId_ShouldThrowException() {
        // Given
        when(eventsRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(DataNotFoundException.class, () -> eventsPublicUsecase.getEventById(1));
    }

//    @Test
//    void createEvent_WithValidData_ShouldReturnCreateEventResponseDTO() {
//        // Given
//        CreateEventRequestDTO createEventRequestDTO = new CreateEventRequestDTO();
//        createEventRequestDTO.setTitle("Concert");
//        createEventRequestDTO.setDescription("Music");
//        createEventRequestDTO.setCategoryId(1);
//        createEventRequestDTO.setEventImagesUrl("https://placehold.co/500x500");
//        createEventRequestDTO.setStartDate(OffsetDateTime.parse("2024-12-12T18:00:00Z"));
//        createEventRequestDTO.setEndDate(OffsetDateTime.parse("2024-12-13T18:00:00Z"));
//        createEventRequestDTO.setTicketPrice(BigDecimal.valueOf(200000));
//        createEventRequestDTO.setUserOrganizerId(1);
//        createEventRequestDTO.setCityId(1);
//        createEventRequestDTO.setAddress("Jakarta street");
//        createEventRequestDTO.setEventStatus("UPCOMING");
//        createEventRequestDTO.setTotalTicket(100);
//        createEventRequestDTO.setAvailableTicket(100);
//
//
//        when(usersAccountRepository.findById(1)).thenReturn(Optional.of(userOrganizer));
//        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
//        when(cityRepository.findById(1)).thenReturn(Optional.of(city));
//        when(eventsRepository.save(any())).thenReturn(event);
//
//        // When
//        CreateEventResponseDTO response = eventsPublicUsecase.createEvent(createEventRequestDTO);
//
//        // Then
//        assertNotNull(response);
//        assertEquals("Concert", response.getTitle());
//        verify(eventsRepository, times(1)).save(any());
//    }

    @Test
    void createEvent_WithInvalidUserOrganizer_ShouldThrowException() {
        // Given
        CreateEventRequestDTO createEventRequestDTO = new CreateEventRequestDTO();
        createEventRequestDTO.setUserOrganizerId(999); // Invalid user ID

        when(usersAccountRepository.findById(999)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> eventsPublicUsecase.createEvent(createEventRequestDTO));
    }

    @Test
    void updateEvent_WithExistingEvent_ShouldReturnUpdatedEventDTO() {
        // Given
        UpdateEventRequestDTO updateEventRequestDTO = new UpdateEventRequestDTO();
        updateEventRequestDTO.setTitle("Updated Concert");
        when(eventsRepository.findById(1)).thenReturn(Optional.of(event));
        when(eventsRepository.save(any())).thenReturn(event);

        // When
        UpdateEventRequestDTO response = eventsPublicUsecase.updateEvent(1, updateEventRequestDTO);

        // Then
        assertEquals("Updated Concert", response.getTitle());
        verify(eventsRepository, times(1)).save(any());
    }

    @Test
    void updateEvent_WithNonExistingEvent_ShouldThrowException() {
        // Given
        UpdateEventRequestDTO updateEventRequestDTO = new UpdateEventRequestDTO();
        updateEventRequestDTO.setTitle("Updated Concert");
        when(eventsRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(DataNotFoundException.class, () -> eventsPublicUsecase.updateEvent(1, updateEventRequestDTO));
    }

    @Test
    void deleteEvent_WithExistingEvent_ShouldDelete() {
        // Given
        when(eventsRepository.findById(1)).thenReturn(Optional.of(event));

        // When
        eventsPublicUsecase.deleteEvent(1);

        // Then
        verify(eventsRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteEvent_WithNonExistingEvent_ShouldThrowException() {
        // Given
        when(eventsRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(DataNotFoundException.class, () -> eventsPublicUsecase.deleteEvent(1));
    }


}
