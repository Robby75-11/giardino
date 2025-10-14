package com.example.giardino.controller;

import com.example.giardino.dto.PrenotazioneRequestDto;
import com.example.giardino.model.Prenotazione;
import com.example.giardino.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
@CrossOrigin
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @GetMapping
    public List<Prenotazione> getAll() { return prenotazioneService.getAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Prenotazione> getById(@PathVariable Long id) {
        try {
            Prenotazione p = prenotazioneService.getById(id);
            return ResponseEntity.ok(p);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Prenotazione create(@RequestBody PrenotazioneRequestDto dto) {
        return prenotazioneService.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('AMMINISTRATORE')")
    public ResponseEntity<Prenotazione> update(@PathVariable Long id, @RequestBody PrenotazioneRequestDto dto) {
        try {
            Prenotazione updated = prenotazioneService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AMMINISTRATORE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        prenotazioneService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Filtra per cliente
    @GetMapping("/cliente/{clienteId}")
    public List<Prenotazione> getByCliente(@PathVariable Long clienteId) {
        return prenotazioneService.getByClienteId(clienteId);
    }

    // 🔹 Filtra per parrucchiere
    @GetMapping("/parrucchiere/{parrucchiereId}")
    public List<Prenotazione> getByParrucchiere(@PathVariable Long parrucchiereId) {
        return prenotazioneService.getByParrucchiereId(parrucchiereId);
    }
}
