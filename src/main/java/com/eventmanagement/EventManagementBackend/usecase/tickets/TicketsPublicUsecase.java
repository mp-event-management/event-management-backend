package com.eventmanagement.EventManagementBackend.usecase.tickets;

import com.eventmanagement.EventManagementBackend.infrastructure.tickets.dto.FilterTicketDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.tickets.dto.PaginatedTicketResponseDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.tickets.dto.TicketResponseDTO;

public interface TicketsPublicUsecase {

    PaginatedTicketResponseDTO<TicketResponseDTO> getAllTicketsByCustomerId(Integer customerId, FilterTicketDTO filter);

    TicketResponseDTO getTicketById(Integer ticketId);
}
