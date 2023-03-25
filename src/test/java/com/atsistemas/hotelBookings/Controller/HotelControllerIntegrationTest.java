package com.atsistemas.hotelBookings.Controller;

import com.atsistemas.hotelBookings.Entity.Hotel;
import com.atsistemas.hotelBookings.Repository.HotelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import javax.transaction.Transactional;
import java.time.LocalDate;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class HotelControllerIntegrationTest {
    /**
     * Inyección de Mocks y repository
     */
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HotelRepository hotelRepository;

    /**
     * Test controller findall(), comprobamos tamaño de la BD que ya conocemos.
     * @throws Exception
     */
    @Test
    public void testGetAllHotels() throws Exception {
        mockMvc.perform(get("/hotels")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)));
    }

    /**
     * Test GetHotelById controlando que devuelve el que conocemos.
     * @throws Exception
     */
    @Test
    public void getHotelByIdTest() throws Exception {
        Integer hotelId = 1;
        Hotel expectedHotel = new Hotel(hotelId, "Hotel Plaza", 1
        );
        //----------------------------------------------------------------
        mockMvc.perform(get("/hotels/" + hotelId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedHotel)));
    }

    /**
     * Test CreateHotel controller.
     * @throws Exception
     */
    @Test
    public void createHotelTest() throws Exception {
        Hotel hotel = new Hotel(null, "New Hotel", 3);
        //----------------------------------------------------------------
        mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotel)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(hotel.getName()))
                .andExpect(jsonPath("$.category").value(hotel.getCategory()));
    }

    /**
     * Test update Hotel Controller.
     * @throws Exception
     */
    @Test
    public void updateHotel_returnsUpdatedHotel() throws Exception {
        Integer hotelId = 1;
        Hotel originalHotel = new Hotel(hotelId, "Hotel Plaza", 2);
        hotelRepository.save(originalHotel);
        //----------------------------------------------------------------
        Hotel updatedHotel = new Hotel(hotelId, "Hotel Modificado", 4);
        //----------------------------------------------------------------
        mockMvc.perform(put("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedHotel)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(hotelId))
                .andExpect(jsonPath("$.name").value(updatedHotel.getName()))
                .andExpect(jsonPath("$.category").value(updatedHotel.getCategory()));
    }

    /**
     * Consulta de disponibilidad test controller.
     * @throws Exception
     */
    @Test
    public void consultAvailabilityTest() throws Exception {
        LocalDate startDate = LocalDate.of(2023, 4, 1);
        LocalDate endDate = LocalDate.of(2023, 4, 2);
        //----------------------------------------------------------------
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/hotels/availabilities")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Hotel Plaza"))
                .andExpect(jsonPath("$[0].category").value(1));
    }
}