package com.atsistemas.hotelBookings.Service.Impl;

import com.atsistemas.hotelBookings.Entity.Hotel;
import com.atsistemas.hotelBookings.Repository.HotelRepository;
import com.atsistemas.hotelBookings.Service.HotelService;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    private HotelRepository hotelRepository;

    public HotelServiceImpl(HotelRepository hotelRepository){
        this.hotelRepository = hotelRepository;
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




}
