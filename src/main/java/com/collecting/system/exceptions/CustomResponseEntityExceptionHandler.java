package com.collecting.system.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by Adrian Rusinek on 24.07.2020
 **/
@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleOrderIdException(OrderIdException e) {
        return new ResponseEntity<>(new OrderIdExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
