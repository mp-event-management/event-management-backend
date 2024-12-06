package com.eventmanagement.EventManagementBackend.infrastructure.events.repository;

import com.eventmanagement.EventManagementBackend.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventsRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {
    @Query("SELECT e FROM Event e JOIN FETCH e.category JOIN FETCH e.userOrganizer JOIN FETCH e.city")
    List<Event> findAllEvents();

    // Custom method to find events by the userOrganizerId
    Page<Event> findEventByUserOrganizerUserId(Integer userId, Pageable pageable);
}
