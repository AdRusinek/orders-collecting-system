package com.collecting.system.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Adrian Rusinek on 24.07.2020
 **/
@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderIdException extends RuntimeException {

    public OrderIdException(String message) {
        super(message);
    }
}
