package com.atsistemas.hotelBookings.Service;

import com.atsistemas.hotelBookings.Entity.Hotel;

import java.util.List;
import java.util.Optional;

public interface HotelService {
    List<Hotel> getAllHotels();
    Optional<Hotel> getHotelById(Integer id);
}
