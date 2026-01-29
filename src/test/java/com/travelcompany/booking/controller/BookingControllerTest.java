package com.travelcompany.booking.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelcompany.booking.dto.BookingRequestDto;
import com.travelcompany.booking.model.Booking;
import com.travelcompany.booking.model.Customer;
import com.travelcompany.booking.service.BookingService;

/**
 * Unit tests for BookingController
 */
@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    private Booking testBooking;
    private BookingRequestDto testBookingRequest;
    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer("John Doe", "john@example.com", "+1234567890", "123 Main St");
        testCustomer.setId(1L);

        testBooking = new Booking(
            testCustomer,
            "123 Main St",
            "456 Oak Ave",
            LocalDateTime.now().plusHours(2),
            "Economy",
            new BigDecimal("15.50")
        );
        testBooking.setId(1L);

        testBookingRequest = new BookingRequestDto(
            1L,
            "123 Main St",
            "456 Oak Ave",
            LocalDateTime.now().plusHours(2),
            "Economy"
        );
    }

    @Test
    void testCreateBooking() throws Exception {
        given(bookingService.createBooking(any(BookingRequestDto.class))).willReturn(testBooking);

        mockMvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBookingRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.pickupLocation").value("123 Main St"))
                .andExpect(jsonPath("$.destination").value("456 Oak Ave"));
    }

    @Test
    void testGetAllBookings() throws Exception {
        List<Booking> bookings = Arrays.asList(testBooking);
        given(bookingService.getAllBookings()).willReturn(bookings);

        mockMvc.perform(get("/api/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void testGetBookingById() throws Exception {
        given(bookingService.findBookingById(1L)).willReturn(Optional.of(testBooking));

        mockMvc.perform(get("/api/bookings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.pickupLocation").value("123 Main St"));
    }

    @Test
    void testGetBookingByIdNotFound() throws Exception {
        given(bookingService.findBookingById(999L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/bookings/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateBookingStatus() throws Exception {
        testBooking.setStatus(Booking.BookingStatus.CONFIRMED);
        given(bookingService.updateBookingStatus(1L, Booking.BookingStatus.CONFIRMED)).willReturn(testBooking);

        mockMvc.perform(put("/api/bookings/1/status")
                .param("status", "CONFIRMED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONFIRMED"));
    }

    @Test
    void testCancelBooking() throws Exception {
        testBooking.setStatus(Booking.BookingStatus.CANCELLED);
        given(bookingService.cancelBooking(1L)).willReturn(testBooking);

        mockMvc.perform(put("/api/bookings/1/cancel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"));
    }
}