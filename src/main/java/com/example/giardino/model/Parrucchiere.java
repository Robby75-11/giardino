package com.example.giardino.model;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "parrucchieri")
@Entity
@Data
public class Parrucchiere {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private String cognome;
    private String specialita; // es. Taglio, Barba, Colore
}
