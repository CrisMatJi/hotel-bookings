package com.atsistemas.hotelBookings.Mapper.Impl;

import com.atsistemas.hotelBookings.Dto.AvailabilityDTO;
import com.atsistemas.hotelBookings.Entity.Availability;
import com.atsistemas.hotelBookings.Mapper.DTOMapper;

import org.modelmapper.ModelMapper;

public class AvailabilityMapperImpl implements DTOMapper<Availability, AvailabilityDTO> {

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public Availability toEntity(AvailabilityDTO dto) {
        return modelMapper.map(dto, Availability.class);
    }

    @Override
    public AvailabilityDTO toDTO(Availability entity) {
        return modelMapper.map(entity, AvailabilityDTO.class);
    }
}


