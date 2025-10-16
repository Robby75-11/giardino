package com.example.giardino.dto;

import com.example.giardino.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UtenteResponseDto {
    private Long id;
    private String email;
    private Role role;
    private String nome;
    private String cognome;
    private String telefono;
    private String username;
}
