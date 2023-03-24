package com.atsistemas.hotelBookings.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingTest {

    private Booking booking;

    @Mock
    private Hotel hotel;

    @BeforeEach
    public void setup() {
        LocalDate dateFrom = LocalDate.of(2023, 04, 01);
        LocalDate dateTo = LocalDate.of(2023, 04, 05);
        String email = "test@test.com";
        Integer idHotel = 1;

        booking = new Booking(dateFrom, dateTo, email, idHotel);
        booking.setId(1);
        booking.setHotel(hotel);
    }

    @Test
    public void testGettersAndSetters() {
        LocalDate dateFrom = LocalDate.of(2023, 04, 01);
        LocalDate dateTo = LocalDate.of(2023, 04, 05);
        String email = "test@test.com";
        Integer idHotel = 1;

        assertEquals(1, booking.getId());
        assertEquals(dateFrom, booking.getDate_from());
        assertEquals(dateTo, booking.getDate_to());
        assertEquals(email, booking.getEmail());
        assertEquals(idHotel, booking.getId_hotel());
        assertEquals(hotel, booking.getHotel());

        LocalDate newDateFrom = LocalDate.of(2023, 05, 01);
        LocalDate newDateTo = LocalDate.of(2023, 05, 05);
        String newEmail = "newtest@test.com";
        Integer newIdHotel = 2;

        booking.setDate_from(newDateFrom);
        booking.setDate_to(newDateTo);
        booking.setEmail(newEmail);
        booking.setId_hotel(newIdHotel);
        booking.setHotel(null);

        assertEquals(newDateFrom, booking.getDate_from());
        assertEquals(newDateTo, booking.getDate_to());
        assertEquals(newEmail, booking.getEmail());
        assertEquals(newIdHotel, booking.getId_hotel());
        assertEquals(null, booking.getHotel());
    }
}