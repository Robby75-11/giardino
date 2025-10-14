package com.example.giardino.service;

import com.example.giardino.dto.ClienteRequestDto;
import com.example.giardino.model.Cliente;
import com.example.giardino.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 🔹 Salva un cliente (usato internamente)
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // 🔹 Crea un nuovo cliente dal DTO
    public Cliente createCliente(ClienteRequestDto dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setCognome(dto.getCognome());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setUsername(dto.getUsername());
        // se vuoi impostare una password di default, o da DTO, codifica qui
        if (dto.getPassword() != null) {
            cliente.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        return clienteRepository.save(cliente);
    }

    // 🔹 Aggiorna cliente esistente
    public Cliente updateCliente(Long id, ClienteRequestDto dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente non trovato"));

        cliente.setNome(dto.getNome());
        cliente.setCognome(dto.getCognome());
        cliente.setTelefono(dto.getTelefono());
        cliente.setEmail(dto.getEmail());
        cliente.setUsername(dto.getUsername());

        // se vuoi aggiornare la password
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            cliente.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return clienteRepository.save(cliente);
    }

    // 🔹 Trova cliente per email
    public Optional<Cliente> findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    // 🔹 Trova cliente per username
    public Optional<Cliente> findByUsername(String username) {
        return clienteRepository.findByUsername(username);
    }

    // 🔹 Tutti i clienti
    public List<Cliente> getAllClienti() {
        return clienteRepository.findAll();
    }

    // 🔹 Cliente per ID
    public Cliente getClienteById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente non trovato"));
    }

    // 🔹 Elimina cliente
    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
