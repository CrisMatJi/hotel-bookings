package com.atsistemas.hotelBookings.Entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class HotelTest {

    @Mock
    private Availability availability;

    @Mock
    private Booking booking;

    @Test
    public void testGettersAndSetters() {
        Integer id = 1;
        String name = "testHotel";
        Integer category = 3;
        List<Availability> availabilities = new ArrayList<>();
        availabilities.add(availability);
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);

        Hotel hotel = new Hotel();
        hotel.setId(id);
        hotel.setName(name);
        hotel.setCategory(category);
        hotel.setAvailabilities(availabilities);
        hotel.setBookings(bookings);

        assertEquals(id, hotel.getId());
        assertEquals(name, hotel.getName());
        assertEquals(category, hotel.getCategory());
        assertEquals(availabilities, hotel.getAvailabilities());
        assertEquals(bookings, hotel.getBookings());
    }
}
