package com.atsistemas.hotelBookings.Mapper;

public interface DTOMapper<E, D> {
    E toEntity(D dto);
    D toDTO(E entity);
}