package com.example.giardino.model;

import com.example.giardino.enumeration.EventType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

@Table(name = "events")
@Entity
@Data
public class Event {
    @Id
    @GeneratedValue
    private Long id;

    private String titolo;
    @Enumerated(EnumType.STRING)
    private EventType tipo;

    private LocalDate dataEvento;

    private int numeroInvitati;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User organizzatore;
}
