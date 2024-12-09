package com.eventmanagement.EventManagementBackend.infrastructure.tickets.controller;

import com.eventmanagement.EventManagementBackend.common.exceptions.DataNotFoundException;
import com.eventmanagement.EventManagementBackend.common.response.ApiResponse;
import com.eventmanagement.EventManagementBackend.entity.UsersAccount;
import com.eventmanagement.EventManagementBackend.infrastructure.tickets.dto.FilterTicketDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.tickets.dto.PaginatedTicketResponseDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.tickets.dto.TicketResponseDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.users.repository.UsersAccountRepository;
import com.eventmanagement.EventManagementBackend.usecase.tickets.TicketsPublicUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketsPublicController {

    private final TicketsPublicUsecase ticketsPublicUsecase;
    private final UsersAccountRepository usersAccountRepository;

    public TicketsPublicController(TicketsPublicUsecase ticketsPublicUsecase, UsersAccountRepository usersAccountRepository) {
        this.ticketsPublicUsecase = ticketsPublicUsecase;
        this.usersAccountRepository = usersAccountRepository;
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getAllTickets(
            @PathVariable("customerId") Integer customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        // Validate page and size to ensure they are positive
        if (page < 0) page = 0;
        if (size <= 0) size = 10;
        int CUSTOMER_ROLE = 1;

        FilterTicketDTO filterTicketDTO = new FilterTicketDTO();
        filterTicketDTO.setPage(page);
        filterTicketDTO.setSize(size);

        UsersAccount customer = usersAccountRepository.findById(customerId)
                .orElseThrow(() -> new DataNotFoundException("Customer not found"));

        if (customer.getRole().getRoleId() != CUSTOMER_ROLE) {
            throw new IllegalArgumentException("User is not an CUSTOMER");
        }

        // Use case method to fetch the paginated transactions for the customer
        PaginatedTicketResponseDTO<TicketResponseDTO> paginatedTickets =
                ticketsPublicUsecase.getAllTicketsByCustomerId(customerId, filterTicketDTO);

        return ApiResponse.successfulResponse("Get all tickets by customer id successful", paginatedTickets);

    }
}
