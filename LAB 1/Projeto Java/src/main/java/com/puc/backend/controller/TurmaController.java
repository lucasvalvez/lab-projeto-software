package com.puc.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.puc.backend.model.Turma;
import com.puc.backend.model.Usuario;
import com.puc.backend.service.AuthService;
import com.puc.backend.service.TurmaService;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = { "http://localhost:8080", "http://127.0.0.1:5500" })
@RequestMapping("/turma")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

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
            @RequestBody Turma turma) {
        try {
            if (!isSecretaria(token)) {
                return ResponseEntity.status(403).body(Map.of(
                        "error", "Acesso negado",
                        "message", "Apenas secretaria pode criar turmas"));
            }

            if (turma.getNome() == null || turma.getNome().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "Dados inválidos",
                        "message", "Nome da turma é obrigatório"));
            }

            Turma saved = turmaService.save(turma);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Falha ao criar turma",
                    "message", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        return turmaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Turma>> listarTodas() {
        return ResponseEntity.ok(turmaService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@RequestHeader("Authorization") String token,
            @PathVariable Integer id,
            @RequestBody Turma turma) {
        try {
            if (!isSecretaria(token)) {
                return ResponseEntity.status(403).body(Map.of(
                        "error", "Acesso negado",
                        "message", "Apenas secretaria pode atualizar turmas"));
            }

            if (!turmaService.findById(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }

            turma.setId(id);
            Turma updated = turmaService.save(turma);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Falha ao atualizar turma",
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
                        "message", "Apenas secretaria pode deletar turmas"));
            }

            if (!turmaService.findById(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }

            turmaService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Falha ao deletar turma",
                    "message", e.getMessage()));
        }
    }
}