package com.atsistemas.hotelBookings.Controller;

import com.atsistemas.hotelBookings.Dto.BookingDTO;
import com.atsistemas.hotelBookings.Entity.Booking;
import com.atsistemas.hotelBookings.Mapper.Impl.BookingMapperImpl;
import com.atsistemas.hotelBookings.Service.Impl.BookingServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
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
    /**
     * Inyección de Beans por constructor
     */
    private BookingServiceImpl bookingService;
    private BookingMapperImpl bookingMapper;

    BookingController(BookingServiceImpl bookingService, BookingMapperImpl bookingMapper) {
        this.bookingService = bookingService;
        this.bookingMapper = bookingMapper;
    }

    /**
     * Reserva de habitación: Dado un hotel, unas fechas de entrada y salida y un email, se creará una reserva.
      * @param hotelId
     * @param bookingDTO
     * @return ResponseEntity<BookingDTO>
     */
    @Operation(summary = "Dado un hotel, unas fechas de entrada y salida y un email, se creará una reserva. ")
    @PostMapping(value = "/{hotelId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingDTO> createBooking(@PathVariable Integer hotelId,
                                                    @RequestBody BookingDTO bookingDTO) {
            Booking booking = bookingMapper.toEntity(bookingDTO);
            Booking createdBooking = bookingService.createBooking(booking, hotelId);
            BookingDTO createdBookingDTO = bookingMapper.toDTO(createdBooking);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBookingDTO);
    }

    /**
     * Consultar las reservas de un hotel en una fecha determinada
     * @param hotelId
     * @param startDate
     * @param endDate
     * @return ResponseEntity<List<BookingDTO>>
     */
    @Operation(summary = "Consultar las reservas de un hotel en una fecha determinada")
    @GetMapping("/{id}")
    public ResponseEntity<List<BookingDTO>> getBookingsByHotelAndDates(
            @PathVariable(value = "id") Integer hotelId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<Booking> bookings = this.bookingService.getBookingsByHotelAndDates(hotelId, startDate, endDate);
        List<BookingDTO> bookingDTOs = this.bookingMapper.listToDTO(bookings);
            return ResponseEntity.ok().body(bookingDTOs);
    }

    /**
     * Consultar reserva por ID y el hotel asociado
     * @param bookingId
     * @return ResponseEntity<BookingDTO>
     */
    @GetMapping("/search/{id}")
    public ResponseEntity<BookingDTO> getBookingWithHotel(
            @PathVariable(value = "id") Integer bookingId) {
            Booking booking = bookingService.getBookingWithHotel(bookingId);
            BookingDTO bookingDTO = bookingMapper.toDTO(booking);
            return ResponseEntity.ok().body(bookingDTO);
    }

    /**
     * Eliminar una reserva, controlamos si existe el ID dentro del método y restamos las habitaciones para esas fechas.
     * @param bookingId
     */
    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void bookingDelete(@PathVariable("id") Integer bookingId) {
        bookingService.deleteBookingById(bookingId);
    }
}