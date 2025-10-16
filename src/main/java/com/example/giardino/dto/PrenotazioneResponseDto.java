package com.example.giardino.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrenotazioneResponseDto {
    private Long id;
    private String utenteNome;
    private String parrucchiereNome;
    private String servizioNome;
    private Double prezzoServizio;
    private LocalDateTime data;
    private String stato;
}
