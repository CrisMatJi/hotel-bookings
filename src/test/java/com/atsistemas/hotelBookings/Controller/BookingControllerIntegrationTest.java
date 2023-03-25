package com.atsistemas.hotelBookings.Controller;

import com.atsistemas.hotelBookings.Entity.Booking;
import com.atsistemas.hotelBookings.Repository.BookingRepository;
import com.atsistemas.hotelBookings.Service.Impl.AvailabilityServiceImpl;
import com.atsistemas.hotelBookings.Service.Impl.HotelServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import javax.transaction.Transactional;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class BookingControllerIntegrationTest {
    /**
     * Inyección de Mocks
     */
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private HotelServiceImpl hotelService;
    @Autowired
    private AvailabilityServiceImpl availabilityService;

    /**
     * Test Creación de bookings controller.
     * @throws Exception
     */
    @Test
    public void createBookingTest() throws Exception {
        Booking booking = bookingRepository.findById(1).get();
        mockMvc.perform(post("/bookings/{hotelId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id_hotel").value(1))
                .andExpect(jsonPath("$.email").value(booking.getEmail()));
    }

    /**
     * Test deletebooking controller.
     * @throws Exception
     */
    @Test
    public void testDeleteBookingById() throws Exception {
        mockMvc.perform(delete("/bookings/delete/{id}", 1))
                .andExpect(status().isNoContent());
        List<Booking> bookings = bookingRepository.findAll();
        assertThat(bookings.size()).isEqualTo(9);
    }

    /**
     * Test Getbooking Controller.
     * @throws Exception
     */
    @Test
    public void getBookingWithHotelTest() throws Exception {
        Booking savedBooking = bookingRepository.findById(1).get();
        mockMvc.perform(get("/bookings/search/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(savedBooking.getId()))
                .andExpect(jsonPath("$.id_hotel").value(savedBooking.getHotel().getId()))
                .andExpect(jsonPath("$.hotel.name").value(savedBooking.getHotel().getName()))
                .andExpect(jsonPath("$.hotel.category").value(savedBooking.getHotel().getCategory()))
                .andExpect(jsonPath("$.email").value(savedBooking.getEmail()));
    }

    /**
     * Test getBookingsByHotelAndDates Controller.
     * @throws Exception
     */
    @Test
    public void getBookingsByHotelAndDatesTest() throws Exception {
        Booking booking = bookingRepository.findById(10).get();
        mockMvc.perform(get("/bookings/{id}", 5)
                        .param("startDate", "2023-04-06")
                        .param("endDate", "2023-04-09")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(booking.getId()))
                .andExpect(jsonPath("$[0].id_hotel").value(booking.getId_hotel()))
                .andExpect(jsonPath("$[0].email").value(booking.getEmail()));
    }
}