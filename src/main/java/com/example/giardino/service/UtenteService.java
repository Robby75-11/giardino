package com.example.giardino.service;

import com.example.giardino.dto.RegisterRequestDto;
import com.example.giardino.enumeration.Role;
import com.example.giardino.model.Utente;
import com.example.giardino.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 🔹 Tutti gli utenti
    public List<Utente> getAllUtenti() {
        return utenteRepository.findAll();
    }

    // 🔹 Utente per ID
    public Utente getUtenteById(Long id) {
        return utenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + id));
    }

    // 🔹 Utente per email
    public Utente getUtenteByEmail(String email) {
        return utenteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con email: " + email));
    }

    // 🔹 Registrazione utente
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

    // 🔹 Aggiorna utente
    public Utente updateUtente(Long id, RegisterRequestDto request) {
        Utente utente = getUtenteById(id);

        if (request.getNome() != null) utente.setNome(request.getNome());
        if (request.getCognome() != null) utente.setCognome(request.getCognome());
        if (request.getTelefono() != null) utente.setTelefono(request.getTelefono());
        if (request.getUsername() != null) utente.setUsername(request.getUsername());
        if (request.getEmail() != null) utente.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            utente.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getRole() != null) utente.setRole(request.getRole());

        return utenteRepository.save(utente);
    }

    // 🔹 Elimina utente
    public void deleteUtente(Long id) {
        Utente utente = getUtenteById(id);
        utenteRepository.delete(utente);
    }
}
