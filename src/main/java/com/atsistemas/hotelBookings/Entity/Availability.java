package com.atsistemas.hotelBookings.Entity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name="availabilities")
public class Availability {

    //Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "date")
    LocalDate date;

    @Column(name ="rooms")
    Integer rooms;

    @Column(name = "id_hotel")
    Integer id_hotel;

    //Relationships
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_hotel", nullable = false, insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Hotel hotel;

    //Constructors
    public Availability() {
    }
    public Availability( LocalDate date, Hotel hotel, Integer rooms) {
        this.date = date;
        this.hotel = hotel;
        this.rooms = rooms;
    }
    public Availability( LocalDate date, Hotel hotel, Integer rooms, Integer id_hotel) {
        this.date = date;
        this.hotel = hotel;
        this.rooms = rooms;
        this.id_hotel = id_hotel;
    }

    //Gettters and Setters
    public Hotel getHotel() {
        return hotel;
    }
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
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
    public Integer getRooms() {
        return rooms;
    }
    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }
    public Integer getId_hotel() {
        return id_hotel;
    }
    public void setId_hotel(Integer id_hotel) {
        this.id_hotel = id_hotel;
    }
}