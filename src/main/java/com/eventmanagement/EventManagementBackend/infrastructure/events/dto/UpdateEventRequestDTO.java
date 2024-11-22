package com.eventmanagement.EventManagementBackend.infrastructure.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequestDTO {
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
}
