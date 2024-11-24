package com.eventmanagement.EventManagementBackend.infrastructure.events.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEventRequestDTO {
    private Integer eventId;
    private String title;
    private String description;
    private Integer categoryId;
    private String eventImagesUrl;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private BigDecimal ticketPrice;
    private Integer totalTicket;
    private Integer availableTicket;
    private String eventStatus;
    private Integer cityId;
    private String address;
}
