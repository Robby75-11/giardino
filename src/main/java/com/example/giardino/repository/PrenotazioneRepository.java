package com.example.giardino.repository;

import com.example.giardino.model.Parrucchiere;
import com.example.giardino.model.Prenotazione;
import com.example.giardino.model.Servizio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByClienteId(Long clienteId);
    List<Prenotazione> findByParrucchiereId(Long parrucchiereId);
}
