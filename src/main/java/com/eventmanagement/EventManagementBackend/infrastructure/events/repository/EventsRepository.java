package com.eventmanagement.EventManagementBackend.infrastructure.events.repository;

import com.eventmanagement.EventManagementBackend.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsRepository extends JpaRepository<Event, Long> {
}
