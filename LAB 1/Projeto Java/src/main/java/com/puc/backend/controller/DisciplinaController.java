package com.puc.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.puc.backend.model.Disciplina;
import com.puc.backend.model.Usuario;
import com.puc.backend.service.AuthService;
import com.puc.backend.service.DisciplinaService;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = { "http://localhost:8080", "http://127.0.0.1:5500" })
@RequestMapping("/disciplina")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private AuthService authService;

    private boolean isSecretaria(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return false;
        }
        Usuario usuario = authService.extractUserData(token.substring(7));
        return usuario != null && usuario.getTipo() == Usuario.TipoUsuario.SECRETARIA;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestHeader("Authorization") String token,
            @RequestBody Disciplina disciplina) {
        try {
            if (!isSecretaria(token)) {
                return ResponseEntity.status(403).body(Map.of(
                        "error", "Acesso negado",
                        "message", "Apenas secretaria pode criar disciplinas"));
            }

            Disciplina saved = disciplinaService.save(disciplina);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Falha ao criar disciplina",
                    "message", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        return disciplinaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Disciplina>> listarTodas() {
        return ResponseEntity.ok(disciplinaService.findAll());
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<Disciplina>> listarPorProfessor(@PathVariable Integer professorId) {
        return ResponseEntity.ok(disciplinaService.findByProfessor(professorId));
    }

    @GetMapping("/turma/{turmaId}")
    public ResponseEntity<List<Disciplina>> listarPorTurma(@PathVariable Integer turmaId) {
        return ResponseEntity.ok(disciplinaService.findByTurma(turmaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@RequestHeader("Authorization") String token,
            @PathVariable Integer id,
            @RequestBody Disciplina disciplina) {
        try {
            if (!isSecretaria(token)) {
                return ResponseEntity.status(403).body(Map.of(
                        "error", "Acesso negado",
                        "message", "Apenas secretaria pode atualizar disciplinas"));
            }

            if (!disciplinaService.findById(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }

            disciplina.setId(id);
            Disciplina updated = disciplinaService.save(disciplina);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Falha ao atualizar disciplina",
                    "message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@RequestHeader("Authorization") String token,
            @PathVariable Integer id) {
        try {
            if (!isSecretaria(token)) {
                return ResponseEntity.status(403).body(Map.of(
                        "error", "Acesso negado",
                        "message", "Apenas secretaria pode deletar disciplinas"));
            }

            if (!disciplinaService.findById(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }

            disciplinaService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Falha ao deletar disciplina",
                    "message", e.getMessage()));
        }
    }
}