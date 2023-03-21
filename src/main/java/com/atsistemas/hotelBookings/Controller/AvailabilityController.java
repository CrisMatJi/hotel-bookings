package com.atsistemas.hotelBookings.Controller;

import com.atsistemas.hotelBookings.Dto.FilterHotel;
import com.atsistemas.hotelBookings.Mapper.Impl.HotelMapperImpl;
import com.atsistemas.hotelBookings.Service.Impl.AvailabilityServiceImpl;
import com.atsistemas.hotelBookings.Utilities.FilterBooking;
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
    private HotelMapperImpl hotelMapperImpl;


    AvailabilityController(AvailabilityServiceImpl availabilityService, HotelMapperImpl hotelMapperImpl) {
        this.availabilityServiceImpl = availabilityService;
        this.hotelMapperImpl = hotelMapperImpl;
    }

    //Insertamos disponibilidad segun fecha de entrada y salida . Tenemos control de excepciones y no tratamos en este caso con DTO
    @PostMapping("/{hotelId}")
    public ResponseEntity<Void> createAvailability(
            @PathVariable Integer hotelId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
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

    //    Consulta de disponibilidad: devolverá qué hoteles tienen disponibilidad dadas
//    unas fechas de entrada y salida (fechas obligatorio), pudiendo filtrar por nombre y categoría del hotel. Para tener disponibilidad entre las fechas, tiene que haber disponible al menos una habitación para el hotel en cada día del rango.
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HotelDTO>> consultAvailability(FilterBooking filterBooking, FilterHotel filterHotel) {
        try {
            List<Hotel> hotels = availabilityServiceImpl.findByavailability(filterBooking, filterHotel);
            if (hotels == null || hotels.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            List<HotelDTO> hotelDTOs = hotelMapperImpl.listToDTO(hotels);
            return ResponseEntity.ok(hotelDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
