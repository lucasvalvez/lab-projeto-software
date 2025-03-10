package com.example.JWT_RestAPI.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.JWT_RestAPI.model.Disciplina;

@Repository
public class DisciplinaRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Disciplina save(Disciplina disciplina) {
        String sql = "INSERT INTO disciplinas (codigo, nome, creditos, id_professor, id_turma) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, disciplina.getCodigo(), disciplina.getNome(), 
                            disciplina.getCreditos(), disciplina.getProfessor().getId(), disciplina.getTurma().getId());
        return disciplina;
    }

    public List<Disciplina> findAll() {
        String sql = "SELECT * FROM disciplinas";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Disciplina disciplina = new Disciplina();
            disciplina.setId(rs.getInt("id"));
            disciplina.setCodigo(rs.getString("codigo"));
            disciplina.setNome(rs.getString("nome"));
            disciplina.setCreditos(rs.getInt("creditos"));
            return disciplina;
        });
    }

    public List<Disciplina> findByUsuario(Long usuarioId) {
        String sql = "SELECT * FROM disciplinas WHERE id_professor = ?";
        return jdbcTemplate.query(sql, new Object[]{usuarioId}, (rs, rowNum) -> {
            Disciplina disciplina = new Disciplina();
            disciplina.setId(rs.getInt("id"));
            disciplina.setCodigo(rs.getString("codigo"));
            disciplina.setNome(rs.getString("nome"));
            disciplina.setCreditos(rs.getInt("creditos"));
            return disciplina;
        });
    }
}
