package com.example.giardino.dto;

import com.example.giardino.enumeration.BookingStatus;
import lombok.Data;

import java.time.LocalDate;


@Data
public class BookingDto {
    private Long id;
    private LocalDate dataPrenotazione;
    private BookingStatus status;
    private Long utenteId;
    private Long eventoId;
}
