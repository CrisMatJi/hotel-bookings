package com.atsistemas.hotelBookings.Service;

import com.atsistemas.hotelBookings.Entity.Booking;
import com.atsistemas.hotelBookings.Entity.Hotel;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    Booking createBooking(Booking booking);

    List<Booking> getBookingsByHotelAndDates(Integer hotelId, LocalDate startDate, LocalDate endDate);


}
