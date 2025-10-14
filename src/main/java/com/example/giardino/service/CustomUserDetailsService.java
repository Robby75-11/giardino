package com.example.giardino.service;

import com.example.giardino.model.Cliente;
import com.example.giardino.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente= clienteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato con email: " + email));

        // Spring Security User (username, password, authorities)
        return org.springframework.security.core.userdetails.User
                .withUsername(cliente.getEmail())
                .password(cliente.getPassword())
                .roles(cliente.getRole().name())
                .build();
    }
}

