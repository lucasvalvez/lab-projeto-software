package com.example.JWT_RestAPI.controller;

import com.example.JWT_RestAPI.model.LoginRequest;
import com.example.JWT_RestAPI.model.RegisterRequest;
import com.example.JWT_RestAPI.model.Usuario;
import com.example.JWT_RestAPI.service.AuthService;
import com.example.JWT_RestAPI.service.UsuarioService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
                return ResponseEntity.status(401).body("Usuario nao encontrado");
            }

            if (!authService.validatePassword(request.getPassword(), usuario.getSenha())) {
                return ResponseEntity.status(401).body("Senha incorreta");
            }

            String token = authService.generateToken(usuario.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("role", usuario.getRole());
            response.put("username", usuario.getNome());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Authentication failed");
            error.put("message", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            Usuario usuario = new Usuario();
            usuario.setNome(request.getName());
            usuario.setEmail(request.getEmail());
            usuario.setSenha(request.getPassword());
            usuario.setRole(request.getRole());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully");
            usuarioService.save(usuario);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Registration failed");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    // No login, capturamos o Username via corpo da requisição (LoginRequest Body)
    // Em seguida, geramos um token JWT

    @GetMapping("/auth/{token}")
    public Usuario extractUsername(@PathVariable String token) {
        Usuario usuario = authService.extractUserData(token);
        return usuario;
    }
    // No extractUsername, capturamos o token via URL apenas por praticidade
    // (poderia ser via @RequestBody também)
    // Em seguida, extraímos o username deste token

    // Qualquer um irá acessar
    @GetMapping("/user")
    public String getUser(Authentication authentication) {
        return "User: " + authentication.getName();
    }

    // Somente o admin irá acessar
    @Secured("ADMIN")
    @GetMapping("/admin")
    public String onlyAdmin(Authentication authentication) {
        return "Admin: " + authentication.getName();
    }
}
