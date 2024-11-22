package com.eventmanagement.EventManagementBackend.infrastructure.events.dto;

import com.eventmanagement.EventManagementBackend.entity.Category;
import com.eventmanagement.EventManagementBackend.entity.City;
import com.eventmanagement.EventManagementBackend.entity.Event;
import com.eventmanagement.EventManagementBackend.entity.UsersAccount;
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

    public Event toEntity(UsersAccount usersAccount, Category category, City city) {
        Event event = new Event();
        event.setUserOrganizer(usersAccount);
        event.setTitle(title);
        event.setDescription(description);
        event.setCategory(category);
        event.setEventImagesUrl(eventImagesUrl);
        event.setStartDate(startDate);
        event.setEndDate(endDate);
        event.setTicketPrice(ticketPrice);
        event.setTotalTicket(totalTicket);
        event.setAvailableTicket(availableTicket);
        event.setEventStatus(eventStatus);
        event.setCity(city);
        event.setAddress(address);
        return event;
    }
}
