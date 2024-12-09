package com.eventmanagement.EventManagementBackend.usecase.transactions;

import com.eventmanagement.EventManagementBackend.infrastructure.transactions.dto.TransactionDetailsDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.transactions.dto.TransactionRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.transactions.dto.TransactionResponseDTO;
import jakarta.transaction.Transactional;

public interface TransactionsPublicUsecase {
    @Transactional
    TransactionResponseDTO createTransaction(TransactionRequestDTO req);
    TransactionDetailsDTO getTransactionById(Integer transactionId);
}
