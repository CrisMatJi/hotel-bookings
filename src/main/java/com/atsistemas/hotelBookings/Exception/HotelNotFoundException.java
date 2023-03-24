package com.atsistemas.hotelBookings.Exception;

import org.springframework.http.HttpStatus;

public class HotelNotFoundException extends CustomException {
    private  String MSG = "We have found an error in your query";
    private final HttpStatus code = HttpStatus.NOT_FOUND;
    private Integer idHotel;
    public HotelNotFoundException(Integer idHotel) {
        super();
        this.idHotel = idHotel;
    }
    public String getMessageError(){
        return MSG ;
    }
    public HttpStatus getErrorCode(){
        return code;
    }


}
