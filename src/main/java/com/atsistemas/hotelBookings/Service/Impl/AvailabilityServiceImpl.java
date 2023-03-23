package com.atsistemas.hotelBookings.Service.Impl;
import com.atsistemas.hotelBookings.Entity.Availability;
import com.atsistemas.hotelBookings.Entity.Hotel;
import com.atsistemas.hotelBookings.Exception.ErrorQueryException;
import com.atsistemas.hotelBookings.Exception.SaveErrorException;
import com.atsistemas.hotelBookings.Repository.AvailabilityRepository;
import com.atsistemas.hotelBookings.Service.AvailabilityService;
import com.atsistemas.hotelBookings.Service.HotelService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {
    /**
     * Inyección de beans por constructor.
     */

    AvailabilityRepository availabilityRepository;
    HotelService hotelService;
    AvailabilityServiceImpl(AvailabilityRepository availabilityRepository, HotelServiceImpl hotelService) {
        this.availabilityRepository = availabilityRepository;
        this.hotelService = hotelService;
    }

    /**
     * Abriendo disponibilidad según Hotel, fecha de entrada/salida y habitaciones.
     * @param hotelId
     * @param startDate
     * @param endDate
     * @param rooms
     * @throws SaveErrorException
     */
    @Override
    public void createAvailability(Integer hotelId, LocalDate startDate, LocalDate endDate, Integer rooms) throws SaveErrorException {
        Hotel hotel = hotelService.getHotelById(hotelId);
        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            Optional<Availability> existingAvailability = getAvailability(hotelId, date);
            Availability availability;
            if (existingAvailability.isPresent()) {
                availability = existingAvailability.get();
                availability.setRooms(availability.getRooms() + rooms);
            } else {
                availability = new Availability(date,hotel,rooms);
            }
            try {
                availabilityRepository.save(availability);
            } catch (Exception e) {
                throw new SaveErrorException();
            }
        }
    }

    /**
     * Consultar la disponibilidad dado Hotel y Fecha.
     * @param hotelId
     * @param date
     * @return
     * @throws ErrorQueryException
     */
    public Optional<Availability> getAvailability(Integer hotelId, LocalDate date) throws ErrorQueryException {
        try {
            Optional<Availability> optionalAvailability = availabilityRepository.findByHotelAndDate(hotelId, date);
            return availabilityRepository.findByHotelAndDate(hotelId, date);
        }catch (Exception e) {
            throw new ErrorQueryException();
        }
    }

    /**
     * Almacenar Disponibilidad
     * @param availability
     * @throws SaveErrorException
     */
    public void saveAvailability(Availability availability) throws SaveErrorException{
        try {
            availabilityRepository.save(availability);
        }catch (Exception e) {
            throw new SaveErrorException();
        }
    }
}