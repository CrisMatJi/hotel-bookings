package com.atsistemas.hotelBookings.Mapper.Impl;

import com.atsistemas.hotelBookings.Dto.HotelDTO;
import com.atsistemas.hotelBookings.Entity.Hotel;
import com.atsistemas.hotelBookings.Mapper.DTOMapper;
import org.modelmapper.ModelMapper;

public class HotelMapperImpl implements DTOMapper<Hotel, HotelDTO> {

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public Hotel toEntity(HotelDTO dto) {
        return modelMapper.map(dto, Hotel.class);
    }

    @Override
    public HotelDTO toDTO(Hotel entity) {
        return modelMapper.map(entity, HotelDTO.class);
    }
}

