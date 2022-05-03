package br.com.cursomicrosservicos.productapi.config.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ExceptionAuthentication extends RuntimeException {

    public ExceptionAuthentication(String message) {
        super(message);
    }
    
}