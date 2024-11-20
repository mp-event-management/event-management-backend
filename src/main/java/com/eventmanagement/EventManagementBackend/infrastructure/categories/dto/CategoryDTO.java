package com.eventmanagement.EventManagementBackend.infrastructure.categories.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Integer categoryId;
    private String name;
    private String iconUrl;
}
