package com.eventmanagement.EventManagementBackend.infrastructure.cities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {
    private Integer cityId;
    private String cityName;
}
