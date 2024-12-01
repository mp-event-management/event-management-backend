package com.eventmanagement.EventManagementBackend.infrastructure.events.dto;

import com.eventmanagement.EventManagementBackend.infrastructure.categories.dto.CategoryDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.cities.dto.CityDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.users.dto.UserProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Integer eventId;
    private UserProfileDTO userOrganizer;
    private String title;
    private String description;
    private CategoryDTO category;
    private String eventImagesUrl;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private BigDecimal ticketPrice;
    private Integer totalTicket;
    private Integer availableTicket;
    private String eventStatus;
    private CityDTO city;
    private String address;
}
