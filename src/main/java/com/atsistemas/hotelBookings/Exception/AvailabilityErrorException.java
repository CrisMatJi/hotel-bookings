package com.atsistemas.hotelBookings.Exception;

import org.springframework.http.HttpStatus;

public class AvailabilityErrorException extends CustomException{
    private  String MSG = "Your booking could not be managed due to lack of availability";
    private final HttpStatus code = HttpStatus.CONFLICT;

    public AvailabilityErrorException() {
        super();
    }
    public String getMessageError(){
        return MSG;
    }
    public HttpStatus getErrorCode(){
        return code;
    }

}