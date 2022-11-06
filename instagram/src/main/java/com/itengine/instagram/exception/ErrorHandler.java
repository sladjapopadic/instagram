package com.itengine.instagram.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException validationException) {
        Throwable cause = validationException.getCause();
        System.err.println(validationException.getMessage());
        if (cause != null) {
            System.err.println(cause.getMessage());
        }
        return new ResponseEntity<>(validationException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
