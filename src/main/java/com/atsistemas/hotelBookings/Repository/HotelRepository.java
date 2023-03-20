package com.atsistemas.hotelBookings.Repository;

import com.atsistemas.hotelBookings.Entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface HotelRepository extends JpaRepository<Hotel,Integer>, JpaSpecificationExecutor<Hotel> {

//    @Query("SELECT h FROM Hotel h " +
//            "WHERE h.id NOT IN (" +
//            "SELECT a.idHotel FROM Availability a " +
//            "WHERE a.date >= :fechaEntrada AND a.date <= :fechaSalida " +
//            "AND a.rooms <= (" +
//            "SELECT COALESCE(SUM(b.rooms), 0) FROM Booking b " +
//            "WHERE b.idHotel = a.idHotel AND (" +
//            "(b.dateFrom <= :fechaEntrada AND b.dateTo >= :fechaEntrada) " +
//            "OR (b.dateFrom <= :fechaSalida AND b.dateTo >= :fechaSalida) " +
//            "OR (b.dateFrom >= :fechaEntrada AND b.dateTo <= :fechaSalida)" +
//            ")" +
//            ")" +
//            ") " +
//            // filtro por nombre del hotel (opcional)
//            "AND (:nombreHotel IS NULL OR h.name LIKE %:nombreHotel%) " +
//            // filtro por categoría del hotel (opcional)
//            "AND (:categoriaHotel IS NULL OR h.category = :categoriaHotel)")
//    List<Hotel> findHotelsWithAvailability(
//            @Param("fechaEntrada") LocalDate fechaEntrada,
//            @Param("fechaSalida") LocalDate fechaSalida,
//            @Param("nombreHotel") String nombreHotel,
//            @Param("categoriaHotel") Integer categoriaHotel);


}
