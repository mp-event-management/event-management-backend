package com.eventmanagement.EventManagementBackend.infrastructure.events.dto;

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
    private int page = 0;
    private int size = 10;
}
