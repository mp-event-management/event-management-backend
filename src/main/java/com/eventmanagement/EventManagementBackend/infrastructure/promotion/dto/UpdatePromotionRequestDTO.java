package com.eventmanagement.EventManagementBackend.infrastructure.promotion.dto;

import com.eventmanagement.EventManagementBackend.entity.Event;
import com.eventmanagement.EventManagementBackend.entity.Promotion;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePromotionRequestDTO {
    private Integer promotionId;

    @NotNull
    private Event event;

    private String promotionType; // can be 'VOUCHER' or 'DATEBASED'

    private String promotionCode;

    private BigDecimal discountPercentage;

    private Integer availableUses;

    private OffsetDateTime startDate;

    private OffsetDateTime endDate;

    public Promotion toEntity() {
        Promotion promotion = new Promotion();
        promotion.setPromotionId(promotionId);
        promotion.setEvent(event);
        promotion.setPromotionType(promotionType);
        promotion.setPromotionCode(promotionCode);
        promotion.setDiscountPercentage(discountPercentage);
        promotion.setAvailableUses(availableUses);
        promotion.setStartDate(startDate);
        promotion.setEndDate(endDate);
        return promotion;
    }
}
