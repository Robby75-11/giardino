package com.example.giardino.dto;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String nome;
    public  String cognome;
    private String email;
    private String password;
    private String username;
    private String telefono;
}
