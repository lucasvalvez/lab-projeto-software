package com.puc.backend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.puc.backend.model.Usuario;
import com.puc.backend.model.requests.LoginRequest;
import com.puc.backend.model.requests.RegisterRequest;
import com.puc.backend.service.AuthService;
import com.puc.backend.service.UsuarioService;

@RestController
@CrossOrigin(origins = { "http://localhost:8080", "http://127.0.0.1:5500" })
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login") 
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Usuario usuario = usuarioService.findByEmail(request.getEmail());
            if (usuario == null) {
                return ResponseEntity.status(400).body(Map.of(
                    "error", "Falha no login",
                    "message", "Usuário não encontrado"
                ));
            }

            if (!authService.validatePassword(request.getSenha(), usuario.getSenha())) {
                return ResponseEntity.status(400).body(Map.of(
                    "error", "Falha no login", 
                    "message", "Senha incorreta"
                ));
            }

            String token = authService.generateToken(usuario.getEmail());
            return ResponseEntity.ok(Map.of(
                "token", token,
                "tipo", usuario.getTipo(),
                "nome", usuario.getNome()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "error", "Erro interno",
                "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            if (request.getNome() == null || request.getNome().trim().isEmpty()) {
                return ResponseEntity.status(400).body(Map.of(
                    "error", "Falha no registro",
                    "message", "Nome é obrigatório"
                ));
            }

            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                return ResponseEntity.status(400).body(Map.of(
                    "error", "Falha no registro",
                    "message", "Email é obrigatório"
                ));
            }

            if (request.getSenha() == null || request.getSenha().trim().isEmpty()) {
                return ResponseEntity.status(400).body(Map.of(
                    "error", "Falha no registro",
                    "message", "Senha é obrigatória"
                ));
            }

            if (request.getTipo() == null) {
                return ResponseEntity.status(400).body(Map.of(
                    "error", "Falha no registro",
                    "message", "Tipo de usuário é obrigatório"
                ));
            }

            Usuario usuario = new Usuario();
            usuario.setNome(request.getNome().trim());
            usuario.setEmail(request.getEmail().trim());
            usuario.setSenha(request.getSenha());
            usuario.setTipo(request.getTipo());

            usuarioService.save(usuario);
            return ResponseEntity.ok(Map.of("message", "Usuário registrado com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of(
                "error", "Falha no registro",
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "error", "Erro interno",
                "message", e.getMessage()
            ));
        }
    }
}