package com.example.giardino.dto;

import com.example.giardino.model.Cliente;
import com.example.giardino.model.Prenotazione;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Cliente cliente;
}
