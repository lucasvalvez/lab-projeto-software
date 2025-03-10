package com.example.JWT_RestAPI.controller;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.JWT_RestAPI.model.Disciplina;
import com.example.JWT_RestAPI.model.Usuario;
import com.example.JWT_RestAPI.repository.DisciplinaRepository;
import com.example.JWT_RestAPI.service.AuthService;

import java.util.List;

@RestController
@RequestMapping("/disciplina")
@CrossOrigin(origins = { "http://localhost:8080", "http://127.0.0.1:5500" })
public class DisciplinaController {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private AuthService authService;

    // Endpoint GET para listar disciplinas
    @GetMapping("/token")
    public ResponseEntity<?> getDisciplinas(@PathVariable String token) {
        try {
            // Extrai o usuário do token
            Usuario usuario = authService.extractUserData(token);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
            }

            // Busca as disciplinas do professor
            List<Disciplina> disciplinas = disciplinaRepository.findByUsuario(usuario.getId());
            return ResponseEntity.ok(disciplinas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao carregar disciplinas: " + e.getMessage());
        }
    }

    // Endpoint POST para cadastrar uma nova disciplina
    @PostMapping
    public ResponseEntity<?> createDisciplina(@RequestBody Disciplina disciplina, @RequestParam String token) {
        try {
            // Verifica o token e extrai os dados do usuário
            Usuario usuario = authService.extractUserData(token);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
            }

            // Define o usuário (professor) na disciplina antes de salvar
            disciplina.setProfessor(usuario);
            disciplinaRepository.save(disciplina);

            return ResponseEntity.status(HttpStatus.CREATED).body(disciplina);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar disciplina: " + e.getMessage());
        }
    }
}
