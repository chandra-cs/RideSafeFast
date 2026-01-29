package com.travelcompany.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.travelcompany.booking.dto.BookingRequestDto;
import com.travelcompany.booking.dto.CustomerRegistrationDto;
import com.travelcompany.booking.service.AdminService;
import com.travelcompany.booking.service.BookingService;
import com.travelcompany.booking.service.CustomerService;

@Controller
public class WebController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Cab Booking System");
        return "index";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("customer", new CustomerRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerCustomer(@ModelAttribute CustomerRegistrationDto customer, 
                                  RedirectAttributes redirectAttributes) {
        try {
            customerService.registerCustomer(customer);
            redirectAttributes.addFlashAttribute("success", "Customer registered successfully!");
            return "redirect:/customers";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed: " + e.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("/customers")
    public String customers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customers";
    }

    @GetMapping("/book")
    public String bookCab(Model model) {
        model.addAttribute("booking", new BookingRequestDto());
        model.addAttribute("customers", customerService.getAllCustomers());
        return "book-cab";
    }

    @PostMapping("/book")
    public String createBooking(@ModelAttribute BookingRequestDto booking, 
                               RedirectAttributes redirectAttributes) {
        try {
            bookingService.createBooking(booking);
            redirectAttributes.addFlashAttribute("success", "Booking created successfully!");
            return "redirect:/bookings";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Booking failed: " + e.getMessage());
            return "redirect:/book";
        }
    }

    @GetMapping("/bookings")
    public String bookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "bookings";
    }

    @GetMapping("/fare-calculator")
    public String fareCalculator() {
        return "fare-calculator";
    }

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        try {
            model.addAttribute("dashboard", adminService.getDashboardStats());
            model.addAttribute("bookings", bookingService.getAllBookings());
        } catch (Exception e) {
            model.addAttribute("error", "Unable to load dashboard data");
        }
        return "admin";
    }

    @GetMapping("/booking/{id}")
    public String viewBooking(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            // Use the existing method from BookingService
            var bookings = bookingService.getAllBookings();
            var booking = bookings.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElse(null);
            
            if (booking != null) {
                model.addAttribute("booking", booking);
                return "booking-details";
            } else {
                redirectAttributes.addFlashAttribute("error", "Booking not found");
                return "redirect:/bookings";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error loading booking details");
            return "redirect:/bookings";
        }
    }
}