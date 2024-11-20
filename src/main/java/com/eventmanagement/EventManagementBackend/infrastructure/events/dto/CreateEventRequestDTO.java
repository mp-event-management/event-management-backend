package com.eventmanagement.EventManagementBackend.infrastructure.events.dto;

import com.eventmanagement.EventManagementBackend.entity.Category;
import com.eventmanagement.EventManagementBackend.entity.City;
import com.eventmanagement.EventManagementBackend.entity.Event;
import com.eventmanagement.EventManagementBackend.entity.UsersAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventRequestDTO {
//    private UsersAccount userOrganizerId;
    private Long userOrganizerId;
    private String title;
    private String description;
//    private Category categoryId;
    private Long categoryId;
    private String eventImagesUrl;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private BigDecimal ticketPrice;
    private Integer totalTicket;
    private Integer availableTicket;
    private String eventStatus;
//    private City cityId;
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
