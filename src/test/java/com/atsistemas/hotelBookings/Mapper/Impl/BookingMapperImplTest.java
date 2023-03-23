package com.atsistemas.hotelBookings.Mapper.Impl;

import com.atsistemas.hotelBookings.Dto.BookingDTO;
import com.atsistemas.hotelBookings.Entity.Booking;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookingMapperImplTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BookingMapperImpl bookingMapper;
    private Booking booking;
    private BookingDTO bookingDTO;

    @Before
    public void setup() {
        booking = new Booking();
        booking.setId(1);
        booking.setEmail("c.mateos.jimenez@gmail.com");
        booking.setId_hotel(1);
        booking.setDate_from(LocalDate.of(2023, 03, 20));
        booking.setDate_from(LocalDate.of(2023, 03, 20));
        //----------------------------------------------------------------
        bookingDTO = new BookingDTO();
        bookingDTO.setId(1);
        bookingDTO.setEmail("c.mateos.jimenez@gmail.com");
        bookingDTO.setId_hotel(1);
        bookingDTO.setDate_from(LocalDate.of(2023, 03, 20));
        bookingDTO.setDate_from(LocalDate.of(2023, 03, 20));
    }

    @Test
    public void testToEntity() {
        when(modelMapper.map(bookingDTO, Booking.class)).thenReturn(booking);
        Booking result = bookingMapper.toEntity(bookingDTO);
        assertEquals(booking, result);
    }

    @Test
    public void testToDTO() {
        when(modelMapper.map(booking, BookingDTO.class)).thenReturn(bookingDTO);
        BookingDTO result = bookingMapper.toDTO(booking);
        assertEquals(bookingDTO, result);
    }

    @Test
    public void testToDTOList() {
        List<BookingDTO> bookingDTOList = new ArrayList<>();
        bookingDTOList.add(bookingDTO);
        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(modelMapper.map(bookingList.get(0), BookingDTO.class)).thenReturn(bookingDTO);
        List<BookingDTO> result = bookingMapper.listToDTO(bookingList);
        assertEquals(bookingDTOList, result);
    }
}

