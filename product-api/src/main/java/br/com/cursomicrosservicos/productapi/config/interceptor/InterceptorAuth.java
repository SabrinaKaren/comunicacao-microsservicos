package br.com.cursomicrosservicos.productapi.config.interceptor;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import br.com.cursomicrosservicos.productapi.config.exceptions.ExceptionValidation;
import br.com.cursomicrosservicos.productapi.services.JwtService;

public class InterceptorAuth implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String TRANSACTION_ID = "transactionid";

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (isOptions(request)) {
            return true;
        }

        
        if (request.getHeader(TRANSACTION_ID) == null || request.getHeader(TRANSACTION_ID).isEmpty()) {
            throw new ExceptionValidation("The transactionid header is required.");
        }

        String authorization = request.getHeader(AUTHORIZATION);
        jwtService.validateAuthorization(authorization);

        request.setAttribute("serviceid", UUID.randomUUID().toString());

        return true;
    }

    private boolean isOptions(HttpServletRequest request) {
        return HttpMethod.OPTIONS.name().equals(request.getMethod());
    }
    
}