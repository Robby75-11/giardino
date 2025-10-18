package com.example.giardino.repository;

import com.example.giardino.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    // Tutte le prenotazioni con join fetch per evitare lazy loading
    @Query("SELECT p FROM Prenotazione p " +
            "JOIN FETCH p.utente " +
            "JOIN FETCH p.parrucchiere " +
            "JOIN FETCH p.servizio")
    List<Prenotazione> findAllWithDetails();

    // Prenotazioni di un utente specifico, con join fetch
    @Query("SELECT p FROM Prenotazione p " +
            "JOIN FETCH p.utente " +
            "JOIN FETCH p.parrucchiere " +
            "JOIN FETCH p.servizio " +
            "WHERE p.utente.id = :utenteId")
    List<Prenotazione> findByUtenteIdWithDetails(@Param("utenteId") Long utenteId);

    // Prenotazioni per un parrucchiere specifico, con join fetch
    @Query("SELECT p FROM Prenotazione p " +
            "JOIN FETCH p.utente " +
            "JOIN FETCH p.parrucchiere " +
            "JOIN FETCH p.servizio " +
            "WHERE p.parrucchiere.id = :parrucchiereId")
    List<Prenotazione> findByParrucchiereIdWithDetails(@Param("parrucchiereId") Long parrucchiereId);
}
