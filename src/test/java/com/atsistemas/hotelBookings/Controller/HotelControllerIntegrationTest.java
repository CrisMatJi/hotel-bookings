package com.atsistemas.hotelBookings.Controller;

import com.atsistemas.hotelBookings.Dto.HotelDTO;
import com.atsistemas.hotelBookings.Entity.Hotel;
import com.atsistemas.hotelBookings.Repository.HotelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext
public class HotelControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HotelRepository hotelRepository;

    @Test
    public void testGetAllHotels() throws Exception {
        mockMvc.perform(get("/hotels")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)));

    }

    @Test
    public void getHotelById_returnsCorrectHotel() throws Exception {
        // Arrange
        Integer hotelId = 1;
        Hotel expectedHotel = new Hotel(hotelId, "Hotel Modificado", 4
        );

        // Act and Assert
        mockMvc.perform(get("/hotels/" + hotelId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedHotel)));
    }

    @Test
    public void createHotel_returnsCreatedHotel() throws Exception {
        Hotel hotel = new Hotel(null, "New Hotel", 3);

        // Act and Assert
        mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotel)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(hotel.getName()))
                .andExpect(jsonPath("$.category").value(hotel.getCategory()));
    }

    @Test
    public void updateHotel_returnsUpdatedHotel() throws Exception {
        // Arrange
        Integer hotelId = 1;
        Hotel originalHotel = new Hotel(hotelId, "Hotel Plaza", 2);
        hotelRepository.save(originalHotel);

        Hotel updatedHotel = new Hotel(hotelId, "Hotel Modificado", 4);

        // Act and Assert
        mockMvc.perform(put("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedHotel)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(hotelId))
                .andExpect(jsonPath("$.name").value(updatedHotel.getName()))
                .andExpect(jsonPath("$.category").value(updatedHotel.getCategory()));
    }

    @Test
    public void consultAvailability_returnsCorrectHotels() throws Exception {
        // Arrange
        LocalDate startDate = LocalDate.of(2023, 4, 1);
        LocalDate endDate = LocalDate.of(2023, 4, 2);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/availabilities")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Hotel Modificado"))
                .andExpect(jsonPath("$[0].category").value(4));
    }


}