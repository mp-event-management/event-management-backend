package com.eventmanagement.EventManagementBackend.usecase.promotions;

import com.eventmanagement.EventManagementBackend.infrastructure.promotion.dto.CreatePromotionRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.promotion.dto.PromotionDTO;

public interface PromotionsUsecase {
    PromotionDTO getPromotions(String promotionId);

    PromotionDTO createPromotion(CreatePromotionRequestDTO req);

    PromotionDTO updatePromotion(PromotionDTO promotionDTO);

    PromotionDTO deletePromotion(PromotionDTO promotionDTO);
}
