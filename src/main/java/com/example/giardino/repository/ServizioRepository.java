package com.example.giardino.repository;

import com.example.giardino.model.Prenotazione;
import com.example.giardino.model.Servizio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServizioRepository extends JpaRepository<Servizio, Long> {

}
