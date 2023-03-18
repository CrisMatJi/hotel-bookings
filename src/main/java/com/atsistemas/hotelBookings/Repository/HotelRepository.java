package com.atsistemas.hotelBookings.Repository;

import com.atsistemas.hotelBookings.Entity.Hotel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HotelRepository extends JpaRepository<Hotel,Integer>, JpaSpecificationExecutor<Hotel> {

}
