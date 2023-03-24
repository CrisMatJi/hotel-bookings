package com.atsistemas.hotelBookings.Mapper.Impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import com.atsistemas.hotelBookings.Dto.HotelDTO;
import com.atsistemas.hotelBookings.Entity.Hotel;

@RunWith(MockitoJUnitRunner.class)
public class HotelMapperImplTest {

    @InjectMocks
    private HotelMapperImpl hotelMapperImpl;
    @Mock
    private ModelMapper modelMapper;
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testToEntity() {
        HotelDTO hotelDTO = new HotelDTO();
        Hotel hotel = new Hotel();
        when(modelMapper.map(hotelDTO, Hotel.class)).thenReturn(hotel);
        Hotel result = hotelMapperImpl.toEntity(hotelDTO);
        assertEquals(hotel, result);
    }

    @Test
    public void testToDTO() {
        // given
        HotelDTO hotelDTO = new HotelDTO();
        Hotel hotel = new Hotel();
        when(modelMapper.map(hotel, HotelDTO.class)).thenReturn(hotelDTO);
        HotelDTO result = hotelMapperImpl.toDTO(hotel);
        assertEquals(hotelDTO, result);
    }

    @Test
    public void testListToDTO() {
        List<Hotel> listaHotel = new ArrayList<>();
        Hotel hotel1 = new Hotel();
        hotel1.setId(1);
        hotel1.setName("Hotel1");
        Hotel hotel2 = new Hotel();
        hotel2.setId(2);
        hotel2.setName("Hotel2");
        listaHotel.add(hotel1);
        listaHotel.add(hotel2);

        HotelDTO hotelDTO1 = new HotelDTO();
        hotelDTO1.setId(1);
        hotelDTO1.setName("Hotel1");
        HotelDTO hotelDTO2 = new HotelDTO();
        hotelDTO2.setId(2);
        hotelDTO2.setName("Hotel2");
        List<HotelDTO> expected = new ArrayList<>();
        expected.add(hotelDTO1);
        expected.add(hotelDTO2);
        when(modelMapper.map(hotel1, HotelDTO.class)).thenReturn(hotelDTO1);
        when(modelMapper.map(hotel2, HotelDTO.class)).thenReturn(hotelDTO2);
        List<HotelDTO> result = hotelMapperImpl.listToDTO(listaHotel);
        assertEquals(expected, result);
    }
}
