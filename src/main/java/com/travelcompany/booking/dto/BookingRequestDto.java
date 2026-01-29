package com.travelcompany.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Booking requests
 */
public class BookingRequestDto {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotBlank(message = "Pickup location is required")
    private String pickupLocation;

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotNull(message = "Preferred time is required")
    private LocalDateTime preferredTime;

    @NotBlank(message = "Cab type is required")
    private String cabType;

    public BookingRequestDto() {}

    public BookingRequestDto(Long customerId, String pickupLocation, String destination, 
                            LocalDateTime preferredTime, String cabType) {
        this.customerId = customerId;
        this.pickupLocation = pickupLocation;
        this.destination = destination;
        this.preferredTime = preferredTime;
        this.cabType = cabType;
    }

    // Getters and Setters
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public LocalDateTime getPreferredTime() { return preferredTime; }
    public void setPreferredTime(LocalDateTime preferredTime) { this.preferredTime = preferredTime; }

    public String getCabType() { return cabType; }
    public void setCabType(String cabType) { this.cabType = cabType; }
}