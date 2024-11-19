package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(Exception.class)
     public ResponseEntity<String> handleGenericException(Exception ex){
        return new ResponseEntity<>("An error occured: "+ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
     }
}
