package com.atsistemas.hotelBookings.Service.Impl;

import com.atsistemas.hotelBookings.Entity.Availability;
import com.atsistemas.hotelBookings.Entity.Booking;
import com.atsistemas.hotelBookings.Entity.Hotel;
import com.atsistemas.hotelBookings.Repository.AvailabilityRepository;
import com.atsistemas.hotelBookings.Repository.BookingRepository;
import com.atsistemas.hotelBookings.Repository.HotelRepository;
import com.atsistemas.hotelBookings.Service.BookingService;
import com.atsistemas.hotelBookings.Service.HotelService;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    //Inyección de beans por constructor.
    private BookingRepository bookingRepository;
    private AvailabilityServiceImpl availabilityService;
    private HotelServiceImpl hotelService;
    BookingServiceImpl(BookingRepository bookingRepository, HotelServiceImpl hotelService, AvailabilityServiceImpl availabilityService) {
        this.bookingRepository = bookingRepository;
        this.availabilityService = availabilityService;
        this.hotelService = hotelService;
    }
    //Método para crear reserva.
    @Override
    @Transactional
    public Booking createBooking(Booking booking, Integer hotelId) {
        Hotel hotel = hotelService.getHotelById(hotelId)
                .orElseThrow(() -> new NoSuchElementException("No se encontró ningún hotel con el ID " + hotelId));
        for (LocalDate date = booking.getDate_from(); date.isBefore(booking.getDate_to().plusDays(1)); date = date.plusDays(1)) {
            Optional<Availability> existingAvailability = availabilityService.getAvailability(hotelId, date);
            if (existingAvailability.isPresent()) {
                Availability availability = existingAvailability.get();
                availability.setRooms(availability.getRooms() - 1);
                availabilityService.saveAvailability(availability);
            }
        }
        return bookingRepository.save(booking);
    }
    //Método para buscar reservas según el IDhotel y rango de fechas.
    @Override
    public List<Booking> getBookingsByHotelAndDates (Integer hotelId, LocalDate startDate, LocalDate endDate){
        return bookingRepository.findByHotelAndDates(hotelId, startDate, endDate);
    }
    //Método para buscar reservas por ID de reserva , datos del hotel incluido.
    public Booking getBookingWithHotel(Integer bookingId){
        Optional<Booking> reservaOptional = bookingRepository.findBookingWithHotelById(bookingId);
        return reservaOptional.orElse(null);

    }
    //Método para Eliminar reservas
    //Tenemos que controlar que haya disponibilidad en esa fecha ( En teoría si hay reserva debe de haber disponibilidad siempre)
    //Recorremos el bucle de fechas y sumamos una habitación cada dia de la reserva
    public void deleteBookingById(Integer bookingId) {
        Optional<Booking> optionalBooking = Optional.ofNullable(bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + bookingId)));
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            for (LocalDate date = booking.getDate_from(); date.isBefore(booking.getDate_to().plusDays(1)); date = date.plusDays(1)) {
                Optional<Availability> existingAvailability = availabilityService.getAvailability(booking.getHotel().getId(), date);
                if (existingAvailability.isPresent()) {
                    Availability availability = existingAvailability.get();
                    availability.setRooms(availability.getRooms() + 1);
                    availabilityService.saveAvailability(availability);
                }
            }
        }
        bookingRepository.deleteById(bookingId);
    }
}
