package com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.repository;

import com.eventmanagement.EventManagementBackend.entity.EventReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventReviewsRepository extends JpaRepository<EventReview, Integer> {
    Boolean existsByEventEventIdAndUserUserId(Integer eventId, Integer customerId);
}
