package com.travelcompany.booking.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travelcompany.booking.dto.BookingRequestDto;
import com.travelcompany.booking.model.Booking;
import com.travelcompany.booking.service.BookingService;

import jakarta.validation.Valid;

/**
 * REST Controller for Booking operations
 * Provides endpoints for booking management microservice
 */
@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Create a new booking
     * POST /api/bookings
     */
    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingRequestDto bookingRequest) {
        try {
            Booking booking = bookingService.createBooking(bookingRequest);
            return new ResponseEntity<>(booking, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get all bookings
     * GET /api/bookings
     */
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    /**
     * Get booking by ID
     * GET /api/bookings/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Optional<Booking> booking = bookingService.findBookingById(id);
        return booking.map(b -> new ResponseEntity<>(b, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Get bookings by customer ID
     * GET /api/bookings/customer/{customerId}
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Booking>> getBookingsByCustomerId(@PathVariable Long customerId) {
        List<Booking> bookings = bookingService.getBookingsByCustomerId(customerId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    /**
     * Get bookings by status
     * GET /api/bookings/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Booking>> getBookingsByStatus(@PathVariable String status) {
        try {
            Booking.BookingStatus bookingStatus = Booking.BookingStatus.valueOf(status.toUpperCase());
            List<Booking> bookings = bookingService.getBookingsByStatus(bookingStatus);
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update booking status
     * PUT /api/bookings/{id}/status
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable Long id,
            @RequestParam String status) {
        try {
            Booking.BookingStatus bookingStatus = Booking.BookingStatus.valueOf(status.toUpperCase());
            Booking updatedBooking = bookingService.updateBookingStatus(id, bookingStatus);
            return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Cancel booking
     * PUT /api/bookings/{id}/cancel
     */
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long id) {
        try {
            Booking cancelledBooking = bookingService.cancelBooking(id);
            return new ResponseEntity<>(cancelledBooking, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Confirm booking
     * PUT /api/bookings/{id}/confirm
     */
    @PutMapping("/{id}/confirm")
    public ResponseEntity<Booking> confirmBooking(@PathVariable Long id) {
        try {
            Booking confirmedBooking = bookingService.confirmBooking(id);
            return new ResponseEntity<>(confirmedBooking, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Search bookings by location
     * GET /api/bookings/search
     */
    @GetMapping("/search")
    public ResponseEntity<List<Booking>> searchBookings(@RequestParam String location) {
        List<Booking> bookings = bookingService.searchBookingsByLocation(location);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
}