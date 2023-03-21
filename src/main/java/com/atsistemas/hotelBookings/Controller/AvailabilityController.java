package com.atsistemas.hotelBookings.Controller;

import com.atsistemas.hotelBookings.Mapper.Impl.HotelMapperImpl;
import com.atsistemas.hotelBookings.Service.Impl.AvailabilityServiceImpl;
import com.atsistemas.hotelBookings.Utilities.FilterBooking;
import com.atsistemas.hotelBookings.Utilities.FilterHotel;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

import com.atsistemas.hotelBookings.Entity.Hotel;
import com.atsistemas.hotelBookings.Dto.HotelDTO;


@RestController
@RequestMapping("/availabilities")
public class AvailabilityController {


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
        try {
            availabilityServiceImpl.createAvailability(hotelId, startDate, endDate, rooms);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
