package com.atsistemas.hotelBookings.Mapper;

import com.atsistemas.hotelBookings.Dto.BookingDTO;
import com.atsistemas.hotelBookings.Dto.HotelDTO;
import com.atsistemas.hotelBookings.Entity.Booking;
import com.atsistemas.hotelBookings.Entity.Hotel;

import java.util.List;

public interface BookingMapper<E, D> {
    Booking toEntity(BookingDTO bookingDTO);
    BookingDTO toDTO(Booking booking);
    List<BookingDTO> listToDTO(List<Booking> bookingList);

}