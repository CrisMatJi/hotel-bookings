package com.atsistemas.hotelBookings.Service;

import com.atsistemas.hotelBookings.Entity.Hotel;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HotelService {
    List<Hotel> getAllHotels();
    Hotel getHotelById(Integer id);
    Hotel saveHotel(Hotel hotel);
    Hotel updateHotel(Hotel hotel,Integer category, String name);
    List<Hotel> findByAvailability(LocalDate startDate, LocalDate endDate, String name, Integer category);

}
