package com.travelcompany.booking.service;

import com.travelcompany.booking.dto.FareCalculationDto;
import com.travelcompany.booking.model.CabType;
import com.travelcompany.booking.repository.CabTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

/**
 * Unit tests for FareCalculatorService
 */
@ExtendWith(MockitoExtension.class)
class FareCalculatorServiceTest {

    @Mock
    private CabTypeRepository cabTypeRepository;

    @InjectMocks
    private FareCalculatorService fareCalculatorService;

    private CabType economyCabType;

    @BeforeEach
    void setUp() {
        economyCabType = new CabType(
            "Economy",
            new BigDecimal("5.00"),
            new BigDecimal("1.50"),
            "Standard comfortable ride"
        );
        economyCabType.setId(1L);
    }

    @Test
    void testCalculateFare() {
        // Given
        given(cabTypeRepository.findByName("Economy")).willReturn(Optional.of(economyCabType));

        // When
        BigDecimal result = fareCalculatorService.calculateFare("123 Main St", "456 Oak Ave", "Economy");

        // Then
        assertNotNull(result);
        assertTrue(result.compareTo(BigDecimal.ZERO) > 0);
        // The exact amount will vary due to simulated distance calculation
        // but should include base fare (5.00) + distance fare + time fare
        assertTrue(result.compareTo(new BigDecimal("5.00")) >= 0);
    }

    @Test
    void testCalculateFareCabTypeNotFound() {
        // Given
        given(cabTypeRepository.findByName("NonExistent")).willReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            fareCalculatorService.calculateFare("123 Main St", "456 Oak Ave", "NonExistent");
        });
    }

    @Test
    void testCalculateDetailedFare() {
        // Given
        given(cabTypeRepository.findByName("Economy")).willReturn(Optional.of(economyCabType));

        // When
        FareCalculationDto result = fareCalculatorService.calculateDetailedFare("123 Main St", "456 Oak Ave", "Economy");

        // Then
        assertNotNull(result);
        assertEquals("123 Main St", result.getPickupLocation());
        assertEquals("456 Oak Ave", result.getDestination());
        assertEquals("Economy", result.getCabType());
        assertEquals(new BigDecimal("5.00"), result.getBaseFare());
        assertNotNull(result.getDistanceFare());
        assertNotNull(result.getTimeFare());
        assertNotNull(result.getTotalFare());
        assertTrue(result.getTotalFare().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testGetEstimatedTripTime() {
        // When
        int result = fareCalculatorService.getEstimatedTripTime("123 Main St", "456 Oak Ave");

        // Then
        assertTrue(result > 0);
        assertTrue(result <= 50); // Based on our simulation logic (max ~25km * 2)
    }
}