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
public class GetEventsRequestDTO {
    private Integer eventId;
    private Integer userOrganizerId;
    private String title;
    private String description;
    private CategoryDTO category;
    private String eventImagesUrl;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private BigDecimal ticketPrice;
    private int totalTicket;
    private int availableTicket;
    private String eventStatus;
    private CityDTO cityId;
    private String address;

}
