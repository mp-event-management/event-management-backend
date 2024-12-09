package com.eventmanagement.EventManagementBackend.usecase.eventReviews.impl;

import com.eventmanagement.EventManagementBackend.common.exceptions.DataNotFoundException;
import com.eventmanagement.EventManagementBackend.entity.Event;
import com.eventmanagement.EventManagementBackend.entity.EventReview;
import com.eventmanagement.EventManagementBackend.entity.UsersAccount;
import com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.dto.EventReviewsRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.dto.EventReviewsResponseDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.repository.EventReviewsRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.events.repository.EventsRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.tickets.repository.TicketsRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.users.repository.UsersAccountRepository;
import com.eventmanagement.EventManagementBackend.usecase.eventReviews.EventReviewsPublicUsecase;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class EventReviewsPublicUsecaseImpl implements EventReviewsPublicUsecase {

    private final EventReviewsRepository eventReviewsRepository;
    private final UsersAccountRepository usersAccountRepository;
    private final EventsRepository eventsRepository;
    private final TicketsRepository ticketsRepository;

    public EventReviewsPublicUsecaseImpl(EventReviewsRepository eventReviewsRepository, UsersAccountRepository usersAccountRepository, EventsRepository eventsRepository, TicketsRepository ticketsRepository, TicketsRepository ticketsRepository1) {
        this.eventReviewsRepository = eventReviewsRepository;
        this.usersAccountRepository = usersAccountRepository;
        this.eventsRepository = eventsRepository;
        this.ticketsRepository = ticketsRepository1;
    }

    @Override
    public EventReviewsResponseDTO createEventReview(EventReviewsRequestDTO request) {

        UsersAccount customer = usersAccountRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new DataNotFoundException("Customer not found"));

        Event event = eventsRepository.findById(request.getEventId())
                .orElseThrow(() -> new DataNotFoundException("Event not found"));

        // Validate user role (only customer)
        if (!"CUSTOMER".equals(customer.getRole().getName())) {
            throw new DataNotFoundException("Only customer can review event");
        }

        // check if the event still ongoing or not done
        if (event.getEndDate().isAfter(OffsetDateTime.now())) {
            throw new IllegalStateException("Event not done yet");
        }

        // Check if have any ticket for the event
        boolean hasValidTicket = ticketsRepository.existsByCustomerUserIdAndEvent_EventId(request.getEventId(), request.getCustomerId());

        if (!hasValidTicket) throw new DataNotFoundException("Ticket not for this event");

        // Check if the customer already give a review or not
        boolean hasReviewed = eventReviewsRepository.existsByEventEventIdAndUserUserId(request.getEventId(), request.getCustomerId());

        if (hasReviewed) throw new DataNotFoundException("Already reviewed this event");

        EventReview eventReview = new EventReview();
        eventReview.setEvent(event);
        eventReview.setUser(customer);
        eventReview.setRating(request.getRating());
        eventReview.setReviewText(request.getReviewText());
        eventReviewsRepository.save(eventReview);

        return new EventReviewsResponseDTO(
                eventReview.getEventReviewId(),
                eventReview.getEvent().getEventId(),
                eventReview.getUser().getUserId(),
                eventReview.getRating(),
                eventReview.getReviewText()
        );
    }
}
