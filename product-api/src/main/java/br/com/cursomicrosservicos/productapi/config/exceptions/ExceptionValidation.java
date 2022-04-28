package br.com.cursomicrosservicos.productapi.config.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExceptionValidation extends RuntimeException {

    public ExceptionValidation(String message) {
        super(message);
    }
    
}