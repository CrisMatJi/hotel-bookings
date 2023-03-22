package com.atsistemas.hotelBookings.Exception;

import org.springframework.http.HttpStatus;

public class SaveErrorException extends CustomException{
    private  String MSG = "Cant save data";
    private final HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;

    public SaveErrorException() {
        super();
    }
    public String getMessageError(){
        return MSG;
    }
    public HttpStatus getErrorCode(){
        return code;
    }

}
