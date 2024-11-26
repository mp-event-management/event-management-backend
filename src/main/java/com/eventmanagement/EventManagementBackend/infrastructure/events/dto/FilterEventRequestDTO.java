package com.eventmanagement.EventManagementBackend.infrastructure.events.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterEventRequestDTO {
    private Integer categoryId;
    private Integer cityId;
    private String search;

    // Define default value
    @Min(value = 0, message = "Page must be 0 or greater")
    private int page = 0;

    @Min(value = 1, message = "Size must be 1 or greater")
    private int size = 10;
}
