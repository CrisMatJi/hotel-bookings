package com.atsistemas.hotelBookings.Service.Impl;

import com.atsistemas.hotelBookings.Entity.Booking;
import com.atsistemas.hotelBookings.Entity.Hotel;
import com.atsistemas.hotelBookings.Repository.AvailabilityRepository;
import com.atsistemas.hotelBookings.Repository.BookingRepository;
import com.atsistemas.hotelBookings.Service.BookingService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private BookingRepository bookingRepository;
    private AvailabilityRepository availabilityRepository;

    BookingServiceImpl(BookingRepository bookingRepository, AvailabilityRepository availabilityRepository) {
        this.bookingRepository = bookingRepository;
        this.availabilityRepository = availabilityRepository;
    }
    @Override
    @Transactional
    public Booking createBooking(Booking booking){
        return bookingRepository.save(booking);
    }
    @Override
    public List<Booking> getBookingsByHotelAndDates(Integer hotelId, LocalDate startDate, LocalDate endDate) {
        return bookingRepository.findByHotelAndDates(hotelId, startDate, endDate);
    }



}
