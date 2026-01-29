package com.travelcompany.booking.repository;

import com.travelcompany.booking.model.Booking;
import com.travelcompany.booking.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Booking entity
 * Provides CRUD operations and custom queries for Booking management
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByCustomer(Customer customer);

    List<Booking> findByCustomerId(Long customerId);

    List<Booking> findByStatus(Booking.BookingStatus status);

    List<Booking> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT b FROM Booking b WHERE b.customer.id = ?1 ORDER BY b.createdDate DESC")
    List<Booking> findByCustomerIdOrderByCreatedDateDesc(Long customerId);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = ?1")
    long countByStatus(Booking.BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.pickupLocation LIKE %?1% OR b.destination LIKE %?1%")
    List<Booking> findByLocationContaining(String location);
}