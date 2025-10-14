package com.example.giardino.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Table(name = "servizi")
@Entity
@Data
public class Servizio {
    @Id
    @GeneratedValue
    private Long id;
    private Double prezzo;
    private String nome; // Taglio, Barba, Shampoo
    private Integer durata;

   }

