package com.example.JWT_RestAPI.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.JWT_RestAPI.model.Turma;

@Repository
public class TurmaRepository {
        @Autowired
    private JdbcTemplate jdbcTemplate;

    public Turma save(Turma turma) {
        String sql = "INSERT INTO public.turma (nome, max_alunos) VALUES (?, ?)";
        jdbcTemplate.update(sql, turma.getName(), turma.getMaxAlunos());
        return turma;
    }
}
