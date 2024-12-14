package com.eventmanagement.EventManagementBackend.infrastructure.referralCode;

import com.eventmanagement.EventManagementBackend.entity.ReferralCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReferralCodeRepository extends JpaRepository<ReferralCode, Integer> {
    boolean existsByReferralCodeId(String referralCodeId);
    Optional<ReferralCode> findByReferralCodeId(String referralCodeId);

    @Modifying
    @Query("UPDATE ReferralCode r SET r.totalUsage = r.totalUsage + 1 WHERE r.referralCodeId = :code")
    int incrementUsage(@Param("code") String code);
}
