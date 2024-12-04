package com.eventmanagement.EventManagementBackend.infrastructure.promotion.controller;

import com.eventmanagement.EventManagementBackend.common.response.ApiResponse;
import com.eventmanagement.EventManagementBackend.infrastructure.promotion.dto.CreatePromotionRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.promotion.dto.PromotionDTO;
import com.eventmanagement.EventManagementBackend.usecase.promotions.PromotionsUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/promotions")
public class PromotionsPublicController {
    private final PromotionsUsecase promotionsUsecase;

    public PromotionsPublicController(PromotionsUsecase promotionsUsecase) {
        this.promotionsUsecase = promotionsUsecase;
    }

    @PostMapping
    public ResponseEntity<?> createPromotion(@RequestBody @Validated CreatePromotionRequestDTO req) {
        PromotionDTO createPromotion = promotionsUsecase.createPromotion(req);
        return ApiResponse.successfulResponse("Create promotion successfully");
    }
}
