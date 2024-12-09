package com.eventmanagement.EventManagementBackend.infrastructure.referralPoints.repository;

import com.eventmanagement.EventManagementBackend.entity.ReferralPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferralPointsRepository extends JpaRepository<ReferralPoint, Integer> {
    List<ReferralPoint> findByUser_UserId(Integer userId);
}
