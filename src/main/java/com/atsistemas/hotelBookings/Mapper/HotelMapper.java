package com.atsistemas.hotelBookings.Mapper;

import com.atsistemas.hotelBookings.Dto.HotelDTO;
import com.atsistemas.hotelBookings.Entity.Hotel;

import java.util.List;

public interface HotelMapper<E, D> {
    Hotel toEntity(HotelDTO hotelDTO);
    HotelDTO toDTO(Hotel hotel);
    List<HotelDTO> listToDTO(List<Hotel> listaHotel);
}