package com.example.giardino.service;

import com.example.giardino.dto.PrenotazioneRequestDto;
import com.example.giardino.model.Utente;
import com.example.giardino.model.Prenotazione;
import com.example.giardino.repository.ParrucchiereRepository;
import com.example.giardino.repository.PrenotazioneRepository;
import com.example.giardino.repository.ServizioRepository;
import com.example.giardino.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private ParrucchiereRepository parrucchiereRepository;

    @Autowired
    private ServizioRepository servizioRepository;

    // 🔹 Tutte le prenotazioni
    public List<Prenotazione> getAll() {
        return prenotazioneRepository.findAll();
    }

    // 🔹 Prenotazione per ID
    public Prenotazione getById(Long id) {
        return prenotazioneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));

    }

    // 🔹 Crea prenotazione
    public Prenotazione create(PrenotazioneRequestDto dto, Utente utenteLoggato) {
        Prenotazione prenotazione = new Prenotazione();

        prenotazione.setUtente(utenteLoggato);

        prenotazione.setParrucchiere(parrucchiereRepository.findById(dto.getParrucchiereId())
                .orElseThrow(() -> new RuntimeException("Parrucchiere non trovato")));
        prenotazione.setServizio(servizioRepository.findById(dto.getServizioId())
                .orElseThrow(() -> new RuntimeException("Servizio non trovato")));
        prenotazione.setData(dto.getData());
        prenotazione.setStato(dto.getStato());

        return prenotazioneRepository.save(prenotazione);
    }

    // 🔹 Aggiorna prenotazione
    public Prenotazione update(Long id, PrenotazioneRequestDto dto) {
        Prenotazione existing = prenotazioneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));

        if (dto.getUtenteId() != null) {
            existing.setUtente(utenteRepository.findById(dto.getUtenteId())
                    .orElseThrow(() -> new RuntimeException("Utente non trovato")));
        }

        if (dto.getParrucchiereId() != null) {
            existing.setParrucchiere(parrucchiereRepository.findById(dto.getParrucchiereId())
                    .orElseThrow(() -> new RuntimeException("Parrucchiere non trovato")));
        }

        if (dto.getServizioId() != null) {
            existing.setServizio(servizioRepository.findById(dto.getServizioId())
                    .orElseThrow(() -> new RuntimeException("Servizio non trovato")));
        }

        if (dto.getData() != null) {
            existing.setData(dto.getData());
        }

        if (dto.getStato() != null) {
            existing.setStato(dto.getStato());
        }

        return prenotazioneRepository.save(existing);
    }

    // 🔹 Elimina prenotazione
    public void delete(Long id) {
        prenotazioneRepository.deleteById(id);
    }

    // 🔹 Filtra per utente
    public List<Prenotazione> getByUtenteId(Long utenteId) {
        return prenotazioneRepository.findByUtenteId(utenteId);
    }

    // 🔹 Filtra per parrucchiere
    public List<Prenotazione> getByParrucchiereId(Long parrucchiereId) {
        return prenotazioneRepository.findByParrucchiereId(parrucchiereId);
    }
}
