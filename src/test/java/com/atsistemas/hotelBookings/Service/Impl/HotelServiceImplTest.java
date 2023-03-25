package com.atsistemas.hotelBookings.Service.Impl;

import com.atsistemas.hotelBookings.Entity.Hotel;
import com.atsistemas.hotelBookings.Exception.HotelNotFoundException;
import com.atsistemas.hotelBookings.Exception.SaveErrorException;
import com.atsistemas.hotelBookings.Repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HotelServiceImplTest {
    /**
     * Inyección de mocks
     */
    @Mock
    private HotelRepository hotelRepositoryMock;

    @InjectMocks
    private HotelServiceImpl hotelService;

    /**
     * Test Findall() Hotels.
     */
    @Test
    public void testGetAllHotels() {
        Hotel hotel1 = new Hotel();
        hotel1.setId(1);
        hotel1.setName("Hotel 1");
        hotel1.setCategory(3);
        //----------------------------------------------------------------
        Hotel hotel2 = new Hotel();
        hotel2.setId(2);
        hotel2.setName("Hotel 2");
        hotel2.setCategory(4);
        //----------------------------------------------------------------
        List<Hotel> hotelList = new ArrayList<>();
        hotelList.add(hotel1);
        hotelList.add(hotel2);
        //----------------------------------------------------------------
        Mockito.when(hotelRepositoryMock.findAll()).thenReturn(hotelList);
        //----------------------------------------------------------------
        List<Hotel> resultList = hotelService.getAllHotels();
        //----------------------------------------------------------------
        assertEquals(2, resultList.size());
        assertEquals("Hotel 1", resultList.get(0).getName());
        assertEquals(4, resultList.get(1).getCategory());
    }

    /**
     * Test FindbyID hotel
     * @throws HotelNotFoundException
     */
    @Test
    public void testGetHotelById() throws HotelNotFoundException {
        Hotel hotel = new Hotel();
        hotel.setId(1);
        hotel.setName("Hotel 1");
        hotel.setCategory(3);
        //----------------------------------------------------------------
        Mockito.when(hotelRepositoryMock.findById(1)).thenReturn(Optional.of(hotel));
        //----------------------------------------------------------------
        Hotel result = hotelService.getHotelById(1);
        //----------------------------------------------------------------
        assertEquals("Hotel 1", result.getName());
        assertEquals(3, result.getCategory());
    }

    /**
     * Test GetHotel HotelNotFoundIdException
     */
    @Test
    public void testGetHotelByIdException() {
        //----------------------------------------------------------------
        Mockito.when(hotelRepositoryMock.findById(1)).thenReturn(Optional.empty());
        //----------------------------------------------------------------
        assertThrows(HotelNotFoundException.class, () -> {
            hotelService.getHotelById(1);
        });
    }

    /**
     * Test save hotel
     * @throws SaveErrorException
     */
    @Test
    public void testSaveHotel() throws SaveErrorException {
        Hotel hotel = new Hotel();
        hotel.setName("Hotel 1");
        hotel.setCategory(3);
        //----------------------------------------------------------------
        Mockito.when(hotelRepositoryMock.save(hotel)).thenReturn(hotel);
        //----------------------------------------------------------------
        Hotel result = hotelService.saveHotel(hotel);
        //----------------------------------------------------------------
        assertEquals("Hotel 1", result.getName());
        assertEquals(3, result.getCategory());
    }

    /**
     * Test save hotel , controlando excepcion.
     * @throws SaveErrorException
     */
    @Test
    public void testSaveHotelException() throws SaveErrorException {
        Hotel hotel = new Hotel();
        hotel.setName("Hotel 1");
        hotel.setCategory(3);
        //----------------------------------------------------------------
        Mockito.when(hotelRepositoryMock.save(hotel)).thenThrow(new RuntimeException());
        assertThrows(SaveErrorException.class, () -> {
            hotelService.saveHotel(hotel);
        });
    }

    /**
     * Test para updatear Hotel.
     * @throws SaveErrorException
     */
    @Test
    public void testUpdateHotel() throws SaveErrorException {
        Hotel hotel = new Hotel();
        hotel.setId(1);
        hotel.setName("Hotel 1");
        hotel.setCategory(3);
        //----------------------------------------------------------------
        Mockito.when(hotelRepositoryMock.save(hotel)).thenReturn(hotel);
        Hotel result = hotelService.updateHotel(hotel, 4, "Hotel updated");
        assertEquals("Hotel updated", result.getName());
        assertEquals(4, result.getCategory());
    }

}
