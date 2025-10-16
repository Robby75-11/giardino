package com.example.giardino.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrenotazioneRequestDto {
    private Long utenteId;
    private Long parrucchiereId;
    private Long servizioId;
    private LocalDateTime data;
    private String stato; // IN_ATTESA, CONFERMATA, CANCELLATA
}
