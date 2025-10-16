package com.example.giardino.controller;

import com.example.giardino.dto.RegisterRequestDto;
import com.example.giardino.dto.UtenteResponseDto;
import com.example.giardino.enumeration.Role;
import com.example.giardino.model.Utente;
import com.example.giardino.service.AuthService;
import com.example.giardino.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/utenti")
@CrossOrigin
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private AuthService authService;

    // 🔹 Tutti gli utenti (solo admin)
    @GetMapping
    @PreAuthorize("hasRole('AMMINISTRATORE')")
    public List<UtenteResponseDto> getAllUtenti() {
        return utenteService.getAllUtenti()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // 🔹 Utente per ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('UTENTE','AMMINISTRATORE')")
    public ResponseEntity<UtenteResponseDto> getUtenteById(@PathVariable Long id,
                                                           @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) {
        Utente utente = utenteService.getUtenteById(id);

        // Controllo: utente normale può vedere solo se stesso
        if (currentUser != null
                && !utente.getEmail().equals(currentUser.getUsername())
                && utente.getRole() == Role.UTENTE) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(mapToDto(utente));
    }

    // 🔹 Registrazione nuovo utente
    @PostMapping("/register")
    public ResponseEntity<UtenteResponseDto> register(@RequestBody RegisterRequestDto request) {
        Utente utente = authService.registerUtente(request);
        return ResponseEntity.ok(mapToDto(utente));
    }

    // 🔹 Aggiorna utente (solo admin o utente stesso)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('UTENTE','AMMINISTRATORE')")
    public ResponseEntity<UtenteResponseDto> update(@PathVariable Long id,
                                                    @RequestBody RegisterRequestDto request,
                                                    @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) {
        Utente utente = utenteService.getUtenteById(id);

        // Controllo: utente normale può aggiornare solo se stesso
        if (currentUser != null
                && !utente.getEmail().equals(currentUser.getUsername())
                && utente.getRole() == Role.UTENTE) {
            return ResponseEntity.status(403).build();
        }

        Utente updated = utenteService.updateUtente(id, request);
        return ResponseEntity.ok(mapToDto(updated));
    }

    // 🔹 Elimina utente (solo admin)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AMMINISTRATORE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        utenteService.deleteUtente(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Mappatura entity → DTO
    private UtenteResponseDto mapToDto(Utente utente) {
        return new UtenteResponseDto(
                utente.getId(),
                utente.getEmail(),
                utente.getRole(),
                utente.getNome(),
                utente.getCognome(),
                utente.getTelefono(),
                utente.getUsername()
        );
    }
}
