package com.atsistemas.hotelBookings.Entity;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "id_hotel")
    private int id_hotel;
    @Column(name = "date_from")
    private LocalDate date_from;
    @Column(name = "date_to")
    private LocalDate date_to;
    @Column(name = "email")
    private String email;

    public Booking() {
    }

    public Booking(int id, int id_hotel, LocalDate date_from, LocalDate date_to, String email) {
        this.id = id;
        this.id_hotel = id_hotel;
        this.date_from = date_from;
        this.date_to = date_to;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_hotel() {
        return id_hotel;
    }

    public void setId_hotel(int id_hotel) {
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
