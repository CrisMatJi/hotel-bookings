package com.atsistemas.hotelBookings.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<Object> handleHotelException(CustomException ex){
        Map<String, String> map = new HashMap<String, String>();
        map.put("Error", ex.getMessageError());
        return new ResponseEntity<Object>(map,ex.getErrorCode());
    }
}
