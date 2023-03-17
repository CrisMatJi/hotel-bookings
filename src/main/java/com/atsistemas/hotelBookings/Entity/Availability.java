package com.atsistemas.hotelBookings.Entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="availabilities")
public class Availability {
    /*
        Hotels(id, name, category): Datos de los hoteles
        Availabilities(id, date, id_hotel, rooms): Disponibilidad de habitaciones por dia yhotel. La columna rooms marca la disponibilidad actualizada del hotel.
        Bookings(id, id_hotel, date_from, date_to, email): Datos de las reservas.
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;
    @Column(name = "date")
    LocalDate date;
    @Column(name = "id_hotel")
    Integer id_hotel;
    @Column(name ="rooms")
    Integer rooms;

    public Availability() {
    }

    public Availability(Integer id, LocalDate date, Integer id_hotel, Integer rooms) {
        this.id = id;
        this.date = date;
        this.id_hotel = id_hotel;
        this.rooms = rooms;
    }

    public Integer getId() {
        return id;
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