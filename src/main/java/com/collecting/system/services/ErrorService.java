package com.collecting.system.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.stream.Collectors;

/**
 * Created by Adrian Rusinek on 24.07.2020
 **/
@Service
public class ErrorService {

    public ResponseEntity<?> validateErrors(BindingResult bindingResult) {

        return new ResponseEntity<>(
                bindingResult.getFieldErrors()
                        .stream()
                        .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)),
                HttpStatus.BAD_REQUEST);
    }
}
