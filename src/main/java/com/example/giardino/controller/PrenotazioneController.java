package com.example.giardino.controller;

import com.example.giardino.dto.PrenotazioneRequestDto;
import com.example.giardino.model.Utente;
import com.example.giardino.model.Prenotazione;
import com.example.giardino.repository.UtenteRepository;
import com.example.giardino.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
@CrossOrigin
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @Autowired
    private UtenteRepository utenteRepository;

    // 🔹 Tutte le prenotazioni (solo admin)
    @GetMapping
    @PreAuthorize("hasAnyRole('UTENTE', 'AMMINISTRATORE')")
    public List<Prenotazione> getAll(@AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) {
        Utente utenteLoggato = utenteRepository.findByEmail(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        if (utenteLoggato.getRole().name().equals("AMMINISTRATORE")) {
            return prenotazioneService.getAll();
        } else {
            return prenotazioneService.getByUtenteId(utenteLoggato.getId());
        }
    }

    // 🔹 Crea prenotazione
    @PostMapping
    @PreAuthorize("hasAnyRole('UTENTE', 'AMMINISTRATORE')")
    public ResponseEntity<Prenotazione> createPrenotazione(
            @RequestBody PrenotazioneRequestDto dto,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) {

        Utente utenteLoggato = utenteRepository.findByEmail(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        if (utenteLoggato.getRole().name().equals("UTENTE")) {
            dto.setUtenteId(utenteLoggato.getId());
        }

        Prenotazione saved = prenotazioneService.create(dto, utenteLoggato);
        return ResponseEntity.ok(saved);
    }

    // 🔹 Aggiorna prenotazione (solo admin)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('AMMINISTRATORE')")
    public ResponseEntity<Prenotazione> update(@PathVariable Long id, @RequestBody PrenotazioneRequestDto dto) {
        return ResponseEntity.ok(prenotazioneService.update(id, dto));
    }

    // 🔹 Elimina prenotazione (solo admin)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AMMINISTRATORE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        prenotazioneService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
