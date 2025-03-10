package com.example.JWT_RestAPI.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.example.JWT_RestAPI.model.Turma;
import com.example.JWT_RestAPI.service.TurmaService;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = { "http://localhost:8080", "http://127.0.0.1:8080" })
public class TurmaController {

    @Autowired
    TurmaService turmaService;

    @PostMapping("/turma")
    public ResponseEntity<?> criarTurma(@RequestBody Turma request) {
        System.out.println("Criando turma");
        System.out.println(request.getName());
        System.out.println(request.getMaxAlunos());
        try {
            Turma turma = new Turma();
            turma.setName(request.getName());
            turma.setMaxAlunos(request.getMaxAlunos());

            turma = turmaService.save(turma);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Turma criada com sucesso");
            response.put("turma", turma);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            System.out.println(e.getMessage());
            error.put("error", "Falha ao criar turma");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
