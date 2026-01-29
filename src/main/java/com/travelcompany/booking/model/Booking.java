package com.travelcompany.booking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Booking entity representing a cab booking in the system
 */
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NotBlank(message = "Pickup location is required")
    @Column(name = "pickup_location", nullable = false)
    private String pickupLocation;

    @NotBlank(message = "Destination is required")
    @Column(nullable = false)
    private String destination;

    @Column(name = "booking_time")
    private LocalDateTime bookingTime;

    @NotNull(message = "Preferred time is required")
    @Column(name = "preferred_time")
    private LocalDateTime preferredTime;

    @NotBlank(message = "Cab type is required")
    @Column(name = "cab_type")
    private String cabType;

    @Positive(message = "Fare must be positive")
    @Column(precision = 10, scale = 2)
    private BigDecimal fare;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public enum BookingStatus {
        PENDING, CONFIRMED, IN_PROGRESS, COMPLETED, CANCELLED
    }

    public Booking() {
        this.bookingTime = LocalDateTime.now();
        this.createdDate = LocalDateTime.now();
        this.status = BookingStatus.PENDING;
    }

    public Booking(Customer customer, String pickupLocation, String destination, 
                   LocalDateTime preferredTime, String cabType, BigDecimal fare) {
        this();
        this.customer = customer;
        this.pickupLocation = pickupLocation;
        this.destination = destination;
        this.preferredTime = preferredTime;
        this.cabType = cabType;
        this.fare = fare;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }

    public LocalDateTime getPreferredTime() { return preferredTime; }
    public void setPreferredTime(LocalDateTime preferredTime) { this.preferredTime = preferredTime; }

    public String getCabType() { return cabType; }
    public void setCabType(String cabType) { this.cabType = cabType; }

    public BigDecimal getFare() { return fare; }
    public void setFare(BigDecimal fare) { this.fare = fare; }

    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", pickupLocation='" + pickupLocation + '\'' +
                ", destination='" + destination + '\'' +
                ", preferredTime=" + preferredTime +
                ", cabType='" + cabType + '\'' +
                ", fare=" + fare +
                ", status=" + status +
                '}';
    }
}