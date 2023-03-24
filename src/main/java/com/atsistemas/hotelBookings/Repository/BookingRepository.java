package com.atsistemas.hotelBookings.Repository;

import com.atsistemas.hotelBookings.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface BookingRepository extends JpaRepository<Booking,Integer>, JpaSpecificationExecutor<Booking> {
    @Query("SELECT b FROM Booking b WHERE b.hotel.id = :hotelId AND b.date_from >= :startDate AND b.date_to <= :endDate")
    List<Booking> findByHotelAndDates(@Param("hotelId") Integer hotelId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT b FROM Booking b JOIN FETCH b.hotel WHERE b.id = :bookingId")
    Optional<Booking> findBookingWithHotelById(@Param("bookingId") Integer bookingId);





}
