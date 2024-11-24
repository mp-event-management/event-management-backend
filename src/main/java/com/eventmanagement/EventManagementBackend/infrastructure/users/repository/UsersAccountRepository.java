package com.eventmanagement.EventManagementBackend.infrastructure.users.repository;

import com.eventmanagement.EventManagementBackend.entity.UsersAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersAccountRepository extends JpaRepository<UsersAccount, Integer> {
}
