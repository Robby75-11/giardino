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
    public List<Prenotazione> getAll() {
        return prenotazioneService.getAll();
    }

    // 🔹 Prenotazione per ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('UTENTE','AMMINISTRATORE')")
    public ResponseEntity<Prenotazione> getById(@PathVariable Long id,
                                                @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) {
        try {
            Prenotazione p = prenotazioneService.getById(id);

            // Se utente normale, può vedere solo le proprie prenotazioni
            Utente utenteLoggato = utenteRepository.findByEmail(currentUser.getUsername())
                    .orElseThrow(() -> new RuntimeException("Utente non trovato"));

            if (utenteLoggato.getRole().name().equals("UTENTE") && !p.getUtente().getId().equals(utenteLoggato.getId())) {
                return ResponseEntity.status(403).build();
            }

            return ResponseEntity.ok(p);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
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

        // Se è un utente normale, forza la prenotazione a suo nome (evita che prenoti per altri)
        if (utenteLoggato.getRole().name().equals("UTENTE")) {
            dto.setUtenteId(utenteLoggato.getId());
        }

        Prenotazione saved = prenotazioneService.create(dto, utenteLoggato);
        return ResponseEntity.ok(saved);
    }

    // 🔹 Aggiorna prenotazione (solo admin)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('UTENTE', 'AMMINISTRATORE')")
    public ResponseEntity<Prenotazione> update(@PathVariable Long id, @RequestBody PrenotazioneRequestDto dto) {
        try {
            Prenotazione updated = prenotazioneService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 🔹 Elimina prenotazione (solo admin)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AMMINISTRATORE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        prenotazioneService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Prenotazioni utente loggato
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('UTENTE', 'AMMINISTRATORE)")
    public List<Prenotazione> getMyPrenotazioni(@AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) {
        Utente utenteLoggato = utenteRepository.findByEmail(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        return prenotazioneService.getByUtenteId(utenteLoggato.getId());
    }

    // 🔹 Prenotazioni per parrucchiere (admin)
    @GetMapping("/parrucchiere/{parrucchiereId}")
    @PreAuthorize("hasRole('AMMINISTRATORE')")
    public List<Prenotazione> getByParrucchiere(@PathVariable Long parrucchiereId) {
        return prenotazioneService.getByParrucchiereId(parrucchiereId);
    }
}
