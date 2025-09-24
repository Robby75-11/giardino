package com.example.giardino.dto;

import com.example.giardino.enumeration.Role;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private Role role;
}
