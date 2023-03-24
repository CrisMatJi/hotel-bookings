package com.atsistemas.hotelBookings.Controller;
import com.atsistemas.hotelBookings.Dto.HotelDTO;
import com.atsistemas.hotelBookings.Entity.Hotel;
import com.atsistemas.hotelBookings.Mapper.Impl.HotelMapperImpl;
import com.atsistemas.hotelBookings.Service.Impl.HotelServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/hotels")
public class HotelController {
    /**
     * Inyección de Beans por constructor.
     */

    private HotelServiceImpl hotelService;
    private HotelMapperImpl hotelMapper;
    public HotelController(HotelServiceImpl hotelService, HotelMapperImpl hotelMapper){
        this.hotelService = hotelService;
        this.hotelMapper = hotelMapper;
    }

    /**
     * Consultamos el listado de hoteles y los devolvemos odernados por ID
     * @return ResponseEntity<List<HotelDTO>
     */
    @Operation(summary = "Consultar el listado completo de hoteles, ordenados por ID")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HotelDTO>> getAllHotels() {
            List<Hotel> hotels = hotelService.getAllHotels();
            List<HotelDTO> hotelDTOs = hotelMapper.listToDTO(hotels);
            return ResponseEntity.ok(hotelDTOs);
    }

    /**
     * Consultamos un Hotel , según su ID
     * @param id
     * @return ResponseEntity<HotelDTO>
     */
    @Operation(summary = "Consultar hotel según ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable(value = "id") Integer id) {
            Hotel hotel= hotelService.getHotelById(id);
            HotelDTO hotelDTO = hotelMapper.toDTO(hotel);
            return ResponseEntity.ok(hotelDTO);
    }


    /**
     * Creamos un Hotel, sólo sin ID.
     * @param hotelDTO
     * @return ResponseEntity<HotelDTO>
     */
    @Operation(summary = "Crear hotel")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HotelDTO> createHotel(@RequestBody HotelDTO hotelDTO) {
            Hotel hotel = hotelMapper.toEntity(hotelDTO);
            Hotel createdHotel = hotelService.saveHotel(hotel);
            HotelDTO createdHotelDTO = hotelMapper.toDTO(createdHotel);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdHotelDTO);
        }


    /**
     * Actualizamos Hotel y nos aseguramos que nos envíe un ID.
      * @param hotelDTO
     * @return ResponseEntity<HotelDTO>
     */
    @Operation(summary = "Actualizar hotel , adjuntar datos en el cuerpo(JSON)")
    @PutMapping
    public ResponseEntity<HotelDTO> updateHotel(@RequestBody HotelDTO hotelDTO) {
            Hotel hotel = hotelService.getHotelById(hotelDTO.getId());
            HotelDTO createdHotelDTO = hotelMapper.toDTO(hotelService.updateHotel(hotel, hotelDTO.getCategory(), hotelDTO.getName()));
            return ResponseEntity.status(HttpStatus.CREATED).body(createdHotelDTO);
    }

    /**
     * Consultamos disponibilidad según rango de fechas(obligatorias) , nombre y categoría de hotel (opcionales)
     * @param startDate
     * @param endDate
     * @param name
     * @param category
     * @return List<HotelDTO>
     */
    @Operation(summary = "Consultar disponibilidad de los hoteles según rango de fechas, opcionalmente se le puede agregar nombre y categoría de hotel para filtrar el resultado")
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