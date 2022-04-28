package br.com.cursomicrosservicos.productapi.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionGlobalHandler {

    @ExceptionHandler(ExceptionValidation.class)
    public ResponseEntity<?> handleValidationException(ExceptionValidation exceptionValidation) {
        ExceptionDatails datails = new ExceptionDatails();
        datails.setStatus(HttpStatus.BAD_REQUEST.value());
        datails.setMessage(exceptionValidation.getMessage());
        return new ResponseEntity<>(datails, HttpStatus.BAD_REQUEST);
    }
    
}