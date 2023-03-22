package com.atsistemas.hotelBookings.Service.Impl;
import com.atsistemas.hotelBookings.Entity.Availability;
import com.atsistemas.hotelBookings.Repository.AvailabilityRepository;
import com.atsistemas.hotelBookings.Service.AvailabilityService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {
    //Inyección de beans por constructor.
    AvailabilityRepository availabilityRepository;
    AvailabilityServiceImpl(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    //Método para crear disponibilidad. Generamos bucle para recorrer todas las fechas y almacenar la disponibilidad.
    @Override
    public void createAvailability(Integer hotelId, LocalDate startDate, LocalDate endDate, Integer rooms) {
        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            Optional<Availability> existingAvailability = availabilityRepository.findByHotelAndDate(hotelId, date);
            Availability availability;
            if (existingAvailability.isPresent()) {
                availability = existingAvailability.get();
                availability.setRooms(availability.getRooms() + rooms);
            } else {
                availability = new Availability(date,hotelId,rooms);
            }
            availabilityRepository.save(availability);
        }
    }
}


