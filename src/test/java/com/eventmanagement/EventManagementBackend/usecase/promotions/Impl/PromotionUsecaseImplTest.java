package com.eventmanagement.EventManagementBackend.usecase.promotions.Impl;

import com.eventmanagement.EventManagementBackend.common.exceptions.DataNotFoundException;
import com.eventmanagement.EventManagementBackend.entity.*;
import com.eventmanagement.EventManagementBackend.infrastructure.events.repository.EventsRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.promotion.dto.PromotionDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.promotion.dto.PromotionRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.promotion.dto.UpdatePromotionRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.promotion.repository.PromotionRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.users.repository.UsersAccountRepository;
import com.eventmanagement.EventManagementBackend.usecase.promotions.impl.PromotionUsecaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PromotionUsecaseImplTest {


    @Mock
    private EventsRepository eventsRepository;

    @Mock
    private UsersAccountRepository usersAccountRepository;

    @Mock
    private PromotionRepository promotionRepository;

    @InjectMocks
    private PromotionUsecaseImpl promotionUsecase;

    private Event event;
    private UsersAccount organizer;
    private PromotionRequestDTO promotionRequestDTO;
    private UpdatePromotionRequestDTO updatePromotionRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Role role = new Role();
        role.setRoleId(2);
        role.setName("ORGANIZER");

        organizer = new UsersAccount();
        organizer.setUserId(1);
        organizer.setName("Organizer");
        organizer.setRole(role); // Role "ORGANIZER"

        Category category = new Category();
        category.setCategoryId(1);
        category.setName("Music");

        City city = new City();
        city.setCityId(1);
        city.setName("New York");

        event = new Event();
        event.setEventId(1);
        event.setTitle("Concert");
        event.setCategory(category);
        event.setCity(city);
        event.setUserOrganizer(organizer);
        event.setTotalTicket(100);
        event.setAvailableTicket(100);
        event.setEventImagesUrl("https://placehold.co/500x500");
        event.setAddress("Jakarta street");

        // Set up sample data for tests
        event = new Event();
        event.setEventId(1);
        event.setTitle("Concert");
        event.setDescription("Music");
        event.setCategory(category);
        event.setEventImagesUrl("https://placehold.co/500x500");
        event.setStartDate(OffsetDateTime.parse("2024-12-12T18:00:00Z"));
        event.setEndDate(OffsetDateTime.parse("2024-12-13T18:00:00Z"));
        event.setTicketPrice(BigDecimal.valueOf(200000));
        event.setUserOrganizer(organizer);
        event.setCity(city);
        event.setAddress("Jakarta street");
        event.setEventStatus("UPCOMING");
        event.setTotalTicket(100);
        event.setAvailableTicket(100);

        organizer = new UsersAccount();
        organizer.setUserId(1);
        organizer.setRole(role); // Organizer role

        promotionRequestDTO = new PromotionRequestDTO();
        promotionRequestDTO.setEvent(event); // Event ID 1
        promotionRequestDTO.setPromotionType("VOUCHER");
        promotionRequestDTO.setAvailableUses(10);
        promotionRequestDTO.setPromotionCode("PROMO2024");
        promotionRequestDTO.setStartDate(OffsetDateTime.parse("2024-12-12T18:00:00Z"));
        promotionRequestDTO.setEndDate(OffsetDateTime.parse("2024-12-13T18:00:00Z"));
        promotionRequestDTO.setDiscountPercentage(BigDecimal.valueOf(0.02));

        updatePromotionRequestDTO = new UpdatePromotionRequestDTO();
        updatePromotionRequestDTO.setPromotionId(1);
        updatePromotionRequestDTO.setPromotionCode("PROMO2025");
        updatePromotionRequestDTO.setDiscountPercentage(BigDecimal.valueOf(0.05));
        updatePromotionRequestDTO.setEvent(event);
    }

    @Test
    void testCreatePromotion_Valid() {
        // Set up a mock Promotion object to simulate the saved entity
        Promotion savedPromotion = new Promotion();
        savedPromotion.setPromotionId(1);
        savedPromotion.setPromotionCode("PROMO2024");
        savedPromotion.setPromotionType("VOUCHER");
        savedPromotion.setAvailableUses(10);
        savedPromotion.setDiscountPercentage(BigDecimal.valueOf(0.02));
        savedPromotion.setStartDate(OffsetDateTime.parse("2024-12-12T18:00:00Z"));
        savedPromotion.setEndDate(OffsetDateTime.parse("2024-12-13T18:00:00Z"));
        savedPromotion.setEvent(event); // Associate the mock event

        // Mock repository behavior
        when(eventsRepository.findById(1)).thenReturn(Optional.of(event));
        when(usersAccountRepository.findById(1)).thenReturn(Optional.of(organizer));
        when(promotionRepository.save(any(Promotion.class))).thenReturn(savedPromotion);

        // Call the method
        PromotionDTO result = promotionUsecase.createPromotion(promotionRequestDTO);

        // Validate the result
        assertNotNull(result);
        assertEquals("PROMO2024", result.getPromotionCode());
        assertEquals(1, result.getPromotionId());
        assertEquals(event.getEventId(), result.getEventId());
        verify(eventsRepository, times(1)).findById(1);
        verify(usersAccountRepository, times(1)).findById(1);
        verify(promotionRepository, times(1)).save(any(Promotion.class));

    }

    @Test
    void testCreatePromotion_InvalidOrganizerRole() {
        // Mock the scenario where the organizer has an invalid role
        Role invalidRole = new Role();
        invalidRole.setRoleId(3); // Setting an invalid role ID (not the organizer role)
        organizer.setRole(invalidRole); // Assign the invalid role to the organizer

        when(eventsRepository.findById(1)).thenReturn(Optional.of(event));
        when(usersAccountRepository.findById(1)).thenReturn(Optional.of(organizer));

        // Call the method and assert exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            promotionUsecase.createPromotion(promotionRequestDTO);
        });

        assertEquals("Only organizer can create promotions", exception.getMessage());
    }

    @Test
    void testUpdatePromotion_Valid() {
        // Mock existing promotion
        Promotion existingPromotion = new Promotion();
        existingPromotion.setPromotionId(1);
        existingPromotion.setPromotionCode("PROMO2024");
        existingPromotion.setPromotionType("VOUCHER"); // Valid type
        existingPromotion.setAvailableUses(10); // Ensure availableUses is set for VOUCHER type
        existingPromotion.setEvent(event);

        // Ensure the event has the correct organizer
        event.setUserOrganizer(organizer);

        when(promotionRepository.findById(1)).thenReturn(Optional.of(existingPromotion));
        when(eventsRepository.findById(1)).thenReturn(Optional.of(event));
        when(usersAccountRepository.findById(organizer.getUserId())).thenReturn(Optional.of(organizer));
        when(promotionRepository.save(any(Promotion.class))).thenAnswer(invocation -> {
            Promotion updatedPromotion = invocation.getArgument(0);
            updatedPromotion.setPromotionId(1); // ID remains the same
            return updatedPromotion;
        });

        updatePromotionRequestDTO.setPromotionId(1);
        updatePromotionRequestDTO.setPromotionCode("PROMO2025");
        updatePromotionRequestDTO.setPromotionType("VOUCHER"); // Valid type
        updatePromotionRequestDTO.setAvailableUses(10); // Set availableUses to a valid value

        PromotionDTO result = promotionUsecase.updatePromotion(updatePromotionRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("PROMO2025", result.getPromotionCode()); // Ensure code is updated
        assertEquals(1, result.getPromotionId()); // Ensure ID is unchanged
        assertEquals(event.getEventId(), result.getEventId()); // Ensure event association

        // Verify repository interactions
        verify(promotionRepository, times(1)).findById(1);
        verify(eventsRepository, times(1)).findById(1);
        verify(promotionRepository, times(1)).save(any(Promotion.class));
    }

    @Test
    void testDeletePromotion_Valid() {
        // Mock the scenario where the promotion exists
        when(promotionRepository.findById(1)).thenReturn(Optional.of(new Promotion()));

        // Call the method
        promotionUsecase.deletePromotion(1);

        // Verify deletion
        verify(promotionRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeletePromotion_NotFound() {
        // Mock the scenario where the promotion does not exist
        when(promotionRepository.findById(1)).thenReturn(Optional.empty());

        // Call the method and assert exception
        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            promotionUsecase.deletePromotion(1);
        });

        assertEquals("Promotion not found", exception.getMessage());
    }

}
