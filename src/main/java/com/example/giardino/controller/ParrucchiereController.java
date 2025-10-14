package com.example.giardino.controller;

import com.example.giardino.dto.ParrucchiereRequestDto;
import com.example.giardino.model.Parrucchiere;
import com.example.giardino.service.ParrucchiereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parrucchieri")
    public class ParrucchiereController {

        @Autowired
        private ParrucchiereService parrucchiereService;

        @GetMapping
        public List<Parrucchiere> getAll() {
            return parrucchiereService.getAll();
        }

        @GetMapping("/{id}")
        public Parrucchiere getById(@PathVariable Long id) {
            return parrucchiereService.getById(id);
        }

        @PostMapping
        public Parrucchiere create(@RequestBody ParrucchiereRequestDto dto) {
            return parrucchiereService.createParrucchiere(dto);
        }

        @PutMapping("/{id}")
        public Parrucchiere update(@PathVariable Long id, @RequestBody ParrucchiereRequestDto dto) {
            return parrucchiereService.updateParrucchiere(id, dto);
        }

        @DeleteMapping("/{id}")
        public void delete(@PathVariable Long id) {
            parrucchiereService.deleteParrucchiere(id);
        }
}
