package com.example.giardino.dto;

import com.example.giardino.enumeration.Role;
import lombok.Data;

@Data
public class UtenteRequestDto {
    private String email;
    private String password;
    private Role role; // UTENTE o ADMIN
    private String nome;      // dati cliente
    private String cognome;
    private String telefono;
    private String username;
}
