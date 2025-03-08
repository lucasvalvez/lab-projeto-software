package com.example.JWT_RestAPI.repository;

import com.example.JWT_RestAPI.model.Disciplina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DisciplinaRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Disciplina save(Disciplina disciplina) {
        String sql = "INSERT INTO disciplinas (codigo, nome, creditos, professor_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, disciplina.getCodigo(), disciplina.getNome(), 
                          disciplina.getCreditos(), disciplina.getProfessor().getId());
        return disciplina;
    }

    public List<Disciplina> findAll() {
        String sql = "SELECT * FROM disciplinas";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Disciplina disciplina = new Disciplina();
            disciplina.setCodigo(rs.getString("codigo"));
            disciplina.setNome(rs.getString("nome"));
            disciplina.setCreditos(rs.getInt("creditos"));
            return disciplina;
        });
    }
}