package com.atsistemas.hotelBookings.Controller;

import com.atsistemas.hotelBookings.Dto.BookingDTO;
import com.atsistemas.hotelBookings.Entity.Booking;
import com.atsistemas.hotelBookings.Mapper.Impl.BookingMapperImpl;
import com.atsistemas.hotelBookings.Service.Impl.BookingServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.bytebuddy.asm.Advice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookingControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BookingServiceImpl bookingService;

    @Mock
    private BookingMapperImpl bookingMapper;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void testCreateBooking() throws Exception {
        //Insercción de datos
        Booking booking = new Booking();
        BookingDTO bookingDTO = new BookingDTO();
        booking.setId_hotel(1);
        booking.setDate_to(LocalDate.of(2023,03,01));
        booking.setDate_from(LocalDate.of(2023,03,05));
        booking.setEmail("test@gmail.com");
        bookingDTO.setId_hotel(1);
        bookingDTO.setDate_to(LocalDate.of(2023,03,01));
        bookingDTO.setDate_from(LocalDate.of(2023,03,05));
        bookingDTO.setEmail("test@gmail.com");
        Integer hotelId = 1;

        given(bookingMapper.toEntity(bookingDTO)).willReturn(booking);
        given(bookingService.createBooking(booking, hotelId)).willReturn(booking);
        given(bookingMapper.toDTO(booking)).willReturn(bookingDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String bookingJson = objectMapper.writeValueAsString(bookingDTO);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/bookings/" + hotelId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(bookingJson);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.email", is(bookingDTO.getEmail())));

    }
    @Test
    public void testGetBookingsByHotelAndDates() throws Exception {
        Integer hotelId = 1;
        LocalDate startDate = LocalDate.of(2023, 3, 1);
        LocalDate endDate = LocalDate.of(2023, 3, 10);

        Booking booking1 = new Booking();
        booking1.setId(1);
        booking1.setId_hotel(hotelId);
        booking1.setDate_from(startDate);
        booking1.setDate_to(endDate);
        booking1.setEmail("client1@example.com");

        Booking booking2 = new Booking();
        booking2.setId(2);
        booking2.setId_hotel(hotelId);
        booking2.setDate_from(startDate.plusDays(1));
        booking2.setDate_to(endDate.minusDays(1));
        booking2.setEmail("client2@example.com");

        List<Booking> bookings = Arrays.asList(booking1, booking2);

//        given(bookingService.getBookingsByHotelAndDates(hotelId, startDate, endDate)).willReturn(bookings);
//        given(bookingMapper.listToDTO(bookings)).willReturn(Arrays.asList(
//                new BookingDTO(booking1.getId(),booking1.getId_hotel(),booking1.getDate_from(),booking1.getDate_to(),booking1.getEmail()),
//                new BookingDTO(booking2)
//        ));

        mockMvc.perform(MockMvcRequestBuilders.get("/bookings/" + hotelId)
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", notNullValue()))
                .andExpect(jsonPath("$[*].id", greaterThan(0)));

        verify(bookingService, times(1)).getBookingsByHotelAndDates(hotelId, startDate, endDate);
        verify(bookingMapper, times(1)).listToDTO(bookings);
    }

}
