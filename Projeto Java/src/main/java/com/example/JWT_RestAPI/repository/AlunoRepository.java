package com.example.JWT_RestAPI.repository;

import com.example.JWT_RestAPI.model.Aluno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AlunoRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Aluno save(Aluno aluno) {
        String sql = "INSERT INTO alunos (matricula, usuario_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, aluno.getMatricula(), aluno.getUsuario().getId());
        return aluno;
    }

    public void matricularDisciplina(Long alunoId, Long disciplinaId) {
        String sql = "INSERT INTO aluno_disciplina (aluno_id, disciplina_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, alunoId, disciplinaId);
    }
}