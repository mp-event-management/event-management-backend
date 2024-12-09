package com.eventmanagement.EventManagementBackend.infrastructure.promotion.repository;

import com.eventmanagement.EventManagementBackend.entity.Event;
import com.eventmanagement.EventManagementBackend.entity.Promotion;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
//    List<Promotion> findAllByEventAndPromotionCodeIn(List<String> promotionCodes, Event event);

    @Query("SELECT p FROM Promotion p WHERE p.event = :event AND p.promotionCode IN :promotionCodes")
    List<Promotion> findAllByEventAndPromotionCodeIn(@Param("event") Event event, @Param("promotionCodes") List<String> promotionCodes);


    //    @Query("SELECT p FROM Promotion p WHERE p.event.eventId = :eventId AND p.promotionCode IN :promotionCodes")
//    List<Promotion> findAllByEventIdAndPromotionCodeIn(@Param("eventId") Integer eventId, @Param("promotionCodes") List<String> promotionCodes);
    Optional<Promotion> findByPromotionCode(String promotionCode);
}
