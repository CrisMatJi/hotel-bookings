package com.atsistemas.hotelBookings.Controller;

import com.atsistemas.hotelBookings.Service.Impl.AvailabilityServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;


@RestController
@RequestMapping("/availabilities")
public class AvailabilityController {
    //Inyección de Beans por constructor.
    private AvailabilityServiceImpl availabilityServiceImpl;
    AvailabilityController(AvailabilityServiceImpl availabilityService) {
        this.availabilityServiceImpl = availabilityService;
    }

    //Insertamos disponibilidad segun fecha de entrada y salida . Tenemos control de excepciones y no tratamos en este caso con DTO
    @PostMapping("/{hotelId}")
    public ResponseEntity<Void> createAvailability(
            @PathVariable Integer hotelId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam Integer rooms) {
        availabilityServiceImpl.createAvailability(hotelId, startDate, endDate, rooms);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}