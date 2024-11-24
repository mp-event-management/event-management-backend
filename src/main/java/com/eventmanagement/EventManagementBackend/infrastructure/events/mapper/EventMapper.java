package com.eventmanagement.EventManagementBackend.infrastructure.events.mapper;

import com.eventmanagement.EventManagementBackend.common.exceptions.DataNotFoundException;
import com.eventmanagement.EventManagementBackend.entity.Category;
import com.eventmanagement.EventManagementBackend.entity.City;
import com.eventmanagement.EventManagementBackend.entity.Event;
import com.eventmanagement.EventManagementBackend.entity.UsersAccount;
import com.eventmanagement.EventManagementBackend.infrastructure.categories.dto.CategoryDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.categories.repository.CategoryRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.cities.dto.CityDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.cities.repository.CityRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.EventDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.users.repository.UsersAccountRepository;

public class EventMapper {

    public static EventDTO mapToEventDto(Event event) {
        return new EventDTO(
                event.getEventId(),
                event.getUserOrganizer().getUserId(),
                event.getTitle(),
                event.getDescription(),
                new CategoryDTO(
                        event.getCategory().getCategoryId(),
                        event.getCategory().getName(),
                        event.getCategory().getIconUrl()
                ),
                event.getEventImagesUrl(),
                event.getStartDate(),
                event.getEndDate(),
                event.getTicketPrice(),
                event.getTotalTicket(),
                event.getAvailableTicket(),
                event.getEventStatus(),
                new CityDTO(
                        event.getCity().getCityId(),
                        event.getCity().getName()
                ),
                event.getAddress()
        );
    }

    public static Event mapToEvent(EventDTO eventDTO, UsersAccountRepository usersRepository, CategoryRepository categoryRepository, CityRepository cityRepository) {
        // Fetch related entity using IDs from the repository
        UsersAccount userOrganizer = usersRepository.findById(eventDTO.getUserOrganizerId())
                .orElseThrow(() -> new DataNotFoundException("User Organizer not found"));

        Category category = eventDTO.getCategory() != null
                ? categoryRepository.findById(eventDTO.getCategory().getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category not found"))
                : null;

        City city = eventDTO.getCity() != null
                ? cityRepository.findById(eventDTO.getCity().getCityId())
                .orElseThrow(() -> new DataNotFoundException("City not found"))
                : null;

        return new Event(
                eventDTO.getEventId(),
                userOrganizer,
                eventDTO.getTitle(),
                eventDTO.getDescription(),
                category,
                eventDTO.getEventImagesUrl(),
                eventDTO.getStartDate(),
                eventDTO.getEndDate(),
                eventDTO.getTicketPrice(),
                eventDTO.getTotalTicket(),
                eventDTO.getAvailableTicket(),
                eventDTO.getEventStatus(),
                city,
                eventDTO.getAddress()
        );
    }
}
