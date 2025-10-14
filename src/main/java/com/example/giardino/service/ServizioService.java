package com.example.giardino.service;

import com.example.giardino.model.Servizio;
import com.example.giardino.repository.ServizioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServizioService {

    @Autowired
    private ServizioRepository servizioRepository;

    // 🔹 Crea o aggiorna un servizio
    public Servizio save(Servizio servizio) {
        return servizioRepository.save(servizio);
    }

    // 🔹 Tutti i servizi
    public List<Servizio> getAllServizi() {
        return servizioRepository.findAll();
    }

    // 🔹 Servizio per ID
    public Servizio getServizioById(Long id) {
        return servizioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servizio non trovato"));
    }

    // 🔹 Elimina servizio
    public void deleteServizio(Long id) {
        servizioRepository.deleteById(id);
    }
}

