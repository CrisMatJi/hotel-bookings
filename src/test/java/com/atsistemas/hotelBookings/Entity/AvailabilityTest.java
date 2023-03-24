package com.atsistemas.hotelBookings.Entity;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AvailabilityTest {

    @Test
    public void testGettersAndSetters() {
        Availability availability = new Availability();
        availability.setId(1);
        availability.setDate(LocalDate.of(2023, 3, 23));
        availability.setRooms(10);

        assertNotNull(availability.getId());
        assertNotNull(availability.getDate());
        assertNotNull(availability.getRooms());

        assertEquals(1, availability.getId());
        assertEquals(LocalDate.of(2023, 3, 23), availability.getDate());
        assertEquals(10, availability.getRooms());
    }

    @Test
    public void testConstructor() {
        Hotel hotel = Mockito.mock(Hotel.class);
        Availability availability = new Availability(LocalDate.of(2023, 3, 23), hotel, 10);

        assertNotNull(availability.getDate());
        assertNotNull(availability.getHotel());
        assertNotNull(availability.getRooms());

        assertEquals(LocalDate.of(2023, 3, 23), availability.getDate());
        assertEquals(hotel, availability.getHotel());
        assertEquals(10, availability.getRooms());
    }
}
