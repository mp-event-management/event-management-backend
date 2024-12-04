package com.eventmanagement.EventManagementBackend.usecase.promotions.impl;

import com.eventmanagement.EventManagementBackend.common.exceptions.DataNotFoundException;
import com.eventmanagement.EventManagementBackend.entity.Event;
import com.eventmanagement.EventManagementBackend.entity.Promotion;
import com.eventmanagement.EventManagementBackend.entity.UsersAccount;
import com.eventmanagement.EventManagementBackend.infrastructure.events.repository.EventsRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.promotion.dto.PromotionDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.promotion.dto.PromotionRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.promotion.dto.UpdatePromotionRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.promotion.repository.PromotionRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.users.repository.UsersAccountRepository;
import com.eventmanagement.EventManagementBackend.usecase.promotions.PromotionsUsecase;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public PromotionDTO createPromotion(PromotionRequestDTO req) {
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

        // Map the promotion request to the promotion entity
        Promotion newPromotion = req.toEntity();

        // Define value for DATABASED type promotion
        if ("DATEBASED".equalsIgnoreCase(req.getPromotionType())) {
            newPromotion.setAvailableUses(null);
        }

        // Save the promotion
        Promotion savedPromotion = promotionRepository.save(newPromotion);

        return new PromotionDTO(
                savedPromotion.getPromotionId(),
                savedPromotion.getEvent().getEventId(),
                savedPromotion.getPromotionType(),
                savedPromotion.getPromotionCode(),
                savedPromotion.getDiscountPercentage(),
                savedPromotion.getAvailableUses(),
                savedPromotion.getStartDate(),
                savedPromotion.getEndDate()
        );
    }

    @Override
    public PromotionDTO updatePromotion(UpdatePromotionRequestDTO req) {
        // Fetch the existing promotion
        Promotion existingPromotion = promotionRepository.findById(req.getPromotionId())
                .orElseThrow(() -> new DataNotFoundException("Promotion not found"));

        // Fetch the associated event and validate it exists
        Integer eventId = req.getEvent().getEventId();
        Event existingEvent = eventsRepository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException("Event not found"));

        // Check if the organizer exists
        UsersAccount existingOrganizer = usersAccountRepository.findById(existingEvent.getUserOrganizer().getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid organizer ID"));

        // Validate the organizer's role
        if (existingOrganizer.getRole().getRoleId() != ORGANIZER_ROLE) {
            throw new IllegalArgumentException("Only organizer can update promotions");
        }

        // Update fields conditionally based on the incoming request
        Optional.ofNullable(req.getPromotionType()).ifPresent(existingPromotion::setPromotionType);
        Optional.ofNullable(req.getPromotionCode()).ifPresent(existingPromotion::setPromotionCode);
        Optional.ofNullable(req.getDiscountPercentage()).ifPresent(existingPromotion::setDiscountPercentage);
        Optional.ofNullable(req.getAvailableUses()).ifPresent(existingPromotion::setAvailableUses);
        Optional.ofNullable(req.getStartDate()).ifPresent(existingPromotion::setStartDate);
        Optional.ofNullable(req.getEndDate()).ifPresent(existingPromotion::setEndDate);

        // Validate the promotion type and availableUses
        if ("VOUCHER".equalsIgnoreCase(req.getPromotionType())) {
            // For VOUCHER type, availableUses must be provided
            if (req.getAvailableUses() == null || req.getAvailableUses() <= 0) {
                throw new IllegalArgumentException("VOUCHER promotion should have availableUses greater than 0");
            }
        } else if ("DATEBASED".equalsIgnoreCase(req.getPromotionType())) {
            if (req.getAvailableUses() != null) {
                throw new IllegalArgumentException("DATEBASED promotion should not be provided");
            }
        } else {
            throw new IllegalArgumentException("Invalid promotion type");
        }

        // Save the updated data
        Promotion updatedPromotion = promotionRepository.save(existingPromotion);

        return new PromotionDTO(
                updatedPromotion.getPromotionId(),
                updatedPromotion.getEvent().getEventId(),
                updatedPromotion.getPromotionType(),
                updatedPromotion.getPromotionCode(),
                updatedPromotion.getDiscountPercentage(),
                updatedPromotion.getAvailableUses(),
                updatedPromotion.getStartDate(),
                updatedPromotion.getEndDate()
        );
    }

    @Override
    public void deletePromotion(Integer promotionId) {
        promotionRepository.findById(promotionId).orElseThrow(() -> new DataNotFoundException("Promotion not found"));
        promotionRepository.deleteById(promotionId);
    }
}
