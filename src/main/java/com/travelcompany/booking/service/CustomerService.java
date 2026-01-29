package com.travelcompany.booking.service;

import com.travelcompany.booking.dto.CustomerRegistrationDto;
import com.travelcompany.booking.model.Customer;
import com.travelcompany.booking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service class for Customer operations
 * Handles business logic for customer management
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Register a new customer
     */
    public Customer registerCustomer(CustomerRegistrationDto registrationDto) {
        // Check if customer already exists
        if (customerRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RuntimeException("Customer with email already exists: " + registrationDto.getEmail());
        }

        Customer customer = new Customer(
            registrationDto.getName(),
            registrationDto.getEmail(),
            registrationDto.getPhone(),
            registrationDto.getAddress()
        );

        return customerRepository.save(customer);
    }

    /**
     * Get all customers
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Find customer by ID
     */
    public Optional<Customer> findCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    /**
     * Find customer by email
     */
    public Optional<Customer> findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    /**
     * Update customer information
     */
    public Customer updateCustomer(Long id, CustomerRegistrationDto updateDto) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        customer.setName(updateDto.getName());
        customer.setPhone(updateDto.getPhone());
        customer.setAddress(updateDto.getAddress());

        return customerRepository.save(customer);
    }

    /**
     * Delete customer
     */
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    /**
     * Get total customer count
     */
    public long getTotalCustomerCount() {
        return customerRepository.countAllCustomers();
    }
}