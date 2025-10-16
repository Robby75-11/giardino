package com.example.giardino.service;

import com.example.giardino.model.Utente;
import com.example.giardino.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Trova il cliente tramite la relazione User.email
      Utente utente = utenteRepository.findByEmail(email)
               .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato con email: " + email));



        return org.springframework.security.core.userdetails.User
                .withUsername(utente.getEmail())
                .password(utente.getPassword())
                .roles(utente.getRole().name())
                .build();
    }
}


