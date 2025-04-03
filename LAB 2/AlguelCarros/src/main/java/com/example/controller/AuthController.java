package com.example.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Cliente;
import com.example.model.requests.LoginRequest;
import com.example.model.requests.RegisterRequest;
import com.example.service.AuthService;
import com.example.service.ClienteService;

@RestController
@CrossOrigin(origins = { "http://localhost:8080", "http://127.0.0.1:5500" })
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Cliente cliente = clienteService.findByEmail(request.getEmail());
            if (cliente == null) {
                return ResponseEntity.status(400).body(Map.of(
                    "error", "Falha no login",
                    "message", "Cliente não encontrado"
                ));
            }

            if (!authService.validatePassword(request.getSenha(), cliente.getTelefone())) {
                return ResponseEntity.status(400).body(Map.of(
                    "error", "Falha no login",
                    "message", "Senha incorreta"
                ));
            }

            String token = authService.generateToken(cliente.getEmail());
            return ResponseEntity.ok(Map.of(
                "token", token,
                "nome", cliente.getNome()
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

            if (request.getTelefone() == null || request.getTelefone().trim().isEmpty()) {
                return ResponseEntity.status(400).body(Map.of(
                    "error", "Falha no registro",
                    "message", "Telefone é obrigatório"
                ));
            }

            Cliente cliente = new Cliente();
            cliente.setNome(request.getNome().trim());
            cliente.setEmail(request.getEmail().trim());
            cliente.setTelefone(request.getTelefone());

            clienteService.save(cliente);
            return ResponseEntity.ok(Map.of("message", "Cliente registrado com sucesso"));
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