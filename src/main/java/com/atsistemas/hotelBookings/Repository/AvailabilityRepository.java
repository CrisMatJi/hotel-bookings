package com.atsistemas.hotelBookings.Repository;

import com.atsistemas.hotelBookings.Entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AvailabilityRepository extends JpaRepository<Availability,Integer>, JpaSpecificationExecutor<Availability> {
}
