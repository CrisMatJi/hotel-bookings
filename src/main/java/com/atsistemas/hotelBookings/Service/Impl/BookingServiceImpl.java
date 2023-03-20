package com.atsistemas.hotelBookings.Service.Impl;

import com.atsistemas.hotelBookings.Entity.Availability;
import com.atsistemas.hotelBookings.Entity.Booking;
import com.atsistemas.hotelBookings.Entity.Hotel;
import com.atsistemas.hotelBookings.Repository.AvailabilityRepository;
import com.atsistemas.hotelBookings.Repository.BookingRepository;
import com.atsistemas.hotelBookings.Repository.HotelRepository;
import com.atsistemas.hotelBookings.Service.BookingService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    private BookingRepository bookingRepository;
    private AvailabilityRepository availabilityRepository;
    private HotelRepository hotelRepository;

    @PersistenceContext
    private EntityManager em;

    BookingServiceImpl(BookingRepository bookingRepository, AvailabilityRepository availabilityRepository, HotelRepository hotelRepository) {
        this.bookingRepository = bookingRepository;
        this.availabilityRepository = availabilityRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    @Transactional
    public Booking createBooking(Booking booking) {
        Hotel hotel = hotelRepository.findById(booking.getId_hotel()).
                orElseThrow(() -> new EntityNotFoundException("Hotel not found with id: " + booking.getId_hotel()));
        for (LocalDate date = booking.getDate_from(); date.isBefore(booking.getDate_to().plusDays(1)); date = date.plusDays(1)) {
            Optional<Availability> existingAvailability = availabilityRepository.findByHotelAndDate(hotel, date);
            if (existingAvailability.isPresent()) {
                Availability availability = existingAvailability.get();
                availability.setRooms(availability.getRooms() - 1);
                availabilityRepository.save(availability);

            }

        }
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingsByHotelAndDates (Integer hotelId, LocalDate startDate, LocalDate endDate){
        return bookingRepository.findByHotelAndDates(hotelId, startDate, endDate);
    }

    public Booking getBookingWithHotel(Integer bookingId){
        Optional<Booking> reservaOptional = bookingRepository.findBookingWithHotelById(bookingId);
        if (reservaOptional.isPresent()) {
            Booking booking = reservaOptional.get();

            return booking;
        } else {
            return null; // Pendiente de las excepcioens
        }

    }

}
