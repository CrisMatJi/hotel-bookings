package com.atsistemas.hotelBookings.Service.Impl;

import com.atsistemas.hotelBookings.Entity.Availability;
import com.atsistemas.hotelBookings.Entity.Booking;
import com.atsistemas.hotelBookings.Entity.Hotel;
import com.atsistemas.hotelBookings.Exception.AvailabilityErrorException;
import com.atsistemas.hotelBookings.Exception.ErrorQueryException;
import com.atsistemas.hotelBookings.Exception.HotelNotFoundException;
import com.atsistemas.hotelBookings.Exception.SaveErrorException;
import com.atsistemas.hotelBookings.Repository.BookingRepository;
import com.atsistemas.hotelBookings.Service.AvailabilityService;
import com.atsistemas.hotelBookings.Service.BookingService;
import com.atsistemas.hotelBookings.Service.HotelService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    /**
     * Inyección de beans por constructor.
     */
    private BookingRepository bookingRepository;
    private AvailabilityService availabilityService;
    private HotelService hotelService;

    BookingServiceImpl(BookingRepository bookingRepository, HotelService hotelService, AvailabilityService availabilityService) {
        this.bookingRepository = bookingRepository;
        this.availabilityService = availabilityService;
        this.hotelService = hotelService;
    }

    /**
     * Método para crear reserva.
     *
     * @param booking
     * @param hotelId
     * @return Booking
     * @throws SaveErrorException
     */
    @Override
    @Transactional
    public Booking createBooking(Booking booking, Integer hotelId) throws SaveErrorException, AvailabilityErrorException {
        Hotel hotel = hotelService.getHotelById(hotelId);
        boolean guardar = false;
        for (LocalDate date = booking.getDate_from(); date.isBefore(booking.getDate_to().plusDays(1)); date = date.plusDays(1)) {
            Optional<Availability> existingAvailability = availabilityService.getAvailability(hotelId, date);
            if (existingAvailability != null && existingAvailability.isPresent()) {
                Availability availability = existingAvailability.get();
                availability.setRooms(availability.getRooms() - 1);
                availabilityService.saveAvailability(availability);
                guardar = true;
            }
        }
        if (guardar == true) {
            booking.setHotel(hotel);
            booking.setId_hotel(hotelId);
            return bookingRepository.save(booking);
        } else throw new AvailabilityErrorException();
    }

    /**
     * Método para buscar reservas según el IDhotel y rango de fechas.
     *
     * @param hotelId
     * @param startDate
     * @param endDate
     * @return List<Booking>
     */
    @Override
    public List<Booking> getBookingsByHotelAndDates(Integer hotelId, LocalDate startDate, LocalDate endDate) throws ErrorQueryException {
        List<Booking> queryList = bookingRepository.findByHotelAndDates(hotelId, startDate, endDate);
        if (queryList == null) {
            throw new ErrorQueryException();
        }
        return queryList;
    }

    /**
     * Método para buscar reservas por ID de reserva , datos del hotel incluido.
     *
     * @param bookingId
     * @return Optional<Booking>
     */
    public Booking getBookingWithHotel(Integer bookingId) throws ErrorQueryException {
        try {
            Optional<Booking> reservaOptional = bookingRepository.findBookingWithHotelById(bookingId);
            return reservaOptional.get();
        } catch (Exception e) {
            throw new ErrorQueryException();
        }
    }

    /**
     * Método para Eliminar reservas
     * Tenemos que controlar que haya disponibilidad en esa fecha ( En teoría si hay reserva debe de haber disponibilidad siempre)
     * Recorremos el bucle de fechas y sumamos una habitación cada dia de la reserva
     *
     * @param bookingId
     */
    @Override
    public void deleteBookingById(Integer bookingId) throws SaveErrorException {
        Optional<Booking> optionalBooking = Optional.ofNullable(bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + bookingId)));
        try {
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
        } catch (Exception e) {
            throw new SaveErrorException();
        }
    }
}