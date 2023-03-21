package com.atsistemas.hotelBookings.Service.Impl;
import com.atsistemas.hotelBookings.Dto.FilterHotel;
import com.atsistemas.hotelBookings.Entity.Availability;
import com.atsistemas.hotelBookings.Entity.Hotel;
import com.atsistemas.hotelBookings.Repository.AvailabilityRepository;
import com.atsistemas.hotelBookings.Repository.HotelRepository;
import com.atsistemas.hotelBookings.Service.AvailabilityService;
import com.atsistemas.hotelBookings.Utilities.FilterAvailability;
import com.atsistemas.hotelBookings.Utilities.FilterBooking;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.time.LocalDate;
import java.util.List;
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

    @Override
    public List<Hotel> findByavailability(FilterBooking filterBooking, FilterHotel filterHotel) {
        Specification<Hotel> spec = Specification.where(null);
        spec = dateFilter(filterBooking.getDate_from(), filterBooking.getDate_to());

        if (filterHotel.getName() != null) {
            Specification<Hotel> nameSpec = nameFilter(filterHotel.getName());
            spec = spec.and(nameSpec);
        }

        if (filterHotel.getCategory() != null) {
            Specification<Hotel> categorySpec = categoryFilter(filterHotel.getCategory());
            spec = spec.and(categorySpec);
        }

        return hotelRepository.findAll(spec);
    }

    public Specification<Hotel> categoryFilter(Integer category){
        Specification<Hotel> categorySpec = (Root<Hotel> root, CriteriaQuery<?> query,
        CriteriaBuilder criteriaBuilder) -> criteriaBuilder.equal(root.get("category"),category);
        return categorySpec;
    }

    public Specification<Hotel> nameFilter(String name){
        Specification<Hotel> nameSpec = (Root<Hotel> root, CriteriaQuery<?> query,
        CriteriaBuilder criteriaBuilder) -> criteriaBuilder.equal(root.get("name"),name);
        return nameSpec;
    }


    public Specification<Hotel> dateFilter(LocalDate startDate, LocalDate endDate) {
        Specification<Hotel> dateSpec = (Root<Hotel> root, CriteriaQuery<?> query,
                                                   CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.between(root.get("startDate"), startDate, endDate),
                        criteriaBuilder.between(root.get("endDate"), startDate, endDate)
                );






//            Subquery<Long> subquery = query.subquery(Long.class);
//            Root<Availability> subRoot = subquery.from(Availability.class);
//            subquery.select(subRoot.get("hotel").get("id"))
//                    .where(criteriaBuilder.or(
//                            criteriaBuilder.between(subRoot.get("fechaEntrada"), startDate, endDate),
//                            criteriaBuilder.between(subRoot.get("fechaSalida"), startDate, endDate)
//                    ));
//            return criteriaBuilder.in(root.get("id")).value(subquery);
        return dateSpec;
        };

    }


