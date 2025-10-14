package com.example.giardino.dto;

import lombok.Data;

@Data
public class ClienteRequestDto {
    private Long id;
    private String nome;
    private String cognome;
    private String telefono;
    private String email;
    private String username;
    private String password;
}
