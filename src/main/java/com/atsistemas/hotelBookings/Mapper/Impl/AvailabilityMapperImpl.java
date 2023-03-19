//package com.atsistemas.hotelBookings.Mapper.Impl;
//
//import com.atsistemas.hotelBookings.Dto.AvailabilityDTO;
//import com.atsistemas.hotelBookings.Dto.HotelDTO;
//import com.atsistemas.hotelBookings.Entity.Availability;
//import com.atsistemas.hotelBookings.Entity.Hotel;
//import com.atsistemas.hotelBookings.Mapper.AvailabilityMapper;
//import com.atsistemas.hotelBookings.Mapper.HotelMapper;
//
//import org.modelmapper.ModelMapper;
//
//import java.util.List;
//
//public class AvailabilityMapperImpl implements AvailabilityMapper<Availability, AvailabilityDTO> {
//
//    private ModelMapper modelMapper = new ModelMapper();
//
//    @Override
//    public Availability toEntity(AvailabilityDTO dto) {
//        return modelMapper.map(dto, Availability.class);
//    }
//
//    @Override
//    public AvailabilityDTO toDTO(Availability entity) {
//        return modelMapper.map(entity, AvailabilityDTO.class);
//    }
//    @Override
//    public List<HotelDTO> listToDTO(List<Hotel> listaHotel) {
//        return null;
//    }
//}


