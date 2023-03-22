package com.atsistemas.hotelBookings.Exception;

import org.springframework.http.HttpStatus;

public class HotelNotFoundException extends CustomException {
    private  String MSG = "Hotel not found";
    private final HttpStatus code = HttpStatus.NOT_FOUND;
    private Integer idHotel;
    public HotelNotFoundException(Integer idHotel) {
        super();
        this.idHotel = idHotel;
    }

    public String getMessageError(){
        return MSG + " with ID: " + idHotel;
    }
    public HttpStatus getErrorCode(){
        return code;
    }


}
