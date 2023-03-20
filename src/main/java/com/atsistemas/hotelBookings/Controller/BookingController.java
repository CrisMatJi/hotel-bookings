package com.atsistemas.hotelBookings.Controller;

import com.atsistemas.hotelBookings.Dto.BookingDTO;
import com.atsistemas.hotelBookings.Entity.Booking;
import com.atsistemas.hotelBookings.Mapper.Impl.BookingMapperImpl;
import com.atsistemas.hotelBookings.Service.Impl.BookingServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private BookingServiceImpl bookingService;
    private BookingMapperImpl bookingMapper;


    BookingController(BookingServiceImpl bookingService, BookingMapperImpl bookingMapper) {
        this.bookingService = bookingService;
        this.bookingMapper = bookingMapper;
    }

    // Reserva de habitación: Dado un hotel, unas fechas de entrada y salida y un email, se creará una reserva.
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO) {
        try {
            Booking booking = bookingMapper.toEntity(bookingDTO);
            Booking createdBooking = bookingService.createBooking(booking);
            BookingDTO createdBookingDTO = bookingMapper.toDTO(createdBooking);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBookingDTO);
        } catch (Exception e) {
            // Manejo de excepciones
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Consultar las reservas de un hotel en una fecha determinada
    @GetMapping("/{id}")
    public ResponseEntity<List<BookingDTO>> getBookingsByHotelAndDates(
            @PathVariable(value = "id") Integer hotelId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            List<Booking> bookings = bookingService.getBookingsByHotelAndDates(hotelId, startDate, endDate);
            List<BookingDTO> bookingDTOs = bookingMapper.listToDTO(bookings);
            return ResponseEntity.ok().body(bookingDTOs);
        } catch (Exception e) {
            // Manejo de excepciones
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    //Consultar las reservas de un hotel en una fecha determinada
    @GetMapping("/search/{id}")
    public ResponseEntity<Booking> getBookingWithHotel(
            @PathVariable(value = "id") Integer bookingId){
        try {
            Booking booking = bookingService.getBookingWithHotel(bookingId);
            BookingDTO bookingDTO = bookingMapper.toDTO(booking);
            return ResponseEntity.ok().body(booking);
        } catch (Exception e) {
            // Manejo de excepciones
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}







