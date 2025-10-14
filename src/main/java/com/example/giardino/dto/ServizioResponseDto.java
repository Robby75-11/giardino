package com.example.giardino.dto;

import lombok.Data;

@Data
public class ServizioResponseDto {
    private Long id;
    private String nome;
    private Double prezzo;
    private Integer durata;
}
