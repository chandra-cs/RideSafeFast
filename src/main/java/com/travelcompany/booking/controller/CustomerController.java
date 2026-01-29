package com.travelcompany.booking.controller;

import com.travelcompany.booking.dto.CustomerRegistrationDto;
import com.travelcompany.booking.model.Customer;
import com.travelcompany.booking.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Customer operations
 * Provides endpoints for customer management microservice
 */
@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Register a new customer
     * POST /api/customers
     */
    @PostMapping
    public ResponseEntity<Customer> registerCustomer(@Valid @RequestBody CustomerRegistrationDto registrationDto) {
        try {
            Customer customer = customerService.registerCustomer(registrationDto);
            return new ResponseEntity<>(customer, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get all customers
     * GET /api/customers
     */
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    /**
     * Get customer by ID
     * GET /api/customers/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findCustomerById(id);
        return customer.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Get customer by email
     * GET /api/customers/email/{email}
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        Optional<Customer> customer = customerService.findCustomerByEmail(email);
        return customer.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Update customer
     * PUT /api/customers/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, 
                                                  @Valid @RequestBody CustomerRegistrationDto updateDto) {
        try {
            Customer updatedCustomer = customerService.updateCustomer(id, updateDto);
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete customer
     * DELETE /api/customers/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get total customer count
     * GET /api/customers/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getCustomerCount() {
        long count = customerService.getTotalCustomerCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}