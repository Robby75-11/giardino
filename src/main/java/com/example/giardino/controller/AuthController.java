package com.example.giardino.controller;

import com.example.giardino.dto.LoginRequest;
import com.example.giardino.dto.LoginResponse;
import com.example.giardino.dto.RegisterRequestDto;
import com.example.giardino.model.Utente;
import com.example.giardino.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // 🔹 Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("message", e.getMessage()));
        }
    }

    // 🔹 Registrazione
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto request) {
        try {
            // 1️⃣ Crea e registra l'utente
            Utente utente = authService.registerUtente(request);

            // 2️⃣ Genera token JWT
            String token = authService.generateToken(utente);

            // 3️⃣ Restituisci token + dati utente
            return ResponseEntity.ok(
                    new LoginResponse(
                            token,
                            utente,                // utente senza password
                            utente.getEmail(),
                            utente.getRole()
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}