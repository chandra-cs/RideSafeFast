package com.travelcompany.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * Data Transfer Object for Fare calculations
 */
public class FareCalculationDto {

    @NotBlank(message = "Pickup location is required")
    private String pickupLocation;

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotBlank(message = "Cab type is required")
    private String cabType;

    @Positive(message = "Distance must be positive")
    private BigDecimal distanceKm;

    private BigDecimal baseFare;
    private BigDecimal distanceFare;
    private BigDecimal timeFare;
    private BigDecimal totalFare;

    public FareCalculationDto() {}

    public FareCalculationDto(String pickupLocation, String destination, String cabType, BigDecimal distanceKm) {
        this.pickupLocation = pickupLocation;
        this.destination = destination;
        this.cabType = cabType;
        this.distanceKm = distanceKm;
    }

    // Getters and Setters
    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getCabType() { return cabType; }
    public void setCabType(String cabType) { this.cabType = cabType; }

    public BigDecimal getDistanceKm() { return distanceKm; }
    public void setDistanceKm(BigDecimal distanceKm) { this.distanceKm = distanceKm; }

    public BigDecimal getBaseFare() { return baseFare; }
    public void setBaseFare(BigDecimal baseFare) { this.baseFare = baseFare; }

    public BigDecimal getDistanceFare() { return distanceFare; }
    public void setDistanceFare(BigDecimal distanceFare) { this.distanceFare = distanceFare; }

    public BigDecimal getTimeFare() { return timeFare; }
    public void setTimeFare(BigDecimal timeFare) { this.timeFare = timeFare; }

    public BigDecimal getTotalFare() { return totalFare; }
    public void setTotalFare(BigDecimal totalFare) { this.totalFare = totalFare; }
}