package br.com.cursomicrosservicos.productapi.config;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import br.com.cursomicrosservicos.productapi.config.exceptions.ExceptionValidation;

public class RequestUtil {

    public static HttpServletRequest getCurrentRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ExceptionValidation("The current request could not be proccessed.");
        }
    }
    
}