package com.example.giardino.repository;

import com.example.giardino.model.Cliente;
import com.example.giardino.model.Parrucchiere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParrucchiereRepository extends JpaRepository<Parrucchiere, Long> {


}
