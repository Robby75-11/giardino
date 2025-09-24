package com.example.giardino.model;

import com.example.giardino.enumeration.Role;
import jakarta.persistence.*;
import lombok.Data;

@Table(name ="users" )
@Entity
@Data
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String nome;
    private String cognome;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

}
