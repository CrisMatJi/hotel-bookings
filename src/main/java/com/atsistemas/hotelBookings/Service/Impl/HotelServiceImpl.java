package com.atsistemas.hotelBookings.Service.Impl;

import com.atsistemas.hotelBookings.Entity.Hotel;
import com.atsistemas.hotelBookings.Repository.AvailabilityRepository;
import com.atsistemas.hotelBookings.Repository.HotelRepository;
import com.atsistemas.hotelBookings.Service.HotelService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
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

    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }
    //Obtener Listado completo de hoteles.
    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll().stream().sorted(Comparator.comparing(Hotel::getId)).collect(Collectors.toList());
    }
    //Obtener Hotel por ID
    @Override
    public Optional<Hotel> getHotelById(Integer id) {
        return hotelRepository.findById(id);
    }
    //Crear un nuevo Hotel. (ID autoincrement)
    @Override
    @Transactional
    public Hotel createHotel(Hotel hotel){
        return hotelRepository.save(hotel);
    }

    //Consultar disponibilidad. Hacemos la consulta obligatoria con @Query y el resto con criteriabuilder.
    @Override
    public List<Hotel> findByAvailability(LocalDate startDate, LocalDate endDate, String name, Integer category) {
        Specification<Hotel> spec = Specification.where(null);
        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        Optional<List<Hotel>> optionalHotels = hotelRepository.findHotelsByAvailability(startDate, endDate, days);
        if (optionalHotels.isPresent()) {
            List<Integer> availableHotelIds = optionalHotels.get().stream()
                    .map(Hotel::getId)
                    .collect(Collectors.toList());
            Specification<Hotel> availabilitySpec = (root, query, cb) -> root.get("id").in(availableHotelIds);
            spec = spec.and(availabilitySpec);
        }
        //Opcionalmente si el usuario introduce el nombre del hotel como parámetro, lo contemplamos en la consulta.
        if (name != null) {
            Specification<Hotel> nameSpec = nameFilter(name);
            spec = spec.and(nameSpec);
        }
        //Opcionalmente si el usuario introduce la categoria del hotel como parámetro, lo contemplamos en la consulta.
        if (category != null) {
            Specification<Hotel> categorySpec = categoryFilter(category);
            spec = spec.and(categorySpec);
        }
        return hotelRepository.findAll(spec);
    }

    //Filtro para obtener categoria de Hotel de manera opcional.
    public Specification<Hotel> categoryFilter(Integer category){
        Specification<Hotel> categorySpec = (Root<Hotel> root, CriteriaQuery<?> query,
                                             CriteriaBuilder criteriaBuilder) -> criteriaBuilder.equal(root.get("category"),category);
        return categorySpec;
    }
    //Filtro para obtener nombre de Hotel de manera opcional.
    public Specification<Hotel> nameFilter(String name){
        Specification<Hotel> nameSpec = (Root<Hotel> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) -> criteriaBuilder.equal(root.get("name"),name);
        return nameSpec;
    }

}
