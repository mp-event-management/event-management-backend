package com.eventmanagement.EventManagementBackend.infrastructure.promotion.controller;

import com.eventmanagement.EventManagementBackend.common.response.ApiResponse;
import com.eventmanagement.EventManagementBackend.infrastructure.promotion.dto.PromotionDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.promotion.dto.PromotionRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.promotion.dto.UpdatePromotionRequestDTO;
import com.eventmanagement.EventManagementBackend.usecase.promotions.PromotionsUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/promotions")
public class PromotionsPublicController {
    private final PromotionsUsecase promotionsUsecase;

    public PromotionsPublicController(PromotionsUsecase promotionsUsecase) {
        this.promotionsUsecase = promotionsUsecase;
    }

    @PostMapping
    public ResponseEntity<?> createPromotion(@RequestBody @Validated PromotionRequestDTO req) {
        PromotionDTO createPromotion = promotionsUsecase.createPromotion(req);
        return ApiResponse.successfulResponse("Create promotion successfully", createPromotion);
    }

    @PutMapping("/{promotionId}")
    public ResponseEntity<?> updatePromotion(@PathVariable Integer promotionId, @RequestBody @Validated UpdatePromotionRequestDTO req) {
        // Set the promotionId to ensure the correct entity is updated
        req.setPromotionId(promotionId);

        PromotionDTO updatedPromotion = promotionsUsecase.updatePromotion(req);
        return ApiResponse.successfulResponse("Update promotion successfully", updatedPromotion);
    }

    @DeleteMapping("/{promotionId}")
    public ResponseEntity<?> deletePromotion(@PathVariable @Validated Integer promotionId) {
        promotionsUsecase.deletePromotion(promotionId);
        return ApiResponse.successfulResponse("Delete promotion successfully");
    }
}
