package com.example.giardino.security;

import com.example.giardino.model.Cliente;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JwtTool {

    @Value("${jwt.secret}")
    private String chiaveSegreta;

    @Value("${jwt.duration}")
    private long durata;

    private java.security.Key getSigningKey() {
        return Keys.hmacShaKeyFor(chiaveSegreta.getBytes(StandardCharsets.UTF_8));
    }

    // Metodo principale: crea token da User
    public String createToken(Cliente cliente) {
        Map<String, Object> claims = Map.of(
                "roles", List.of("ROLE_" + cliente.getRole().name())
        );

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(cliente.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + durata))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Verifica token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Estrae username
    public String getUsernameFromToken(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);

        return claimsJws.getBody().getSubject();
    }

    // Estrae ruoli
    public List<String> getRolesFromToken(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);

        return (List<String>) claimsJws.getBody().get("roles");
    }
}