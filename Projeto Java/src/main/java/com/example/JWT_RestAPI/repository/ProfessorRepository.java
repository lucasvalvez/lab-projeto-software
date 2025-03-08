package com.example.JWT_RestAPI.repository;

import com.example.JWT_RestAPI.model.Professor;
import com.example.JWT_RestAPI.model.Aluno;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProfessorRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Professor save(Professor professor) {
        String sql = "INSERT INTO professores (usuario_id) VALUES (?)";
        jdbcTemplate.update(sql, professor.getUsuario().getId());
        return professor;
    }

    public List<Aluno> getAlunosMatriculados(Long disciplinaId) {
        String sql = """
            SELECT a.* FROM alunos a 
            JOIN aluno_disciplina ad ON a.id = ad.aluno_id 
            WHERE ad.disciplina_id = ?
        """;
        return jdbcTemplate.query(sql, new Object[]{disciplinaId}, 
            (rs, rowNum) -> {
                Aluno aluno = new Aluno();
                aluno.setMatricula(rs.getString("matricula"));
                return aluno;
            });
    }
}