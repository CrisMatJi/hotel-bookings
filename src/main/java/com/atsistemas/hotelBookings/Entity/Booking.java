package com.atsistemas.hotelBookings.Entity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date_from")
    private LocalDate date_from;
    @Column(name = "date_to")
    private LocalDate date_to;
    @Column(name = "email")
    private String email;
    @Column(name="id_hotel")
    private Integer id_hotel;

    public Integer getId_hotel() {
        return id_hotel;
    }

    public void setId_hotel(Integer id_hotel) {
        this.id_hotel = id_hotel;
    }

    //Relationships
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_hotel", nullable = false, insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Hotel hotel;

    //Constructors
    public Booking() {
    }
    public Booking( LocalDate date_from, LocalDate date_to, String email) {
        this.date_from = date_from;
        this.date_to = date_to;
        this.email = email;
    }

    //Getters and setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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
    public Hotel getHotel() {
        return hotel;
    }
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
