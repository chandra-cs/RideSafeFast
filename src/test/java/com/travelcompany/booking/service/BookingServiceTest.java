package com.travelcompany.booking.service;

import com.travelcompany.booking.dto.BookingRequestDto;
import com.travelcompany.booking.model.Booking;
import com.travelcompany.booking.model.Customer;
import com.travelcompany.booking.repository.BookingRepository;
import com.travelcompany.booking.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for BookingService
 */
@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private FareCalculatorService fareCalculatorService;

    @InjectMocks
    private BookingService bookingService;

    private Customer testCustomer;
    private BookingRequestDto testBookingRequest;
    private Booking testBooking;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer("John Doe", "john@example.com", "+1234567890", "123 Main St");
        testCustomer.setId(1L);

        testBookingRequest = new BookingRequestDto(
            1L,
            "123 Main St",
            "456 Oak Ave",
            LocalDateTime.now().plusHours(2),
            "Economy"
        );

        testBooking = new Booking(
            testCustomer,
            "123 Main St",
            "456 Oak Ave",
            LocalDateTime.now().plusHours(2),
            "Economy",
            new BigDecimal("15.50")
        );
        testBooking.setId(1L);
    }

    @Test
    void testCreateBooking() {
        // Given
        given(customerRepository.findById(1L)).willReturn(Optional.of(testCustomer));
        given(fareCalculatorService.calculateFare("123 Main St", "456 Oak Ave", "Economy"))
            .willReturn(new BigDecimal("15.50"));
        given(bookingRepository.save(any(Booking.class))).willReturn(testBooking);

        // When
        Booking result = bookingService.createBooking(testBookingRequest);

        // Then
        assertNotNull(result);
        assertEquals(testCustomer, result.getCustomer());
        assertEquals("123 Main St", result.getPickupLocation());
        assertEquals("456 Oak Ave", result.getDestination());
        assertEquals(new BigDecimal("15.50"), result.getFare());
        assertEquals(Booking.BookingStatus.PENDING, result.getStatus());

        verify(customerRepository).findById(1L);
        verify(fareCalculatorService).calculateFare("123 Main St", "456 Oak Ave", "Economy");
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    void testCreateBookingCustomerNotFound() {
        // Given
        given(customerRepository.findById(999L)).willReturn(Optional.empty());

        BookingRequestDto invalidRequest = new BookingRequestDto(
            999L, "123 Main St", "456 Oak Ave", LocalDateTime.now().plusHours(2), "Economy"
        );

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            bookingService.createBooking(invalidRequest);
        });
    }

    @Test
    void testUpdateBookingStatus() {
        // Given
        given(bookingRepository.findById(1L)).willReturn(Optional.of(testBooking));
        testBooking.setStatus(Booking.BookingStatus.CONFIRMED);
        given(bookingRepository.save(testBooking)).willReturn(testBooking);

        // When
        Booking result = bookingService.updateBookingStatus(1L, Booking.BookingStatus.CONFIRMED);

        // Then
        assertEquals(Booking.BookingStatus.CONFIRMED, result.getStatus());
        verify(bookingRepository).findById(1L);
        verify(bookingRepository).save(testBooking);
    }

    @Test
    void testCancelBooking() {
        // Given
        given(bookingRepository.findById(1L)).willReturn(Optional.of(testBooking));
        testBooking.setStatus(Booking.BookingStatus.CANCELLED);
        given(bookingRepository.save(testBooking)).willReturn(testBooking);

        // When
        Booking result = bookingService.cancelBooking(1L);

        // Then
        assertEquals(Booking.BookingStatus.CANCELLED, result.getStatus());
        verify(bookingRepository).findById(1L);
        verify(bookingRepository).save(testBooking);
    }

    @Test
    void testConfirmBooking() {
        // Given
        given(bookingRepository.findById(1L)).willReturn(Optional.of(testBooking));
        testBooking.setStatus(Booking.BookingStatus.CONFIRMED);
        given(bookingRepository.save(testBooking)).willReturn(testBooking);

        // When
        Booking result = bookingService.confirmBooking(1L);

        // Then
        assertEquals(Booking.BookingStatus.CONFIRMED, result.getStatus());
        verify(bookingRepository).findById(1L);
        verify(bookingRepository).save(testBooking);
    }
}