package com.atsistemas.hotelBookings.Service;

import com.atsistemas.hotelBookings.Entity.Booking;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingService {
    Booking createBooking(Booking booking, Integer hotelId);
    List<Booking> getBookingsByHotelAndDates(Integer hotelId, LocalDate startDate, LocalDate endDate);
    Booking getBookingWithHotel(Integer bookingId);
    void deleteBookingById( Integer bookingId);
}
