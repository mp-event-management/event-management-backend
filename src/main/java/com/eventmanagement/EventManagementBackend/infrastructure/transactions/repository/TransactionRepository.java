package com.eventmanagement.EventManagementBackend.infrastructure.transactions.repository;

import com.eventmanagement.EventManagementBackend.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
