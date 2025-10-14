package com.example.giardino.model;

import com.example.giardino.enumeration.Role;
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
    private Cliente cliente;

    @ManyToOne
    private Parrucchiere parrucchiere;

    @ManyToOne
    private Servizio servizio;

    private LocalDateTime data;
    private String stato; // es. IN_ATTESA, CONFERMATA, CANCELLATA

}
