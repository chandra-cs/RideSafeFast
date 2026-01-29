package com.travelcompany.booking.service;

import com.travelcompany.booking.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for Admin operations
 * Handles business logic for administrative functions and reporting
 */
@Service
public class AdminService {

    private final BookingService bookingService;
    private final CustomerService customerService;

    @Autowired
    public AdminService(BookingService bookingService, CustomerService customerService) {
        this.bookingService = bookingService;
        this.customerService = customerService;
    }

    /**
     * Get dashboard statistics
     */
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        // Total counts
        stats.put("totalCustomers", customerService.getTotalCustomerCount());
        stats.put("totalBookings", bookingService.getAllBookings().size());

        // Booking status counts
        stats.put("pendingBookings", bookingService.getBookingsCountByStatus(Booking.BookingStatus.PENDING));
        stats.put("confirmedBookings", bookingService.getBookingsCountByStatus(Booking.BookingStatus.CONFIRMED));
        stats.put("inProgressBookings", bookingService.getBookingsCountByStatus(Booking.BookingStatus.IN_PROGRESS));
        stats.put("completedBookings", bookingService.getBookingsCountByStatus(Booking.BookingStatus.COMPLETED));
        stats.put("cancelledBookings", bookingService.getBookingsCountByStatus(Booking.BookingStatus.CANCELLED));

        // Recent bookings (last 7 days)
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        LocalDateTime now = LocalDateTime.now();
        List<Booking> recentBookings = bookingService.getBookingsBetweenDates(weekAgo, now);
        stats.put("recentBookingsCount", recentBookings.size());

        return stats;
    }

    /**
     * Get all bookings for admin view
     */
    public List<Booking> getAllBookingsForAdmin() {
        return bookingService.getAllBookings();
    }

    /**
     * Update booking status (admin function)
     */
    public Booking updateBookingStatus(Long bookingId, String status) {
        Booking.BookingStatus bookingStatus = Booking.BookingStatus.valueOf(status.toUpperCase());
        return bookingService.updateBookingStatus(bookingId, bookingStatus);
    }

    /**
     * Get bookings by status for admin filtering
     */
    public List<Booking> getBookingsByStatus(String status) {
        Booking.BookingStatus bookingStatus = Booking.BookingStatus.valueOf(status.toUpperCase());
        return bookingService.getBookingsByStatus(bookingStatus);
    }

    /**
     * Search bookings by location
     */
    public List<Booking> searchBookings(String location) {
        if (location == null || location.trim().isEmpty()) {
            return bookingService.getAllBookings();
        }
        return bookingService.searchBookingsByLocation(location);
    }

    /**
     * Get monthly booking report
     */
    public Map<String, Object> getMonthlyReport() {
        Map<String, Object> report = new HashMap<>();

        LocalDateTime monthStart = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime monthEnd = LocalDateTime.now();

        List<Booking> monthlyBookings = bookingService.getBookingsBetweenDates(monthStart, monthEnd);

        report.put("monthlyBookings", monthlyBookings.size());
        report.put("monthlyRevenue", calculateRevenue(monthlyBookings));

        return report;
    }

    /**
     * Calculate total revenue from bookings
     */
    private double calculateRevenue(List<Booking> bookings) {
        return bookings.stream()
            .filter(booking -> booking.getStatus() == Booking.BookingStatus.COMPLETED)
            .mapToDouble(booking -> booking.getFare().doubleValue())
            .sum();
    }
}