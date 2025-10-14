package com.example.giardino.controller;

import com.example.giardino.dto.LoginRequest;
import com.example.giardino.dto.LoginResponse;
import com.example.giardino.dto.RegisterRequestDto;
import com.example.giardino.model.Cliente;
import com.example.giardino.service.AuthService;
import com.example.giardino.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private ClienteService customerService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto request) {
        try {
            // 1️⃣ Crea e registra il cliente (salvando anche la password codificata)
            Cliente cliente = authService.registerCliente(request);

            // 2️⃣ Genera token JWT per il cliente
            String token = authService.generateToken(cliente);

            // 3️⃣ Restituisci il token + dati cliente
            return ResponseEntity.ok(new LoginResponse(token, cliente));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body( e.getMessage());
        }
    }
}
