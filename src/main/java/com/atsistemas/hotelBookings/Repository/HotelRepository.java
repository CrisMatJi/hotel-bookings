package com.atsistemas.hotelBookings.Repository;

import com.atsistemas.hotelBookings.Entity.Hotel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface HotelRepository extends JpaRepository<Hotel,Integer>, JpaSpecificationExecutor<Hotel> {
    @Query("SELECT h FROM Hotel h JOIN h.availabilities a WHERE a.date BETWEEN :startDate AND :endDate AND a.rooms>0")
    Optional<List<Hotel>>findHotelsByAvailability(@Param("startDate") LocalDate startDate ,@Param("endDate") LocalDate endDate);
    @Query("SELECT h FROM Hotel h WHERE h.id IN (" +
            "SELECT a.hotel.id FROM Availability a " +
            "WHERE a.date BETWEEN :startDate AND :endDate AND a.rooms > 0 " +
            "GROUP BY a.hotel.id " +
            "HAVING COUNT(*) = :days)")
    Optional<List<Hotel>>findHotelsByAvailability2(@Param("startDate") LocalDate startDate ,@Param("endDate") LocalDate endDate,@Param("days") Long days);








}
