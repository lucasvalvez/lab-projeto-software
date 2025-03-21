package com.puc.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puc.backend.model.Disciplina;
import com.puc.backend.model.Usuario;
import com.puc.backend.repository.DisciplinaRepository;
import com.puc.backend.repository.TurmaRepository;
import com.puc.backend.repository.UsuarioRepository;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    public Disciplina save(Disciplina disciplina) {
        validateDisciplina(disciplina);
        return disciplinaRepository.save(disciplina);
    }
    private void validateDisciplina(Disciplina disciplina) {
        if (disciplina.getCodigo() == null || disciplina.getCodigo().trim().isEmpty()) {
            throw new RuntimeException("Código da disciplina é obrigatório");
        }

        if (disciplina.getNome() == null || disciplina.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome da disciplina é obrigatório");
        }

        if (disciplina.getCargaHoraria() <= 0) {
            throw new RuntimeException("Carga horária deve ser maior que zero");
        }

        if (disciplina.getProfessorId() == null) {
            throw new RuntimeException("Professor é obrigatório");
        }

        // Validate professor exists and is a PROFESSOR
        Usuario professor = usuarioRepository.findById(disciplina.getProfessorId())
            .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        if (professor.getTipo() != Usuario.TipoUsuario.PROFESSOR) {
            throw new RuntimeException("Usuário selecionado não é um professor");
        }

        if (disciplina.getTurmaId() == null) {
            throw new RuntimeException("Turma é obrigatória");
        }

        // Validate turma exists
        if (!turmaRepository.existsById(disciplina.getTurmaId())) {
            throw new RuntimeException("Turma não encontrada");
        }

        if (disciplina.getId() == null && disciplinaRepository.existsByCodigo(disciplina.getCodigo())) {
            throw new RuntimeException("Já existe uma disciplina com este código");
        }
    }
    public Optional<Disciplina> findById(Integer id) {
        return disciplinaRepository.findById(id);
    }

    public List<Disciplina> findAll() {
        return disciplinaRepository.findAll();
    }

    public void deleteById(Integer id) {
        if (!disciplinaRepository.existsById(id)) {
            throw new RuntimeException("Disciplina não encontrada");
        }
        disciplinaRepository.deleteById(id);
    }

    public List<Disciplina> findByProfessor(Integer professorId) {
        return disciplinaRepository.findByProfessor(professorId);
    }

    public List<Disciplina> findByTurma(Integer turmaId) {
        return disciplinaRepository.findByTurma(turmaId);
    }
}