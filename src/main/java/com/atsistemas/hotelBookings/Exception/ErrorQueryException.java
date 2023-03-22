package com.atsistemas.hotelBookings.Exception;

import org.springframework.http.HttpStatus;

public class ErrorQueryException extends CustomException {
    private  String MSG = "Error with query ";
    private final HttpStatus code = HttpStatus.NO_CONTENT;
    private Integer idHotel;
    public ErrorQueryException() {
        super();
    }
    public String getMessageError(){
        return MSG;
    }
    public HttpStatus getErrorCode(){
        return code;
    }


}