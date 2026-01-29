package com.travelcompany.booking.controller;

import com.travelcompany.booking.dto.FareCalculationDto;
import com.travelcompany.booking.service.FareCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

/**
 * REST Controller for Fare calculation operations
 * Provides endpoints for fare calculator microservice
 */
@RestController
@RequestMapping("/api/fare")
@CrossOrigin(origins = "*")
public class FareController {

    private final FareCalculatorService fareCalculatorService;

    @Autowired
    public FareController(FareCalculatorService fareCalculatorService) {
        this.fareCalculatorService = fareCalculatorService;
    }

    /**
     * Calculate fare for a trip
     * POST /api/fare/calculate
     */
    @PostMapping("/calculate")
    public ResponseEntity<BigDecimal> calculateFare(@RequestParam String pickupLocation,
                                                   @RequestParam String destination,
                                                   @RequestParam String cabType) {
        try {
            BigDecimal fare = fareCalculatorService.calculateFare(pickupLocation, destination, cabType);
            return new ResponseEntity<>(fare, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Calculate detailed fare breakdown
     * POST /api/fare/detailed
     */
    @PostMapping("/detailed")
    public ResponseEntity<FareCalculationDto> calculateDetailedFare(@RequestParam String pickupLocation,
                                                                   @RequestParam String destination,
                                                                   @RequestParam String cabType) {
        try {
            FareCalculationDto fareDetails = fareCalculatorService.calculateDetailedFare(pickupLocation, destination, cabType);
            return new ResponseEntity<>(fareDetails, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get estimated trip time
     * GET /api/fare/time-estimate
     */
    @GetMapping("/time-estimate")
    public ResponseEntity<Integer> getEstimatedTime(@RequestParam String pickupLocation,
                                                   @RequestParam String destination) {
        try {
            int estimatedTime = fareCalculatorService.getEstimatedTripTime(pickupLocation, destination);
            return new ResponseEntity<>(estimatedTime, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}