package com.puc.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puc.backend.model.Matricula;
import com.puc.backend.model.Usuario;
import com.puc.backend.repository.DisciplinaRepository;
import com.puc.backend.repository.MatriculaRepository;
import com.puc.backend.repository.UsuarioRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public Matricula matricular(int alunoId, int disciplinaId) {
        Usuario aluno = usuarioRepository.findById(alunoId)
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        if (aluno.getTipo() != Usuario.TipoUsuario.ALUNO) {
            throw new RuntimeException("Usuário não é um aluno");
        }

        if (!disciplinaRepository.existsById(disciplinaId)) {
            throw new RuntimeException("Disciplina não encontrada");
        }

        if (matriculaRepository.existsByAlunoAndDisciplina(alunoId, disciplinaId)) {
            throw new RuntimeException("Aluno já matriculado nesta disciplina");
        }

        Matricula matricula = new Matricula();
        matricula.setAlunoId(alunoId);
        matricula.setDisciplinaId(disciplinaId);
        
        return matriculaRepository.save(matricula);
    }

    public Matricula atribuirNota(int matriculaId, BigDecimal nota, int professorId) {
        Usuario professor = usuarioRepository.findById(professorId)
            .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        if (professor.getTipo() != Usuario.TipoUsuario.PROFESSOR) {
            throw new RuntimeException("Usuário não é um professor");
        }

        Matricula matricula = matriculaRepository.findById(matriculaId)
            .orElseThrow(() -> new RuntimeException("Matrícula não encontrada"));

        if (nota.compareTo(BigDecimal.ZERO) < 0 || nota.compareTo(new BigDecimal("10")) > 0) {
            throw new RuntimeException("Nota deve estar entre 0 e 10");
        }

        // Precisa criar o método pra ver se o professor que está tentando alterar é o professor da disciplina
        // if (!disciplinaRepository.isProfessorOfDisciplina(professorId, matricula.getDisciplinaId())) {
        //     throw new RuntimeException("Professor não está autorizado a atribuir nota para esta disciplina");
        // }

        matricula.setNota(nota);
        return matriculaRepository.save(matricula);
    }

    public List<Matricula> findByAluno(Integer alunoId) {
        return matriculaRepository.findByAluno(alunoId);
    }

    public List<Matricula> findByDisciplina(Integer disciplinaId) {
        return matriculaRepository.findByDisciplina(disciplinaId);
    }
}