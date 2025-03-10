package com.example.JWT_RestAPI.controller;

import com.example.JWT_RestAPI.model.Usuario;
import com.example.JWT_RestAPI.service.UsuarioService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080"})
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{email}")
    public ResponseEntity<?> buscarPorEmail(@PathVariable String email) {
        try {
            Usuario usuario = usuarioService.findByEmail(email);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/professores")
    public ResponseEntity<?> getProfessores() {
        try {
            List<Usuario> professores = usuarioService.getProfessores();
            return ResponseEntity.ok(professores);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}