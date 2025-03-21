package com.puc.backend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.puc.backend.model.Turma;

import java.util.List;
import java.util.Optional;

@Repository
public class TurmaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Turma> turmaMapper = (rs, rowNum) -> {
        Turma turma = new Turma();
        turma.setId(rs.getInt("id"));
        turma.setNome(rs.getString("nome")); 
        turma.setAno(rs.getInt("ano"));
        return turma;
    };

    public Turma save(Turma turma) {
        String sql = turma.getId() == null ?
            "INSERT INTO turma (nome, ano) VALUES (?, ?)" :
            "UPDATE turma SET nome = ?, ano = ? WHERE id = ?";

        if (turma.getId() == null) {
            jdbcTemplate.update(sql, 
                turma.getNome(),
                turma.getAno()
            );
            return findByNome(turma.getNome());
        } else {
            jdbcTemplate.update(sql,
                turma.getNome(),
                turma.getAno(),
                turma.getId()
            );
            return turma;
        }
    }

    public Optional<Turma> findById(Integer id) {
        String sql = "SELECT * FROM turma WHERE id = ?";
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(sql, turmaMapper, id)
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Turma findByNome(String nome) {
        String sql = "SELECT * FROM turma WHERE nome = ?";
        try {
            return jdbcTemplate.queryForObject(sql, turmaMapper, nome);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Turma> findAll() {
        return jdbcTemplate.query("SELECT * FROM turma", turmaMapper);
    }

    public List<Turma> findByAno(int ano) {
        String sql = "SELECT * FROM turma WHERE ano = ?";
        return jdbcTemplate.query(sql, turmaMapper, ano);
    }

    public void deleteById(Integer id) {
        jdbcTemplate.update("DELETE FROM turma WHERE id = ?", id);
    }

    public boolean existsById(Integer id) {
        String sql = "SELECT COUNT(*) FROM turma WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count > 0;
    }
}