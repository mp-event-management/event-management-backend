package com.eventmanagement.EventManagementBackend.usecase.eventReviews.impl;

import com.eventmanagement.EventManagementBackend.common.exceptions.DataNotFoundException;
import com.eventmanagement.EventManagementBackend.entity.Event;
import com.eventmanagement.EventManagementBackend.entity.EventReview;
import com.eventmanagement.EventManagementBackend.entity.UsersAccount;
import com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.dto.EventReviewsRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.dto.EventReviewsResponseDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.dto.FilterEventReviewsDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.dto.PaginatedEventReviewsDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.eventReviews.repository.EventReviewsRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.events.repository.EventsRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.tickets.repository.TicketsRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.users.repository.UsersAccountRepository;
import com.eventmanagement.EventManagementBackend.usecase.eventReviews.EventReviewsPublicUsecase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        boolean hasValidTicket = ticketsRepository.existsByEventEventIdAndCustomerUserId(request.getEventId(), request.getCustomerId());

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

    @Override
    public PaginatedEventReviewsDTO<EventReviewsResponseDTO> getAllReviewsByEventId(Integer eventId, FilterEventReviewsDTO filter) {
        // Validate page and size parameters
        int page = Math.max(filter.getPage(), 0);
        int size = filter.getSize() <= 0 ? 10 : filter.getSize();

        // Fetch transactions with pagination
        Pageable pageable = PageRequest.of(page, size, Sort.by("eventReviewId").descending());

        Page<EventReview> eventReviewPage = eventReviewsRepository.findEventReviewByEventEventId(eventId, pageable);

        // Convert the page transaction into a list
        List<EventReviewsResponseDTO> response = eventReviewPage.getContent().stream()
                .map(review -> new EventReviewsResponseDTO(
                        review.getEventReviewId(),
                        review.getEvent().getEventId(),
                        review.getUser().getUserId(),
                        review.getRating(),
                        review.getReviewText()
                ))
                .collect(Collectors.toList());

        return new PaginatedEventReviewsDTO<>(
                response,
                eventReviewPage.getTotalElements(),
                eventReviewPage.getTotalPages(),
                eventReviewPage.getNumber()
        );
    }
}
