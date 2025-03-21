package com.puc.backend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.puc.backend.model.Matricula;

import java.util.List;
import java.util.Optional;

@Repository
public class MatriculaRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Matricula> matriculaMapper = (rs, rowNum) -> {
        Matricula matricula = new Matricula();
        matricula.setId(rs.getInt("id"));
        matricula.setAlunoId(rs.getInt("id_aluno"));
        matricula.setDisciplinaId(rs.getInt("id_disciplina"));
        matricula.setNota(rs.getBigDecimal("nota"));
        return matricula;
    };

    public Matricula save(Matricula matricula) {
        String sql = matricula.getId() == null ?
            "INSERT INTO matricula (id_aluno, id_disciplina, nota) VALUES (?, ?, ?)" :
            "UPDATE matricula SET id_aluno = ?, id_disciplina = ?, nota = ? WHERE id = ?";

        if (matricula.getId() == null) {
            jdbcTemplate.update(sql,
                matricula.getAlunoId(),
                matricula.getDisciplinaId(),
                matricula.getNota()
            );
            return findByAlunoAndDisciplina(matricula.getAlunoId(), matricula.getDisciplinaId());
        } else {
            jdbcTemplate.update(sql,
                matricula.getAlunoId(),
                matricula.getDisciplinaId(),
                matricula.getNota(),
                matricula.getId()
            );
            return matricula;
        }
    }

    public Optional<Matricula> findById(Integer id) {
        String sql = "SELECT * FROM matricula WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, matriculaMapper, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Matricula findByAlunoAndDisciplina(Integer alunoId, Integer disciplinaId) {
        String sql = "SELECT * FROM matricula WHERE id_aluno = ? AND id_disciplina = ?";
        try {
            return jdbcTemplate.queryForObject(sql, matriculaMapper, alunoId, disciplinaId);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Matricula> findByAluno(Integer alunoId) {
        String sql = "SELECT * FROM matricula WHERE id_aluno = ?";
        return jdbcTemplate.query(sql, matriculaMapper, alunoId);
    }

    public List<Matricula> findByDisciplina(Integer disciplinaId) {
        String sql = "SELECT * FROM matricula WHERE id_disciplina = ?";
        return jdbcTemplate.query(sql, matriculaMapper, disciplinaId);
    }

    public boolean existsByAlunoAndDisciplina(Integer alunoId, Integer disciplinaId) {
        String sql = "SELECT COUNT(*) FROM matricula WHERE id_aluno = ? AND id_disciplina = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, alunoId, disciplinaId);
        return count > 0;
    }
}