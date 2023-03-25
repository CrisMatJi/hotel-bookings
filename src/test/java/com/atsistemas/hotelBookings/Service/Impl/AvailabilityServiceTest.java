package com.atsistemas.hotelBookings.Service.Impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import com.atsistemas.hotelBookings.Entity.Availability;
import com.atsistemas.hotelBookings.Entity.Hotel;
import com.atsistemas.hotelBookings.Exception.SaveErrorException;
import com.atsistemas.hotelBookings.Repository.AvailabilityRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AvailabilityServiceTest {
    /**
     * Tests availability
     */
    @Mock
    private AvailabilityRepository availabilityRepository;

    @Mock
    private HotelServiceImpl hotelService;

    @InjectMocks
    private AvailabilityServiceImpl availabilityService;
    private static final Integer HOTEL_ID = 1;
    private static final LocalDate START_DATE = LocalDate.of(2023, 4, 1);
    private static final LocalDate END_DATE = LocalDate.of(2023, 4, 3);
    private static final Integer ROOMS = 2;

    /**
     * Preparación del testing
     */
    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
        // Configurar el comportamiento del mock availabilityRepository
        when(availabilityRepository.save(any(Availability.class)))
                .thenAnswer(invocation -> {
                    Availability availability = invocation.getArgument(0);
                    availability.setId(1); // asignar un ID a la entidad creada
                    return availability;
                });
    }

    /**
     * Test para probar la apertura de disponibilidad.
     * @throws SaveErrorException
     */
    @Test
    public void testCreateAvailability() throws SaveErrorException {
        Hotel hotel = new Hotel();
        hotel.setId(HOTEL_ID);
        Availability existingAvailability = new Availability(START_DATE, hotel, 5);
        when(hotelService.getHotelById(HOTEL_ID)).thenReturn(hotel);
        when(availabilityRepository.findByHotelAndDate(any(Integer.class), any(LocalDate.class)))
                .thenReturn(Optional.of(existingAvailability));
        when(availabilityRepository.save(any(Availability.class)))
                .thenReturn(existingAvailability);
        availabilityService.createAvailability(HOTEL_ID, START_DATE, END_DATE, ROOMS);
        Assertions.assertEquals(11, existingAvailability.getRooms());
    }

    /**
     * Test para comprobar la disponibilidad
     */
    @Test
    public void testGetAvailability() {
        when(availabilityRepository.findByHotelAndDate(any(Integer.class), any(LocalDate.class)))
                .thenReturn(Optional.empty());
        Optional<Availability> result = availabilityService
                .getAvailability(HOTEL_ID, LocalDate.of(2023, 3, 22));
        Assertions.assertTrue(result.isEmpty());
    }

    /**
     * Test donde almacenamos disponibilidad.
     */
    @Test
    public void testSaveAvailability() {
        Availability availability = new Availability();
        availabilityService.saveAvailability(availability);
        Assertions.assertNotNull(availability.getId());
    }
}