package com.example.giardino.dto;

import com.example.giardino.enumeration.EventType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventDto {
    private Long id;
    private String titolo;
    private EventType tipo;
    private LocalDate dataEvento;
    private int numeroInvitati;
    private Long organizzatoreId;
}
