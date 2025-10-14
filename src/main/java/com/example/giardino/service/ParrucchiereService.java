package com.example.giardino.service;

import com.example.giardino.dto.ParrucchiereRequestDto;
import com.example.giardino.model.Parrucchiere;
import com.example.giardino.repository.ParrucchiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParrucchiereService {
    @Autowired
    private ParrucchiereRepository parrucchiereRepository;

    // Crea parrucchiere
    public Parrucchiere createParrucchiere(ParrucchiereRequestDto dto) {
        Parrucchiere p = new Parrucchiere();
        p.setNome(dto.getNome());
        p.setCognome(dto.getCognome());
        p.setSpecialita(dto.getSpecialita());
        return parrucchiereRepository.save(p);
    }

    // Lista tutti i parrucchieri
    public List<Parrucchiere> getAll() {
        return parrucchiereRepository.findAll();
    }

    // Recupera parrucchiere per ID
    public Parrucchiere getById(Long id) {
        return parrucchiereRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parrucchiere non trovato"));
    }

    // Aggiorna parrucchiere
    public Parrucchiere updateParrucchiere(Long id, ParrucchiereRequestDto dto) {
        Parrucchiere p = getById(id);
        p.setNome(dto.getNome());
        p.setCognome(dto.getCognome());
        p.setSpecialita(dto.getSpecialita());
        return parrucchiereRepository.save(p);
    }

    // Elimina parrucchiere
    public void deleteParrucchiere(Long id) {
        parrucchiereRepository.deleteById(id);
    }
}
