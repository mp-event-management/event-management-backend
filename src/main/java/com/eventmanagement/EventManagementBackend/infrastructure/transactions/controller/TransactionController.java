package com.eventmanagement.EventManagementBackend.infrastructure.transactions.controller;

import com.eventmanagement.EventManagementBackend.common.response.ApiResponse;
import com.eventmanagement.EventManagementBackend.infrastructure.transactions.dto.TransactionRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.transactions.dto.TransactionResponseDTO;
import com.eventmanagement.EventManagementBackend.usecase.transactions.TransactionsPublicUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionsPublicUsecase transactionsPublicUsecase;

    public TransactionController(TransactionsPublicUsecase transactionsPublicUsecase) {
        this.transactionsPublicUsecase = transactionsPublicUsecase;
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<?> getTransactionById(@PathVariable Integer transactionId) {
        return ApiResponse.successfulResponse("Get transaction by id successful", transactionsPublicUsecase.getTransactionById(transactionId));
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody @Validated TransactionRequestDTO request) {
        TransactionResponseDTO newTransaction = transactionsPublicUsecase.createTransaction(request);
        return ApiResponse.successfulResponse("Purchase ticket successful", newTransaction);
    }
}
