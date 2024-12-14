package com.eventmanagement.EventManagementBackend.infrastructure.users.repository;

import com.eventmanagement.EventManagementBackend.entity.UsersAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersAccountRepository extends JpaRepository<UsersAccount, Integer> {
    Optional<UsersAccount> findByEmailContainingIgnoreCase(String email);
    Optional<UsersAccount> findByReferralCode(String referralCode);
}
