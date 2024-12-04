package com.eventmanagement.EventManagementBackend.usecase.promotions;

import com.eventmanagement.EventManagementBackend.infrastructure.promotion.dto.PromotionDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.promotion.dto.PromotionRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.promotion.dto.UpdatePromotionRequestDTO;

public interface PromotionsUsecase {
    PromotionDTO getPromotions(String promotionId);

    PromotionDTO createPromotion(PromotionRequestDTO req);

    PromotionDTO updatePromotion(UpdatePromotionRequestDTO req);

    void deletePromotion(Integer id);
}
