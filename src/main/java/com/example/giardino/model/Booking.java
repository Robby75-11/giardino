package com.example.giardino.model;

import com.example.giardino.enumeration.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Table(name = "bookings")
@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate dataPrenotazione;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User utente;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event evento;
}

