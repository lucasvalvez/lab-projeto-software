package com.example.JWT_RestAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.JWT_RestAPI.model.Disciplina;
import com.example.JWT_RestAPI.repository.DisciplinaRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Service
public class DisciplinaService {

    @Autowired
    DisciplinaRepository disciplinaRepository;

    @PostMapping
    public ResponseEntity<?> createDisciplina(@RequestBody Disciplina disciplina) {
        try {
            disciplinaRepository.save(disciplina);
            return ResponseEntity.status(HttpStatus.CREATED).body(disciplina);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar disciplina: " + e.getMessage());
        }
    }
}
