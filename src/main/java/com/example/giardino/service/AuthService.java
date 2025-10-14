package com.example.giardino.service;

import com.example.giardino.dto.LoginRequest;
import com.example.giardino.dto.LoginResponse;
import com.example.giardino.dto.RegisterRequestDto;
import com.example.giardino.enumeration.Role;
import com.example.giardino.model.Cliente;
import com.example.giardino.repository.ClienteRepository;
import com.example.giardino.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtTool jwtTool;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 🔐 LOGIN
    public LoginResponse login(LoginRequest request) {
        // 1️⃣ Verifica credenziali tramite Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // 2️⃣ Recupera cliente dal DB
        Cliente cliente = clienteRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Cliente non trovato"));

        // 3️⃣ Genera token JWT
        String token = jwtTool.createToken(cliente);

        // 4️⃣ Restituisce token + dati cliente
        return new LoginResponse(token, cliente);
    }

    // 🧾 REGISTRAZIONE CLIENTE
    public Cliente registerCliente(RegisterRequestDto request) {
        if (clienteRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email già registrata");
        }

        if (clienteRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username già in uso");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(request.getNome());
        cliente.setCognome(request.getCognome());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());
        cliente.setUsername(request.getUsername());
        cliente.setPassword(passwordEncoder.encode(request.getPassword()));
        cliente.setRole(Role.CLIENTE); // Ruolo di default

        return clienteRepository.save(cliente);
    }

    // 🎟️ GENERA TOKEN
    public String generateToken(Cliente cliente) {
        return jwtTool.createToken(cliente);
    }

    // 🔎 TROVA CLIENTE
    public Cliente getClienteByEmail(String email) {
        return clienteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cliente non trovato"));
    }
}