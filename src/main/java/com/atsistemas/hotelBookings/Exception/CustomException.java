package com.atsistemas.hotelBookings.Exception;

import org.springframework.http.HttpStatus;

abstract class CustomException extends RuntimeException {

    public CustomException() {
        super("Generic Error");
    }

    abstract String getMessageError();

    abstract HttpStatus getErrorCode();

}
