package com.atsistemas.hotelBookings.Utilities;

import java.time.LocalDate;

public class FilterAvailability {
    private Integer id;
    private LocalDate date;
    private Integer id_hotel;
    private Integer rooms;

    public FilterAvailability() {
    }

    public Integer getId() {
        return id;
    }

    public FilterAvailability(Integer id, LocalDate date, Integer id_hotel, Integer rooms) {
        this.id = id;
        this.date = date;
        this.id_hotel = id_hotel;
        this.rooms = rooms;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getId_hotel() {
        return id_hotel;
    }

    public void setId_hotel(Integer id_hotel) {
        this.id_hotel = id_hotel;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }
}
