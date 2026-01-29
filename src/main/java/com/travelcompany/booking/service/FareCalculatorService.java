package com.travelcompany.booking.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelcompany.booking.dto.FareCalculationDto;
import com.travelcompany.booking.model.CabType;
import com.travelcompany.booking.repository.CabTypeRepository;

/**
 * Service class for Fare calculation
 * Handles business logic for fare calculation based on distance, time, and cab type
 */
@Service
public class FareCalculatorService {

    private final CabTypeRepository cabTypeRepository;

    @Autowired
    public FareCalculatorService(CabTypeRepository cabTypeRepository) {
        this.cabTypeRepository = cabTypeRepository;
    }

    /**
     * Calculate fare for a trip
     */
    public BigDecimal calculateFare(String pickupLocation, String destination, String cabTypeName) {
        // Find cab type
        CabType cabType = cabTypeRepository.findByName(cabTypeName)
            .orElseThrow(() -> new RuntimeException("Cab type not found: " + cabTypeName));

        // Simulate distance calculation (in real app, use maps API)
        BigDecimal distanceKm = simulateDistance(pickupLocation, destination);

        // Calculate fare components
        BigDecimal baseFare = cabType.getBaseFare();
        BigDecimal distanceFare = distanceKm.multiply(cabType.getPerKmRate());
        BigDecimal timeFare = calculateTimeFare(); // Surge pricing based on time

        BigDecimal totalFare = baseFare.add(distanceFare).add(timeFare);

        return totalFare.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculate detailed fare breakdown
     */
    public FareCalculationDto calculateDetailedFare(String pickupLocation, String destination, String cabTypeName) {
        CabType cabType = cabTypeRepository.findByName(cabTypeName)
            .orElseThrow(() -> new RuntimeException("Cab type not found: " + cabTypeName));

        BigDecimal distanceKm = simulateDistance(pickupLocation, destination);

        FareCalculationDto fareDto = new FareCalculationDto(pickupLocation, destination, cabTypeName, distanceKm);

        fareDto.setBaseFare(cabType.getBaseFare());
        fareDto.setDistanceFare(distanceKm.multiply(cabType.getPerKmRate()));
        fareDto.setTimeFare(calculateTimeFare());

        BigDecimal totalFare = fareDto.getBaseFare()
            .add(fareDto.getDistanceFare())
            .add(fareDto.getTimeFare());

        fareDto.setTotalFare(totalFare.setScale(2, RoundingMode.HALF_UP));

        return fareDto;
    }

    /**
     * Simulate distance calculation
     * In real application, integrate with Google Maps API or similar service
     */
    private BigDecimal simulateDistance(String pickup, String destination) {
        // Simple simulation based on string hash (for demo purposes)
        int hash = Math.abs((pickup + destination).hashCode());
        double distance = 2.0 + (hash % 20); // Distance between 2-22 km
        return BigDecimal.valueOf(distance).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculate time-based fare (surge pricing)
     */
    private BigDecimal calculateTimeFare() {
        // Simple surge pricing simulation
        java.time.LocalTime now = java.time.LocalTime.now();
        int hour = now.getHour();

        // Peak hours: 7-9 AM and 6-8 PM
        if ((hour >= 7 && hour <= 9) || (hour >= 18 && hour <= 20)) {
            return BigDecimal.valueOf(5.00); // Surge pricing
        } else {
            return BigDecimal.ZERO; // No surge pricing
        }
    }

    /**
     * Get estimated time for trip
     */
    public int getEstimatedTripTime(String pickupLocation, String destination) {
        BigDecimal distance = simulateDistance(pickupLocation, destination);
        // Assume average speed of 30 km/h in city
        return distance.multiply(BigDecimal.valueOf(2)).intValue(); // minutes
    }
}