package com.atsistemas.hotelBookings.Exception;

import org.springframework.http.HttpStatus;

public class SaveErrorException extends CustomException{
    private  String MSG = "Unable to perform data storage or update operation.";
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
