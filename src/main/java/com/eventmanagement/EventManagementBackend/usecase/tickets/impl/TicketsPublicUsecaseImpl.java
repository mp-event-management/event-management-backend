package com.eventmanagement.EventManagementBackend.usecase.tickets.impl;

import com.eventmanagement.EventManagementBackend.common.exceptions.DataNotFoundException;
import com.eventmanagement.EventManagementBackend.entity.Ticket;
import com.eventmanagement.EventManagementBackend.infrastructure.tickets.dto.FilterTicketDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.tickets.dto.PaginatedTicketResponseDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.tickets.dto.TicketResponseDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.tickets.repository.TicketsRepository;
import com.eventmanagement.EventManagementBackend.usecase.tickets.TicketsPublicUsecase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketsPublicUsecaseImpl implements TicketsPublicUsecase {

    private final TicketsRepository ticketsRepository;

    public TicketsPublicUsecaseImpl(TicketsRepository ticketsRepository) {
        this.ticketsRepository = ticketsRepository;
    }

    @Override
    public PaginatedTicketResponseDTO<TicketResponseDTO> getAllTicketsByCustomerId(Integer customerId, FilterTicketDTO filter) {
        // Validate page and size parameters
        int page = Math.max(filter.getPage(), 0);
        int size = filter.getSize() <= 0 ? 10 : filter.getSize();

        // Fetch transactions with pagination
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Ticket> ticketPage = ticketsRepository.findByCustomer_UserId(customerId, pageable);

        // Convert the page transaction into a list
        List<TicketResponseDTO> response = ticketPage.getContent().stream()
                .map(ticket -> new TicketResponseDTO(
                        ticket.getId(),
                        ticket.getTransaction().getId(),
                        ticket.getEvent().getEventId(),
                        ticket.getCustomer().getUserId(),
                        ticket.getCustomer().getName(),
                        ticket.getEvent().getTitle(),
                        ticket.getEvent().getStartDate(),
                        ticket.getEvent().getEndDate(),
                        ticket.getTransaction().getTicketPrice(),
                        ticket.getStatus(),
                        ticket.getIssuedate(),
                        ticket.getValidUntil()
                ))
                .collect(Collectors.toList());

        return new PaginatedTicketResponseDTO<>(
                response,
                ticketPage.getTotalElements(),
                ticketPage.getTotalPages(),
                ticketPage.getNumber()
        );
    }

    @Override
    public TicketResponseDTO getTicketById(Integer ticketId) {
        Ticket ticket = ticketsRepository.findById(ticketId)
                .orElseThrow(() -> new DataNotFoundException("Ticket not found"));

        return new TicketResponseDTO(
                ticket.getId(),
                ticket.getTransaction().getId(),
                ticket.getEvent().getEventId(),
                ticket.getCustomer().getUserId(),
                ticket.getCustomer().getName(),
                ticket.getEvent().getTitle(),
                ticket.getEvent().getStartDate(),
                ticket.getEvent().getEndDate(),
                ticket.getTransaction().getTicketPrice(),
                ticket.getStatus(),
                ticket.getIssuedate(),
                ticket.getValidUntil()
        );
    }

}
