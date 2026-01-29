package com.travelcompany.booking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * Fare entity representing fare calculation details
 */
@Entity
@Table(name = "fares")
public class Fare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Positive
    @Column(name = "base_fare", precision = 10, scale = 2)
    private BigDecimal baseFare;

    @Positive
    @Column(name = "distance_fare", precision = 10, scale = 2)
    private BigDecimal distanceFare;

    @Column(name = "time_fare", precision = 10, scale = 2)
    private BigDecimal timeFare;

    @Positive
    @Column(name = "total_fare", precision = 10, scale = 2)
    private BigDecimal totalFare;

    @Column(name = "distance_km", precision = 10, scale = 2)
    private BigDecimal distanceKm;

    public Fare() {}

    public Fare(Booking booking, BigDecimal baseFare, BigDecimal distanceFare, 
                BigDecimal timeFare, BigDecimal totalFare, BigDecimal distanceKm) {
        this.booking = booking;
        this.baseFare = baseFare;
        this.distanceFare = distanceFare;
        this.timeFare = timeFare;
        this.totalFare = totalFare;
        this.distanceKm = distanceKm;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }

    public BigDecimal getBaseFare() { return baseFare; }
    public void setBaseFare(BigDecimal baseFare) { this.baseFare = baseFare; }

    public BigDecimal getDistanceFare() { return distanceFare; }
    public void setDistanceFare(BigDecimal distanceFare) { this.distanceFare = distanceFare; }

    public BigDecimal getTimeFare() { return timeFare; }
    public void setTimeFare(BigDecimal timeFare) { this.timeFare = timeFare; }

    public BigDecimal getTotalFare() { return totalFare; }
    public void setTotalFare(BigDecimal totalFare) { this.totalFare = totalFare; }

    public BigDecimal getDistanceKm() { return distanceKm; }
    public void setDistanceKm(BigDecimal distanceKm) { this.distanceKm = distanceKm; }

    @Override
    public String toString() {
        return "Fare{" +
                "id=" + id +
                ", baseFare=" + baseFare +
                ", distanceFare=" + distanceFare +
                ", timeFare=" + timeFare +
                ", totalFare=" + totalFare +
                ", distanceKm=" + distanceKm +
                '}';
    }
}