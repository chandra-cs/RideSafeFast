package com.travelcompany.booking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Data Transfer Object for Customer registration
 */
public class CustomerRegistrationDto {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Valid email is required")
    private String email;

    @Pattern(regexp = "\\+?[1-9]\\d{1,14}", message = "Valid phone number is required")
    private String phone;

    @NotBlank(message = "Address is required")
    private String address;

    public CustomerRegistrationDto() {}

    public CustomerRegistrationDto(String name, String email, String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}