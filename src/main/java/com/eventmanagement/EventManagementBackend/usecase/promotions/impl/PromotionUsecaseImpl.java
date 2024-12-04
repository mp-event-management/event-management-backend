package com.eventmanagement.EventManagementBackend.usecase.promotions.impl;

import com.eventmanagement.EventManagementBackend.common.exceptions.DataNotFoundException;
import com.eventmanagement.EventManagementBackend.entity.Event;
import com.eventmanagement.EventManagementBackend.entity.Promotion;
import com.eventmanagement.EventManagementBackend.entity.UsersAccount;
import com.eventmanagement.EventManagementBackend.infrastructure.events.repository.EventsRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.promotion.dto.CreatePromotionRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.promotion.dto.PromotionDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.promotion.repository.PromotionRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.users.repository.UsersAccountRepository;
import com.eventmanagement.EventManagementBackend.usecase.promotions.PromotionsUsecase;
import org.springframework.stereotype.Service;

@Service
public class PromotionUsecaseImpl implements PromotionsUsecase {
    private static final int ORGANIZER_ROLE = 2;
    private final EventsRepository eventsRepository;
    private final UsersAccountRepository usersAccountRepository;
    private final PromotionRepository promotionRepository;

    public PromotionUsecaseImpl(EventsRepository eventsRepository, UsersAccountRepository usersAccountRepository, PromotionRepository promotionRepository) {
        this.eventsRepository = eventsRepository;
        this.usersAccountRepository = usersAccountRepository;
        this.promotionRepository = promotionRepository;
    }

    @Override
    public PromotionDTO getPromotions(String promotionId) {
        return null;
    }

    @Override
    public PromotionDTO createPromotion(CreatePromotionRequestDTO req) {
        Integer eventId = req.getEvent().getEventId();

        // Check if the event exists
        Event event = eventsRepository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException("Event not found"));

        // Check if the organizer exists
        UsersAccount organizer = usersAccountRepository.findById(event.getUserOrganizer().getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid organizer ID"));

        // Validate the organizer's role
        if (organizer.getRole().getRoleId() != ORGANIZER_ROLE) {
            throw new IllegalArgumentException("Only organizer can create promotions");
        }

        // Check the promotion type and validate availableUses
        if ("VOUCHER".equalsIgnoreCase(req.getPromotionType())) {
            // For VOUCHER type, availableUses must be provided
            if (req.getAvailableUses() == null || req.getAvailableUses() <= 0) {
                throw new IllegalArgumentException("VOUCHER promotion should have availableUses greater than 0");
            }
        } else if ("DATEBASED".equalsIgnoreCase(req.getPromotionType())) {
            // For DATEBASED type, availableUses should not be included or null
            if (req.getAvailableUses() != null) {
                throw new IllegalArgumentException("DATEBASED promotion should not be provided");
            }
        } else {
            throw new IllegalArgumentException("Invalid promotion type");
        }

        // Validate if the event is created by the exact same organizer
//        if (!event.getUserOrganizer().getUserId().equals(event.getUserOrganizer().getUserId())) {
//            throw new IllegalArgumentException("The event does not belong to the organizer");
//        }

        // Map the promotion request to the promotion entity
        Promotion newPromotion = req.toEntity();

        // Define value for DATABASED type promotion
        if ("DATABASED".equalsIgnoreCase(req.getPromotionType())) {
            newPromotion.setAvailableUses(null);
        }

        // Save the promotion
        Promotion savedPromotion = promotionRepository.save(newPromotion);

        return new PromotionDTO(
                savedPromotion.getPromotionId(),
                savedPromotion.getEvent(),
                savedPromotion.getPromotionType(),
                savedPromotion.getPromotionCode(),
                savedPromotion.getDiscountPercentage(),
                savedPromotion.getAvailableUses(),
                savedPromotion.getStartDate(),
                savedPromotion.getEndDate()
        );
    }

    @Override
    public PromotionDTO updatePromotion(PromotionDTO promotionDTO) {
        return null;
    }

    @Override
    public PromotionDTO deletePromotion(PromotionDTO promotionDTO) {
        return null;
    }
}
