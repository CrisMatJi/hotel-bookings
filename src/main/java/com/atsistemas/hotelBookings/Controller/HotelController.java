package com.atsistemas.hotelBookings.Controller;
import com.atsistemas.hotelBookings.Dto.HotelDTO;
import com.atsistemas.hotelBookings.Entity.Hotel;
import com.atsistemas.hotelBookings.Mapper.Impl.HotelMapperImpl;
import com.atsistemas.hotelBookings.Service.Impl.HotelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotels")
public class HotelController {

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
    //Creamos un Hotel, en caso de que el cliente nos envíe el ID y coincide con el que tenemos lo actualiza. En caso de que no exista ID o no lo envíe, lo creamos.
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






}









