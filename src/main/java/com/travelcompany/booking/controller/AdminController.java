package com.travelcompany.booking.controller;

import com.travelcompany.booking.model.Booking;
import com.travelcompany.booking.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for Admin operations
 * Provides endpoints for administrative functions and reporting
 */
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Get dashboard statistics
     * GET /api/admin/dashboard
     */
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = adminService.getDashboardStats();
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

    /**
     * Get all bookings for admin
     * GET /api/admin/bookings
     */
    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = adminService.getAllBookingsForAdmin();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    /**
     * Update booking status (admin function)
     * PUT /api/admin/bookings/{id}/status
     */
    @PutMapping("/bookings/{id}/status")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable Long id,
                                                      @RequestParam String status) {
        try {
            Booking updatedBooking = adminService.updateBookingStatus(id, status);
            return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get bookings by status (admin filtering)
     * GET /api/admin/bookings/status/{status}
     */
    @GetMapping("/bookings/status/{status}")
    public ResponseEntity<List<Booking>> getBookingsByStatus(@PathVariable String status) {
        try {
            List<Booking> bookings = adminService.getBookingsByStatus(status);
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Search bookings (admin function)
     * GET /api/admin/bookings/search
     */
    @GetMapping("/bookings/search")
    public ResponseEntity<List<Booking>> searchBookings(@RequestParam(required = false) String location) {
        List<Booking> bookings = adminService.searchBookings(location);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    /**
     * Get monthly report
     * GET /api/admin/reports/monthly
     */
    @GetMapping("/reports/monthly")
    public ResponseEntity<Map<String, Object>> getMonthlyReport() {
        Map<String, Object> report = adminService.getMonthlyReport();
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
}