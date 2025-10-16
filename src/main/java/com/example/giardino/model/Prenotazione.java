package com.example.giardino.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Table(name ="prenotazioni" )
@Entity
@Data
public class Prenotazione {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    private Parrucchiere parrucchiere;

    @ManyToOne
    private Servizio servizio;

    private LocalDateTime data;
    private String stato; // es. IN_ATTESA, CONFERMATA, CANCELLATA

}
