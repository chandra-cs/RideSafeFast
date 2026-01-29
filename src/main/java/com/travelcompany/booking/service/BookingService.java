package com.travelcompany.booking.service;

import com.travelcompany.booking.dto.BookingRequestDto;
import com.travelcompany.booking.model.Booking;
import com.travelcompany.booking.model.Customer;
import com.travelcompany.booking.repository.BookingRepository;
import com.travelcompany.booking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for Booking operations
 * Handles business logic for booking management
 */
@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final FareCalculatorService fareCalculatorService;

    @Autowired
    public BookingService(BookingRepository bookingRepository, 
                         CustomerRepository customerRepository,
                         FareCalculatorService fareCalculatorService) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.fareCalculatorService = fareCalculatorService;
    }

    /**
     * Create a new booking
     */
    public Booking createBooking(BookingRequestDto bookingRequest) {
        // Find customer
        Customer customer = customerRepository.findById(bookingRequest.getCustomerId())
            .orElseThrow(() -> new RuntimeException("Customer not found with id: " + bookingRequest.getCustomerId()));

        // Calculate fare
        BigDecimal fare = fareCalculatorService.calculateFare(
            bookingRequest.getPickupLocation(),
            bookingRequest.getDestination(),
            bookingRequest.getCabType()
        );

        // Create booking
        Booking booking = new Booking(
            customer,
            bookingRequest.getPickupLocation(),
            bookingRequest.getDestination(),
            bookingRequest.getPreferredTime(),
            bookingRequest.getCabType(),
            fare
        );

        return bookingRepository.save(booking);
    }

    /**
     * Get all bookings
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /**
     * Find booking by ID
     */
    public Optional<Booking> findBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    /**
     * Get bookings by customer ID
     */
    public List<Booking> getBookingsByCustomerId(Long customerId) {
        return bookingRepository.findByCustomerIdOrderByCreatedDateDesc(customerId);
    }

    /**
     * Get bookings by status
     */
    public List<Booking> getBookingsByStatus(Booking.BookingStatus status) {
        return bookingRepository.findByStatus(status);
    }

    /**
     * Update booking status
     */
    public Booking updateBookingStatus(Long bookingId, Booking.BookingStatus status) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    /**
     * Cancel booking
     */
    public Booking cancelBooking(Long bookingId) {
        return updateBookingStatus(bookingId, Booking.BookingStatus.CANCELLED);
    }

    /**
     * Confirm booking
     */
    public Booking confirmBooking(Long bookingId) {
        return updateBookingStatus(bookingId, Booking.BookingStatus.CONFIRMED);
    }

    /**
     * Get bookings count by status
     */
    public long getBookingsCountByStatus(Booking.BookingStatus status) {
        return bookingRepository.countByStatus(status);
    }

    /**
     * Search bookings by location
     */
    public List<Booking> searchBookingsByLocation(String location) {
        return bookingRepository.findByLocationContaining(location);
    }

    /**
     * Get bookings between dates
     */
    public List<Booking> getBookingsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return bookingRepository.findByCreatedDateBetween(startDate, endDate);
    }
}