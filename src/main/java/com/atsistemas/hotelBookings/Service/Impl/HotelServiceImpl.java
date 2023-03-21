package com.atsistemas.hotelBookings.Service.Impl;

import com.atsistemas.hotelBookings.Entity.Availability;
import com.atsistemas.hotelBookings.Entity.Booking;
import com.atsistemas.hotelBookings.Entity.Hotel;
import com.atsistemas.hotelBookings.Repository.AvailabilityRepository;
import com.atsistemas.hotelBookings.Repository.HotelRepository;
import com.atsistemas.hotelBookings.Service.HotelService;
import com.atsistemas.hotelBookings.Utilities.FilterHotel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    private HotelRepository hotelRepository;
    private AvailabilityRepository availabilityRepository;

    public HotelServiceImpl(HotelRepository hotelRepository, AvailabilityRepository availabilityRepository) {
        this.hotelRepository = hotelRepository;
        this.availabilityRepository = availabilityRepository;
    }
    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll().stream().sorted(Comparator.comparing(Hotel::getId)).collect(Collectors.toList());
    }
    @Override
    public Optional<Hotel> getHotelById(Integer id) {
        return hotelRepository.findById(id);
    }
    @Override
    @Transactional
    public Hotel createHotel(Hotel hotel){
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> findByAvailability(LocalDate startDate, LocalDate endDate, String name, Integer category) {
        Specification<Hotel> spec = Specification.where(null);
        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        Optional<List<Hotel>> optionalHotels = hotelRepository.findHotelsByAvailability2(startDate, endDate, days);
//        Optional<List<Hotel>> optionalHotels = hotelRepository.findHotelsByAvailability2(startDate,endDate);
        if (optionalHotels.isPresent()) {
            List<Integer> availableHotelIds = optionalHotels.get().stream()
                    .map(Hotel::getId)
                    .collect(Collectors.toList());
            Specification<Hotel> availabilitySpec = (root, query, cb) -> root.get("id").in(availableHotelIds);
            spec = spec.and(availabilitySpec);
        }



        if (name != null) {
            Specification<Hotel> nameSpec = nameFilter(name);
            spec = spec.and(nameSpec);
        }

        if (category != null) {
            Specification<Hotel> categorySpec = categoryFilter(category);
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
                        criteriaBuilder.between(root.get("date_from"), startDate, endDate),
                        criteriaBuilder.between(root.get("date_to"), startDate, endDate)
                );

        return dateSpec;
    }


}
