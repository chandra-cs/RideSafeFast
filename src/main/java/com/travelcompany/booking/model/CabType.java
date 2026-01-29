package com.travelcompany.booking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * CabType entity representing different types of cabs available
 */
@Entity
@Table(name = "cab_types")
public class CabType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Cab type name is required")
    @Column(unique = true, nullable = false)
    private String name;

    @Positive(message = "Base fare must be positive")
    @Column(name = "base_fare", precision = 10, scale = 2, nullable = false)
    private BigDecimal baseFare;

    @Positive(message = "Per km rate must be positive")
    @Column(name = "per_km_rate", precision = 10, scale = 2, nullable = false)
    private BigDecimal perKmRate;

    @Column(columnDefinition = "TEXT")
    private String description;

    public CabType() {}

    public CabType(String name, BigDecimal baseFare, BigDecimal perKmRate, String description) {
        this.name = name;
        this.baseFare = baseFare;
        this.perKmRate = perKmRate;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getBaseFare() { return baseFare; }
    public void setBaseFare(BigDecimal baseFare) { this.baseFare = baseFare; }

    public BigDecimal getPerKmRate() { return perKmRate; }
    public void setPerKmRate(BigDecimal perKmRate) { this.perKmRate = perKmRate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "CabType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", baseFare=" + baseFare +
                ", perKmRate=" + perKmRate +
                ", description='" + description + '\'' +
                '}';
    }
}