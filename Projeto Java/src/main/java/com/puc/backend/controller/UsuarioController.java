package com.puc.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.puc.backend.model.Usuario;
import com.puc.backend.model.Usuario.TipoUsuario;
import com.puc.backend.service.UsuarioService;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080"})
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    @Autowired 
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Usuario usuario) {
        try {
            Usuario saved = usuarioService.save(usuario);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        return usuarioService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> buscarPorEmail(@PathVariable String email) {
        Usuario usuario = usuarioService.findByEmail(email);
        return usuario != null ? 
            ResponseEntity.ok(usuario) : 
            ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Usuario>> listarPorTipo(@PathVariable TipoUsuario tipo) {
        return ResponseEntity.ok(usuarioService.findByTipo(tipo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        try {
            if (!usuarioService.findById(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }
            usuario.setId(id);
            Usuario updated = usuarioService.save(usuario);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        if (!usuarioService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/professores")
    public ResponseEntity<List<Usuario>> getProfessores() {
        return ResponseEntity.ok(usuarioService.getProfessores());
    }
}