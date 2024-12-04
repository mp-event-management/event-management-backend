package com.eventmanagement.EventManagementBackend.infrastructure.promotion.repository;

import com.eventmanagement.EventManagementBackend.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
}
