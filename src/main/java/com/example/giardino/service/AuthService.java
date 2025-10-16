package com.example.giardino.service;

import com.example.giardino.dto.LoginRequest;
import com.example.giardino.dto.LoginResponse;
import com.example.giardino.dto.RegisterRequestDto;
import com.example.giardino.model.Utente;
import com.example.giardino.repository.UtenteRepository;
import com.example.giardino.security.JwtTool;
import com.example.giardino.enumeration.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTool jwtTool;

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Utente utente = utenteRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        String token = jwtTool.createToken(utente);

        return new LoginResponse(token, safeUtente(utente), utente.getEmail(), utente.getRole());
    }

    public Utente registerUtente(RegisterRequestDto request) {
        if (utenteRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email già registrata");
        }

        Utente utente = new Utente();
        utente.setNome(request.getNome());
        utente.setCognome(request.getCognome());
        utente.setTelefono(request.getTelefono());
        utente.setUsername(request.getUsername());
        utente.setEmail(request.getEmail());
        utente.setPassword(passwordEncoder.encode(request.getPassword()));
        utente.setRole(request.getRole() != null ? request.getRole() : Role.UTENTE);

        return utenteRepository.save(utente);
    }

    public String generateToken(Utente utente) {
        return jwtTool.createToken(utente);
    }

    private Utente safeUtente(Utente u) {
        Utente safe = new Utente();
        safe.setId(u.getId());
        safe.setNome(u.getNome());
        safe.setCognome(u.getCognome());
        safe.setTelefono(u.getTelefono());
        safe.setUsername(u.getUsername());
        safe.setEmail(u.getEmail());
        safe.setRole(u.getRole());
        return safe;
    }
}
