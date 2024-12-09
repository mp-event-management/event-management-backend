package com.eventmanagement.EventManagementBackend.usecase.transactions;

import com.eventmanagement.EventManagementBackend.infrastructure.transactions.dto.*;
import jakarta.transaction.Transactional;

public interface TransactionsPublicUsecase {
    @Transactional
    TransactionResponseDTO createTransaction(TransactionRequestDTO req);

    TransactionDetailsDTO getTransactionById(Integer transactionId);

    PaginatedTransactionDTO<TransactionResponseDTO> getAllTransactions(Integer customerId, FilterTransactionDTO filter);
}
