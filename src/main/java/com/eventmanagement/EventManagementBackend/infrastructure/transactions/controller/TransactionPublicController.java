package com.eventmanagement.EventManagementBackend.infrastructure.transactions.controller;

import com.eventmanagement.EventManagementBackend.common.exceptions.DataNotFoundException;
import com.eventmanagement.EventManagementBackend.common.response.ApiResponse;
import com.eventmanagement.EventManagementBackend.entity.UsersAccount;
import com.eventmanagement.EventManagementBackend.infrastructure.transactions.dto.FilterTransactionDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.transactions.dto.PaginatedTransactionDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.transactions.dto.TransactionRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.transactions.dto.TransactionResponseDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.users.repository.UsersAccountRepository;
import com.eventmanagement.EventManagementBackend.usecase.transactions.TransactionsPublicUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionPublicController {

    private final TransactionsPublicUsecase transactionsPublicUsecase;
    private final UsersAccountRepository usersAccountRepository;

    public TransactionPublicController(TransactionsPublicUsecase transactionsPublicUsecase, UsersAccountRepository usersAccountRepository) {
        this.transactionsPublicUsecase = transactionsPublicUsecase;
        this.usersAccountRepository = usersAccountRepository;
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getAllTransactions(
            @PathVariable("customerId") Integer customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        // Validate page and size to ensure they are positive
        if (page < 0) page = 0;
        if (size <= 0) size = 10;
        int CUSTOMER_ROLE = 1;

        FilterTransactionDTO filterTransactionDTO = new FilterTransactionDTO();
        filterTransactionDTO.setPage(page);
        filterTransactionDTO.setSize(size);

        UsersAccount customer = usersAccountRepository.findById(customerId)
                .orElseThrow(() -> new DataNotFoundException("Customer not found"));

        if (customer.getRole().getRoleId() != CUSTOMER_ROLE) {
            throw new IllegalArgumentException("User is not an CUSTOMER");
        }

        // Use case method to fetch the paginated transactions for the customer
        PaginatedTransactionDTO<TransactionResponseDTO> paginatedTransactions =
                transactionsPublicUsecase.getAllTransactions(customerId, filterTransactionDTO);

        return ApiResponse.successfulResponse("Get all transactions by customer id successful", paginatedTransactions);
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
