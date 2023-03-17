//package com.atsistemas.hotelBookings.Mapper.Impl;
//
//import com.atsistemas.hotelBookings.Dto.BookingDTO;
//import com.atsistemas.hotelBookings.Entity.Booking;
//import com.atsistemas.hotelBookings.Mapper.DTOMapper;
//import org.modelmapper.ModelMapper;
//
//
//public class BookingMapperImpl implements DTOMapper<Booking, BookingDTO> {
//
//    private ModelMapper modelMapper = new ModelMapper();
//
//    @Override
//    public Booking toEntity(BookingDTO dto) {
//        return modelMapper.map(dto, Booking.class);
//    }
//
//    @Override
//    public BookingDTO toDTO(Booking entity) {
//        return modelMapper.map(entity, BookingDTO.class);
//    }
//}
