package br.com.cursomicrosservicos.productapi.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import br.com.cursomicrosservicos.productapi.config.exceptions.ExceptionAuthentication;
import br.com.cursomicrosservicos.productapi.dto.jwt.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${app-config.secrets.api-secret}")
    private String apiSecret;

    public void validateAuthorization(String token) {
        String accessToken = extractToken(token);
        
        try {

            Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(apiSecret.getBytes()))
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
            
            JwtResponse user = JwtResponse.getUser(claims);
            if (user == null || user.getId() == null) {
                throw new ExceptionAuthentication("The user is not valid.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionAuthentication("Error while trying to proccess the Acces Token");
        }
    }

    private String extractToken(String token) {
        if (token == null || token.isBlank()) {
            throw new ExceptionAuthentication("The access token was not informed.");
        }
        if (token.contains(" ")) {
            return token.split(" ")[1];
        }
        return token;
    }
    
}