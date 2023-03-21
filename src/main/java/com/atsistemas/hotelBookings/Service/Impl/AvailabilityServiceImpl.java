package com.atsistemas.hotelBookings.Service.Impl;
import com.atsistemas.hotelBookings.Entity.Availability;
import com.atsistemas.hotelBookings.Entity.Hotel;
import com.atsistemas.hotelBookings.Repository.AvailabilityRepository;
import com.atsistemas.hotelBookings.Repository.HotelRepository;
import com.atsistemas.hotelBookings.Service.AvailabilityService;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {
    AvailabilityRepository availabilityRepository;
    HotelRepository hotelRepository;

    AvailabilityServiceImpl(HotelRepository hotelRepository, AvailabilityRepository availabilityRepository) {
        this.hotelRepository = hotelRepository;
        this.availabilityRepository = availabilityRepository;
    }

    //Generamos bucle para recorrer todas las fechas y almacenar la disponibilidad.
    @Override
    public void createAvailability(Integer hotelId, LocalDate startDate, LocalDate endDate, Integer rooms) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found with id: " + hotelId));
        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            Optional<Availability> existingAvailability = availabilityRepository.findByHotelAndDate(hotel, date);
            Availability availability;
            if (existingAvailability.isPresent()) {
                availability = existingAvailability.get();
                availability.setRooms(availability.getRooms() + rooms);
            } else {
                availability = new Availability();
                availability.setHotel(hotel);
                availability.setDate(date);
                availability.setRooms(rooms);
                availability.setId_hotel(hotelId);
            }
            availabilityRepository.save(availability);
        }
    }

    @Override
    public Optional<Availability> findByHotelAndDate(Hotel hotel, LocalDate date) {
        return availabilityRepository.findByHotelAndDate(hotel, date);
    }
}


