package com.atsistemas.hotelBookings.Repository;

import com.atsistemas.hotelBookings.Entity.Availability;
import com.atsistemas.hotelBookings.Entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.Optional;

public interface AvailabilityRepository extends JpaRepository<Availability,Integer>, JpaSpecificationExecutor<Availability> {
    //Realizamos una búsqueda de disponibilidad según hotel y fecha.
    @Query("SELECT a FROM Availability a WHERE a.id_hotel = :id_hotel AND a.date = :date")
    Optional<Availability>  findByHotelAndDate(@Param("id_hotel") Integer id_hotel, @Param("date") LocalDate date);

}
