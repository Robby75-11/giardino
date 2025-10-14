package com.example.giardino.dto;

import lombok.Data;

@Data
public class ServizioRequestDto {
    private String nome;       // Taglio, Barba, Shampoo
    private Double prezzo;
    private Integer durata;    // in minuti
}
