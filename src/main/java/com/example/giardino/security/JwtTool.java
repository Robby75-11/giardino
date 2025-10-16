package com.example.giardino.security;

import com.example.giardino.model.Utente;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
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

    // 🔹 Crea token da Utente
    public String createToken(Utente utente) {
        Map<String, Object> claims = Map.of(
                "roles", List.of("ROLE_" + utente.getRole().name()),
                "id", utente.getId()
        );

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(utente.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + durata))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 🔹 Verifica token
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

    // 🔹 Estrae email dal token
    public String getEmailFromToken(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);

        return claimsJws.getBody().getSubject();
    }

    // 🔹 Estrae ruoli dal token
    public List<String> getRolesFromToken(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);

        return (List<String>) claimsJws.getBody().get("roles");
    }

    // 🔹 Estrae ID utente dal token
    public Long getUserIdFromToken(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);

        Object id = claimsJws.getBody().get("id");
        if (id instanceof Integer) return ((Integer) id).longValue();
        if (id instanceof Long) return (Long) id;
        return null;
    }
}
