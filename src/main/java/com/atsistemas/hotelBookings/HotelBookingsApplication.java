package com.atsistemas.hotelBookings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableWebMvc

@SpringBootApplication
public class HotelBookingsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelBookingsApplication.class, args);
	}

}
