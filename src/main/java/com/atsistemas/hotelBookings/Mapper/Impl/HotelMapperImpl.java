package com.atsistemas.hotelBookings.Mapper.Impl;

import com.atsistemas.hotelBookings.Dto.HotelDTO;
import com.atsistemas.hotelBookings.Entity.Hotel;
import com.atsistemas.hotelBookings.Mapper.HotelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HotelMapperImpl implements HotelMapper<Hotel, HotelDTO> {

    private ModelMapper modelMapper = new ModelMapper();
    
    @Override
    public Hotel toEntity(HotelDTO dto) {
        return modelMapper.map(dto, Hotel.class);
    }

    @Override
    public HotelDTO toDTO(Hotel entity) {
        return modelMapper.map(entity, HotelDTO.class);
    }
    public List<HotelDTO> listToDTO(List<Hotel> listaHotel) {
        return listaHotel.stream()
                .map(hotel -> modelMapper.map(hotel, HotelDTO.class))
                .collect(Collectors.toList());
    }

}

