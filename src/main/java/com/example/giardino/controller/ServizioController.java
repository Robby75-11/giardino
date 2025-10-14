package com.example.giardino.controller;

import com.example.giardino.model.Servizio;
import com.example.giardino.service.ServizioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servizi")
public class ServizioController {

    private final ServizioService servizioService;

    public ServizioController(ServizioService servizioService) {
        this.servizioService = servizioService;
    }

    // 🔹 Tutti i servizi
    @GetMapping
    public List<Servizio> getAll() {
        return servizioService.getAllServizi();
    }

    // 🔹 Servizio singolo
    @GetMapping("/{id}")
    public ResponseEntity<Servizio> getById(@PathVariable Long id) {
        try {
            Servizio servizio = servizioService.getServizioById(id);
            return ResponseEntity.ok(servizio);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 🔹 Crea nuovo servizio (solo admin)
    @PostMapping
    @PreAuthorize("hasRole('AMMINISTRATORE')")
    public Servizio create(@RequestBody Servizio servizio) {
        return servizioService.save(servizio);
    }

    // 🔹 Aggiorna servizio (solo admin)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('AMMINISTRATORE')")
    public ResponseEntity<Servizio> update(@PathVariable Long id, @RequestBody Servizio servizio) {
        try {
            Servizio existing = servizioService.getServizioById(id);
            existing.setNome(servizio.getNome());
            existing.setPrezzo(servizio.getPrezzo());
            existing.setDurata(servizio.getDurata());
            Servizio updated = servizioService.save(existing);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 🔹 Elimina servizio (solo admin)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AMMINISTRATORE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        servizioService.deleteServizio(id);
        return ResponseEntity.noContent().build();
    }
}
