package com.eventmanagement.EventManagementBackend.infrastructure.promotion.repository;

import com.eventmanagement.EventManagementBackend.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    Optional<Promotion> findByPromotionCode(String promotionCode);
}
