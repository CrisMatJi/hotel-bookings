package com.atsistemas.hotelBookings.Controller;
import com.atsistemas.hotelBookings.Dto.HotelDTO;
import com.atsistemas.hotelBookings.Entity.Hotel;
import com.atsistemas.hotelBookings.Mapper.Impl.HotelMapperImpl;
import com.atsistemas.hotelBookings.Service.Impl.HotelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    HotelServiceImpl hotelService;

    @Autowired
    HotelMapperImpl hotelMapper;
    //Consultamos el listado de hoteles
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HotelDTO>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        if (hotels == null || hotels.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<HotelDTO> hotelDTOs = hotelMapper.listToDTO(hotels);
        return ResponseEntity.ok(hotelDTOs);
    }
    //Consultamos un Hotel , según su ID
    @GetMapping(value ="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable Integer id) {
        Optional<Hotel> optionalHotel = hotelService.getHotelById(id);
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            HotelDTO hotelDTO = hotelMapper.toDTO(hotel);
            return ResponseEntity.ok(hotelDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}


