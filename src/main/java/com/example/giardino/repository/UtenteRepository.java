package com.example.giardino.repository;


import com.example.giardino.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByEmail(String email);
    Optional<Utente> findByUsername(String username);

}
