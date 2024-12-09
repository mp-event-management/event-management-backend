package com.eventmanagement.EventManagementBackend.infrastructure.transactions.controller;

import com.eventmanagement.EventManagementBackend.common.response.ApiResponse;
import com.eventmanagement.EventManagementBackend.infrastructure.transactions.dto.TransactionRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.transactions.dto.TransactionResponseDTO;
import com.eventmanagement.EventManagementBackend.usecase.transactions.TransactionsPublicUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionsPublicUsecase transactionsPublicUsecase;

    public TransactionController(TransactionsPublicUsecase transactionsPublicUsecase) {
        this.transactionsPublicUsecase = transactionsPublicUsecase;
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody @Validated TransactionRequestDTO request) {
//        try {
        // Delegate to the service layer
        TransactionResponseDTO newTransaction = transactionsPublicUsecase.createTransaction(request);
        return ApiResponse.successfulResponse("Purchase ticket successful", newTransaction);
//        } catch (IllegalStateException e) {
//            // Handle application specific issues (e.g. tickets already sold out or event already ended
//            return ApiResponse.failedResponse("Payment failed, tickets already sold out or the event already done");
//        } catch (Exception e) {
//            // Handle unexpected errors
//            return ApiResponse.failedResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Payment failed");
//        }
    }
}
