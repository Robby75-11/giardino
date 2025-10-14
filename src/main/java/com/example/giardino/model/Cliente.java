package com.example.giardino.model;

import com.example.giardino.enumeration.Role;
import jakarta.persistence.*;
import lombok.Data;

@Table(name = "clienti")
@Entity
@Data
public class Cliente {
    @Id
    @GeneratedValue
    private Long id;

    private String nome;
    private String cognome;
    private String email;
    private String telefono;
    private String password;
    private String username;
    @Enumerated(EnumType.STRING)
    private Role role; // CLIENTE o AMMINISTRATORE
}


