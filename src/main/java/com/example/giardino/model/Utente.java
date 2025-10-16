package com.example.giardino.model;

import com.example.giardino.enumeration.Role;
import jakarta.persistence.*;
import lombok.Data;

@Table(name = "utenti")
@Entity
@Data
public class Utente {
    @Id
    @GeneratedValue
    private Long id;

    private String nome;
    private String cognome;
    private String telefono;
    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.UTENTE;

}


