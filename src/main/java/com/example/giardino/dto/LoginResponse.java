package com.example.giardino.dto;

import com.example.giardino.enumeration.Role;
import com.example.giardino.model.Utente;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Utente utente;
    private String email;
    private Role role;

}
