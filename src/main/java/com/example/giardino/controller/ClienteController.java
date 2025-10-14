package com.example.giardino.controller;

import com.example.giardino.dto.ClienteRequestDto;
import com.example.giardino.model.Cliente;
import com.example.giardino.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clienti")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // 🔹 Tutti i clienti (solo admin eventualmente con @PreAuthorize)
    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClienti() {
        List<Cliente> clienti = clienteService.getAllClienti();
        return ResponseEntity.ok(clienti);
    }

    // 🔹 Singolo cliente per ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Cliente cliente = clienteService.getClienteById(id);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        }
        return ResponseEntity.notFound().build();
    }

    // 🔹 Crea cliente usando DTO
    @PostMapping
    public ResponseEntity<Cliente> createCliente(@RequestBody ClienteRequestDto dto) {
        Cliente cliente = clienteService.createCliente(dto);
        return ResponseEntity.ok(cliente);
    }

    // 🔹 Aggiorna cliente usando DTO
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody ClienteRequestDto dto) {
        Cliente updated = clienteService.updateCliente(id, dto);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // 🔹 Elimina cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}
