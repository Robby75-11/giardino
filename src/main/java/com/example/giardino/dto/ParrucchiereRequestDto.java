package com.example.giardino.dto;

import lombok.Data;

@Data
public class ParrucchiereRequestDto {
    private String nome;
    private String cognome;
    private String specialita; // Taglio, Barba, Shampoo
}
