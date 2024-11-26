package com.eventmanagement.EventManagementBackend.infrastructure.events.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventResponseDTO {
    @NotNull
    private Integer userOrganizerId;

    private String title;
    private String description;

    @NotNull
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
