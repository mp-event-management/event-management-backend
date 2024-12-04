package com.eventmanagement.EventManagementBackend.infrastructure.promotion.dto;

import com.eventmanagement.EventManagementBackend.entity.Event;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionDTO {

    private Integer promotionId;

    @NotNull
    private Event eventId;

    @NotNull
    private String promotionType;

    @NotNull
    private String promotionCode;

    private BigDecimal discountPercentage;

    private Integer availableUses;

    @NotNull
    private OffsetDateTime startDate;

    @NotNull
    private OffsetDateTime endDate;


}
