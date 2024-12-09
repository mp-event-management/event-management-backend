package com.eventmanagement.EventManagementBackend.infrastructure.transactions.repository;

import com.eventmanagement.EventManagementBackend.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Page<Transaction> findByCustomer_UserId(int customerId, Pageable pageable);
}
