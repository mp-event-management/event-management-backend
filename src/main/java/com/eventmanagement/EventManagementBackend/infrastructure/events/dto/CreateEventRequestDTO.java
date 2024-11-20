package com.eventmanagement.EventManagementBackend.infrastructure.events.dto;

import com.eventmanagement.EventManagementBackend.infrastructure.categories.dto.CategoryDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.cities.dto.CityDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventRequestDTO {
    private Integer userOrganizerId;
    private String title;
    private String description;
    private Integer categoryId;
    private String eventImagesUrl;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private BigDecimal ticketPrice;
    private int totalTicket;
    private int availableTicket;
    private String eventStatus;
    private Integer cityId;
    private String address;

//    public Event toEntity() {
//        Event event = new Event();
//        event.setUserOrganizer(userOrganizerId);
//        event.setTitle(title);
//        event.setDescription(description);
//        event.setCategory(categoryId);
//        event.setEventImagesUrl(eventImagesUrl);
//        event.setStartDate(startDate);
//        event.setEndDate(endDate);
//        event.setTicketPrice(ticketPrice);
//        event.setTotalTicket(totalTicket);
//        event.setAvailableTicket(availableTicket);
//        event.setEventStatus(eventStatus);
//        event.setCity(cityId);
//        event.setAddress(address);
//        return event;
//    }
}
