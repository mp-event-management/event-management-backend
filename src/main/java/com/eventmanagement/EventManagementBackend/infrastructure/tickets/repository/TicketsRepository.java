package com.eventmanagement.EventManagementBackend.infrastructure.tickets.repository;

import com.eventmanagement.EventManagementBackend.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketsRepository extends JpaRepository<Ticket, Integer> {
    Page<Ticket> findByCustomer_UserId(Integer eventId, Pageable pageable);

    boolean existsByEventEventIdAndCustomerUserId(Integer eventId, Integer customerId);
}
