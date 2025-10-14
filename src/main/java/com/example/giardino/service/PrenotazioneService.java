package com.example.giardino.service;

import com.example.giardino.dto.PrenotazioneRequestDto;
import com.example.giardino.model.Prenotazione;
import com.example.giardino.repository.ClienteRepository;
import com.example.giardino.repository.ParrucchiereRepository;
import com.example.giardino.repository.PrenotazioneRepository;
import com.example.giardino.repository.ServizioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ParrucchiereRepository parrucchiereRepository;

    @Autowired
    private ServizioRepository servizioRepository;

    public List<Prenotazione> getAll() {
        return prenotazioneRepository.findAll();
    }

    public Prenotazione getById(Long id) {
        return prenotazioneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));
    }

    // Crea prenotazione a partire dal DTO
    public Prenotazione create(PrenotazioneRequestDto dto) {
        Prenotazione prenotazione = new Prenotazione();

        prenotazione.setCliente(clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente non trovato")));
        prenotazione.setParrucchiere(parrucchiereRepository.findById(dto.getParrucchiereId())
                .orElseThrow(() -> new RuntimeException("Parrucchiere non trovato")));
        prenotazione.setServizio(servizioRepository.findById(dto.getServizioId())
                .orElseThrow(() -> new RuntimeException("Servizio non trovato")));
        prenotazione.setData(dto.getData());
        prenotazione.setStato(dto.getStato());

        return prenotazioneRepository.save(prenotazione);
    }

    public Prenotazione update(Long id, PrenotazioneRequestDto dto) {
        Prenotazione existing = prenotazioneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));

        if (dto.getClienteId() != null) {
            existing.setCliente(clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente non trovato")));
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

    public void delete(Long id) {
        prenotazioneRepository.deleteById(id);
    }

    public List<Prenotazione> getByClienteId(Long clienteId) {
        return prenotazioneRepository.findByClienteId(clienteId);
    }

    public List<Prenotazione> getByParrucchiereId(Long parrucchiereId) {
        return prenotazioneRepository.findByParrucchiereId(parrucchiereId);
    }
}
