package br.com.cursomicrosservicos.productapi.config.exceptions;

import lombok.Data;

@Data
public class ExceptionDatails {

    private int status;
    private String message;
    
}