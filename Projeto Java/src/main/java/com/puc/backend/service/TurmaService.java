package com.puc.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puc.backend.model.Turma;
import com.puc.backend.repository.TurmaRepository;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    public Turma save(Turma turma) {
        validateTurma(turma);
        return turmaRepository.save(turma);
    }

    public Optional<Turma> findById(Integer id) {
        return turmaRepository.findById(id);
    }

    public List<Turma> findAll() {
        return turmaRepository.findAll();
    }

    public void deleteById(Integer id) {
        if (!turmaRepository.existsById(id)) {
            throw new RuntimeException("Turma não encontrada");
        }
        turmaRepository.deleteById(id);
    }

    private void validateTurma(Turma turma) {
        if (turma.getNome() == null || turma.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome da turma é obrigatório");
        }

        if (turma.getAno() < 2000) {
            throw new RuntimeException("Ano deve ser maior ou igual a 2000");
        }
    }

    public boolean existsById(Integer id) {
        return turmaRepository.existsById(id);
    }

    public List<Turma> findByAno(int ano) {
        return turmaRepository.findByAno(ano);
    }
}