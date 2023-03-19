package com.atsistemas.hotelBookings.Mapper.Impl;

import com.atsistemas.hotelBookings.Dto.BookingDTO;
import com.atsistemas.hotelBookings.Dto.HotelDTO;
import com.atsistemas.hotelBookings.Entity.Booking;
import com.atsistemas.hotelBookings.Entity.Hotel;
import com.atsistemas.hotelBookings.Mapper.BookingMapper;
import com.atsistemas.hotelBookings.Mapper.HotelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingMapperImpl implements BookingMapper<Booking, BookingDTO> {

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public Booking toEntity(BookingDTO dto) {
        return modelMapper.map(dto, Booking.class);
    }

    @Override
    public BookingDTO toDTO(Booking entity) {
        return modelMapper.map(entity, BookingDTO.class);
    }
    @Override
    public List<BookingDTO> listToDTO(List<Booking> bookingList) {
        return bookingList.stream()
                .map(booking -> modelMapper.map(booking, BookingDTO.class))
                .collect(Collectors.toList());
    }




}

