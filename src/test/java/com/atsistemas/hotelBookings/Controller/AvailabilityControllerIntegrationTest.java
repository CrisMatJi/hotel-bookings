package com.atsistemas.hotelBookings.Controller;

import com.atsistemas.hotelBookings.Service.Impl.AvailabilityServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.time.LocalDate;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AvailabilityControllerIntegrationTest {
    /**
     * Inyección de Beans/Mocks
     */
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AvailabilityServiceImpl availabilityServiceImpl;

    /**
     * Test de apertura de disponibilidad
     * @throws Exception
     */
    @Test
    void testCreateAvailability() throws Exception {
        Integer hotelId = 123;
        LocalDate startDate = LocalDate.of(2023, 3, 23);
        LocalDate endDate = LocalDate.of(2023, 3, 25);
        Integer rooms = 2;

        doNothing().when(availabilityServiceImpl).createAvailability(hotelId, startDate, endDate, rooms);

        // Respuesta
        MvcResult result = mockMvc.perform(post("/availabilities/{hotelId}", hotelId)
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .param("rooms", rooms.toString()))
                .andExpect(status().isCreated())
                .andReturn();

        // Verify that the service method was called with the expected parameters
        verify(availabilityServiceImpl).createAvailability(hotelId, startDate, endDate, rooms);


    }
}
