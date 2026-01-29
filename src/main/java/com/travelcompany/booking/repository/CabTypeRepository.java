package com.travelcompany.booking.repository;

import com.travelcompany.booking.model.CabType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository interface for CabType entity
 * Provides CRUD operations for CabType management
 */
@Repository
public interface CabTypeRepository extends JpaRepository<CabType, Long> {

    Optional<CabType> findByName(String name);

    boolean existsByName(String name);
}