package com.atsistemas.hotelBookings.Service.Impl;

import com.atsistemas.hotelBookings.Entity.Availability;
import com.atsistemas.hotelBookings.Entity.Booking;
import com.atsistemas.hotelBookings.Entity.Hotel;
import com.atsistemas.hotelBookings.Exception.ErrorQueryException;
import com.atsistemas.hotelBookings.Exception.SaveErrorException;
import com.atsistemas.hotelBookings.Repository.BookingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {
    /**
     * Inyección de Mocks
     */
    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private AvailabilityServiceImpl availabilityService;

    @Mock
    private HotelServiceImpl hotelService;

    @InjectMocks
    private BookingServiceImpl bookingService;
    @InjectMocks
    private Availability availability;

    private Booking booking;
    private Integer hotelId;
    private Hotel hotel;

    /**
     * Preparación de test
     */
    @BeforeEach
    public void setUp() {
        hotelId = 1;
        hotel = new Hotel();
        hotel.setId(hotelId);
        //----------------------------------------------------------------
        booking = new Booking();
        booking.setId(1);
        booking.setDate_from(LocalDate.of(2023, 04, 15));
        booking.setDate_to(LocalDate.of(2023, 04, 20));
        booking.setEmail("test@test.com");
        booking.setId_hotel(1);
        booking.setHotel(hotel);
        //----------------------------------------------------------------
        availability.setId(1);
        availability.setRooms(4);
        availability.setDate(LocalDate.of(2023,04,15));
        availability.setId_hotel(1);
    }

    /**
     * Test de creación de bookings
     * @throws SaveErrorException
     */
    @Test
    public void createBookingTest() throws SaveErrorException {
        Availability availability = new Availability();
        availability.setId_hotel(hotelId);
        availability.setDate(booking.getDate_from());
        availability.setRooms(4);
        Mockito.when(availabilityService.getAvailability(hotelId, booking.getDate_from())).thenReturn(Optional.of(availability));
        Mockito.when(hotelService.getHotelById(hotelId)).thenReturn(hotel);
        Mockito.when(bookingRepository.save(Mockito.any(Booking.class))).thenReturn(booking);
        Booking result = bookingService.createBooking(booking, hotelId);
        Mockito.verify(hotelService, Mockito.times(1)).getHotelById(hotelId);
        Mockito.verify(availabilityService, Mockito.times(1)).getAvailability(hotelId, booking.getDate_from());
        Mockito.verify(availabilityService, Mockito.times(1)).saveAvailability(Mockito.any(Availability.class));
        Mockito.verify(bookingRepository, Mockito.times(1)).save(booking);
    }

    /**
     * Creación de bookings controlando excepciones.
     * @throws SaveErrorException
     */
    @Test
    public void createBooking_ExceptionTest() throws SaveErrorException {
        Mockito.when(hotelService.getHotelById(hotelId)).thenReturn(hotel);
        Mockito.when(availabilityService.getAvailability(hotelId, booking.getDate_from())).thenReturn(Optional.of(new Availability()));
        assertThrows(Exception.class, () -> bookingService.createBooking(booking, hotelId));
    }

    /**
     * Test de Query controlando el retorno
     * @throws ErrorQueryException
     */
    @Test
    public void getBookingsByHotelAndDatesTest() throws ErrorQueryException {
        Mockito.when(bookingRepository.findByHotelAndDates(hotelId, booking.getDate_from(), booking.getDate_to())).thenReturn(List.of(booking));
        bookingService.getBookingsByHotelAndDates(hotelId, booking.getDate_from(), booking.getDate_to());
        Mockito.verify(bookingRepository, Mockito.times(1)).findByHotelAndDates(hotelId, booking.getDate_from(), booking.getDate_to());
    }

    /**
     * Test de Query controlando el retorno.
     * @throws ErrorQueryException
     */
    @Test
    public void getBookingWithHotelTest() throws ErrorQueryException {
        Mockito.when(bookingRepository.findBookingWithHotelById(1)).thenReturn(Optional.of(booking));
        Booking result = bookingService.getBookingWithHotel(1);
        Mockito.verify(bookingRepository, Mockito.times(1)).findBookingWithHotelById(1);
        Assertions.assertEquals(booking, result);
    }

    /**
     * Test DeleteBooking , comprobando disponibilidad.
     * @throws SaveErrorException
     */
    @Test
    public void deleteBookingByIdTest() throws SaveErrorException {
        Optional<Booking> optionalBooking = Optional.of(booking);
        Mockito.when(bookingRepository.findById(1)).thenReturn(optionalBooking);
        Optional<Availability> optionalAvailability = Optional.of(availability);
        Mockito.when(availabilityService.getAvailability(hotelId, booking.getDate_from())).thenReturn(optionalAvailability);
        bookingService.deleteBookingById(1);
        Mockito.verify(bookingRepository, Mockito.times(1)).findById(1);
        Mockito.verify(bookingRepository, Mockito.times(1)).deleteById(1);
        Mockito.verify(availabilityService, Mockito.times(1)).getAvailability(hotelId, booking.getDate_from());
        Mockito.verify(availabilityService, Mockito.times(1)).saveAvailability(availability);
    }
}