package com.eventmanagement.EventManagementBackend.infrastructure.referralCode;

import com.eventmanagement.EventManagementBackend.entity.ReferralCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReferralCodeRepository extends JpaRepository<ReferralCode, Integer> {
    boolean existsByReferralCodeId(String referralCodeId);
    Optional<ReferralCode> findByReferralCodeId(String referralCodeId);
}
