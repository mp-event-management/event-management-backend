package com.eventmanagement.EventManagementBackend.infrastructure.events.mapper;

import com.eventmanagement.EventManagementBackend.entity.Event;
import com.eventmanagement.EventManagementBackend.infrastructure.categories.dto.CategoryDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.cities.dto.CityDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.events.dto.EventDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.promotion.dto.GetPromotionListDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.users.dto.UserProfileDTO;

import java.util.Collections;
import java.util.stream.Collectors;

public class EventMapper {

    public static EventDTO mapToEventDto(Event event) {
        return new EventDTO(
                event.getEventId(),
                new UserProfileDTO(
                        event.getUserOrganizer().getUserId(),
                        event.getUserOrganizer().getRole(),
                        event.getUserOrganizer().getName(),
                        event.getUserOrganizer().getEmail(),
                        event.getUserOrganizer().getProfilePictureUrl()
                ),
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
                event.getAddress(),
                event.getPromotions() != null ? event.getPromotions().stream()
                        .map(promotion -> new GetPromotionListDTO(
                                promotion.getPromotionId(),
                                promotion.getPromotionType(),
                                promotion.getPromotionCode(),
                                promotion.getDiscountPercentage(),
                                promotion.getAvailableUses(),
                                promotion.getStartDate(),
                                promotion.getEndDate()
                        ))
                        .collect(Collectors.toList())
                        : Collections.emptyList()
        );
    }
}