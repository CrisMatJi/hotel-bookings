package com.atsistemas.hotelBookings.Service;

import com.atsistemas.hotelBookings.Entity.Availability;
import com.atsistemas.hotelBookings.Entity.Hotel;

import java.time.LocalDate;
import java.util.Optional;

public interface AvailabilityService {
    void createAvailability(Integer hotelId, LocalDate startDate, LocalDate endDate, Integer rooms);
    Optional<Availability> findByHotelAndDate(Hotel hotel, LocalDate date);

}
