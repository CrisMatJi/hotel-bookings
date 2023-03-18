package com.atsistemas.hotelBookings.Dto;

import java.time.LocalDate;

public class BookingDTO {
    private Integer id;
    private Integer id_hotel;
    private LocalDate date_from;
    private LocalDate date_to;
    private String email;

    public BookingDTO() {
    }

    public BookingDTO(Integer id, Integer id_hotel, LocalDate date_from, LocalDate date_to, String email) {
        this.id = id;
        this.id_hotel = id_hotel;
        this.date_from = date_from;
        this.date_to = date_to;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_hotel() {
        return id_hotel;
    }

    public void setId_hotel(Integer id_hotel) {
        this.id_hotel = id_hotel;
    }

    public LocalDate getDate_from() {
        return date_from;
    }

    public void setDate_from(LocalDate date_from) {
        this.date_from = date_from;
    }

    public LocalDate getDate_to() {
        return date_to;
    }

    public void setDate_to(LocalDate date_to) {
        this.date_to = date_to;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
