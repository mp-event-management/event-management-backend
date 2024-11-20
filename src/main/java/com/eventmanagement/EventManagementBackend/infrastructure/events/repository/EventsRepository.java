package com.eventmanagement.EventManagementBackend.infrastructure.events.repository;

import com.eventmanagement.EventManagementBackend.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventsRepository extends JpaRepository<Event, Integer> {
    @Query("SELECT e FROM Event e JOIN FETCH e.category JOIN FETCH e.userOrganizer JOIN FETCH e.city")
    List<Event> findAllEvents();
}
