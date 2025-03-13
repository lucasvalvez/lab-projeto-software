package com.puc.backend.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puc.backend.model.Email;
import com.puc.backend.model.Matricula;
import com.puc.backend.model.Usuario;
import com.puc.backend.service.EmailService;
import com.puc.backend.service.MatriculaService;
import com.puc.backend.service.UsuarioService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController {
    
    @Autowired 
    private MatriculaService matriculaService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UsuarioService usuarioService;


    @PostMapping
    public ResponseEntity<?> matricular(@RequestBody Matricula matricula) {
        try {
            Matricula saved = matriculaService.matricular(matricula.getAlunoId(), matricula.getDisciplinaId());
            Optional<Usuario> usuario = this.usuarioService.findById(matricula.getAlunoId());
            if (usuario.isPresent()) {
                Usuario user = usuario.get();
                Email email = new Email(user.getEmail(), "Matrícula realizada com sucesso", user.getNome());
                emailService.sendEmail(email);
            }
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
