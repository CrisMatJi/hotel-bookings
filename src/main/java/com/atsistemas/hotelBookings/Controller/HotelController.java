package com.atsistemas.hotelBookings.Controller;
import com.atsistemas.hotelBookings.Dto.HotelDTO;
import com.atsistemas.hotelBookings.Entity.Hotel;
import com.atsistemas.hotelBookings.Mapper.Impl.HotelMapperImpl;
import com.atsistemas.hotelBookings.Service.Impl.HotelServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    //Inyección de Beans por constructor.
    private HotelServiceImpl hotelService;
    private HotelMapperImpl hotelMapper;
    public HotelController(HotelServiceImpl hotelService, HotelMapperImpl hotelMapper){
        this.hotelService = hotelService;
        this.hotelMapper = hotelMapper;
    }

    //Consultamos el listado de hoteles y los devolvemos odernados por ID
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HotelDTO>> getAllHotels() {
        try {

            List<Hotel> hotels = hotelService.getAllHotels();
            if (hotels == null || hotels.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            List<HotelDTO> hotelDTOs = hotelMapper.listToDTO(hotels);
            return ResponseEntity.ok(hotelDTOs);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //Consultamos un Hotel , según su ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable(value = "id") Integer id) {
        try {
            Optional<Hotel> optionalHotel = hotelService.getHotelById(id);
            if (optionalHotel.isPresent()) {
                Hotel hotel = optionalHotel.get();
                HotelDTO hotelDTO = hotelMapper.toDTO(hotel);
                return ResponseEntity.ok(hotelDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //Creamos un Hotel, sólo sin ID.
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HotelDTO> createHotel(@RequestBody HotelDTO hotelDTO) {
        try {
            Hotel hotel = hotelMapper.toEntity(hotelDTO);
            Hotel createdHotel = hotelService.createHotel(hotel);
            HotelDTO createdHotelDTO = hotelMapper.toDTO(createdHotel);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdHotelDTO);
        } catch (Exception e) {
            // Manejo de excepciones
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //Actualizamos Hotel y nos aseguramos que nos envíe un ID.
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HotelDTO> updateHotel(@RequestBody HotelDTO hotelDTO) {
        try {
            Hotel hotel = hotelMapper.toEntity(hotelDTO);
            if (hotelService.getHotelById(hotel.getId()).isEmpty()) {
                throw new IllegalArgumentException("Para crear un nuevo Hotel , use método POST");
            }
            Hotel createdHotel = hotelService.createHotel(hotel);
            HotelDTO createdHotelDTO = hotelMapper.toDTO(createdHotel);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdHotelDTO);
        } catch (Exception e) {
            // Manejo de excepciones
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping(value="/availabilities", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HotelDTO>> consultAvailability(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                                              @RequestParam(required = false) String name,
                                                              @RequestParam(required = false) Integer category) {
        try {
            List<Hotel> hotels = hotelService.findByAvailability(startDate,endDate,name, category);
            if (hotels == null || hotels.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            List<HotelDTO> hotelDTOs = hotelMapper.listToDTO(hotels);
            return ResponseEntity.ok(hotelDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}