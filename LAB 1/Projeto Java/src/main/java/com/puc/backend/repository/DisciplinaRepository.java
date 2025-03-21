package com.puc.backend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.puc.backend.model.Disciplina;
import com.puc.backend.model.Turma;
import com.puc.backend.model.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public class DisciplinaRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    private final RowMapper<Disciplina> disciplinaMapper = (rs, rowNum) -> {
        Disciplina disciplina = new Disciplina();
        disciplina.setId(rs.getInt("id"));
        disciplina.setCodigo(rs.getString("codigo"));
        disciplina.setNome(rs.getString("nome"));
        disciplina.setCargaHoraria(rs.getInt("carga_horaria"));
        
        Usuario professor = usuarioRepository.findById(rs.getInt("id_professor")).orElse(null);
        disciplina.setProfessorId(professor.getId());
        
        Turma turma = turmaRepository.findById(rs.getInt("id_turma")).orElse(null);
        disciplina.setTurmaId(turma.getId());
        
        return disciplina;
    };

    public Disciplina save(Disciplina disciplina) {
        String sql = disciplina.getId() == null ?
            "INSERT INTO disciplina (codigo, nome, carga_horaria, id_professor, id_turma) VALUES (?, ?, ?, ?, ?)" :
            "UPDATE disciplina SET codigo = ?, nome = ?, carga_horaria = ?, id_professor = ?, id_turma = ? WHERE id = ?";

        if (disciplina.getId() == null) {
            jdbcTemplate.update(sql,
                disciplina.getCodigo(),
                disciplina.getNome(),
                disciplina.getCargaHoraria(),
                disciplina.getProfessorId(),
                disciplina.getTurmaId()
            );
            return findByCodigo(disciplina.getCodigo());
        } else {
            jdbcTemplate.update(sql,
                disciplina.getCodigo(),
                disciplina.getNome(),
                disciplina.getCargaHoraria(),
                disciplina.getProfessorId(),
                disciplina.getTurmaId(),
                disciplina.getId()
            );
            return disciplina;
        }
    }

    public Optional<Disciplina> findById(int id) {
        String sql = "SELECT * FROM disciplina WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, disciplinaMapper, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Disciplina findByCodigo(String codigo) {
        String sql = "SELECT * FROM disciplina WHERE codigo = ?";
        try {
            return jdbcTemplate.queryForObject(sql, disciplinaMapper, codigo);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Disciplina> findAll() {
        return jdbcTemplate.query("SELECT * FROM disciplina", disciplinaMapper);
    }

    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM disciplina WHERE id = ?", id);
    }

    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM disciplina WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count > 0;
    }

    public boolean existsByCodigo(String codigo) {
        String sql = "SELECT COUNT(*) FROM disciplina WHERE codigo = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, codigo);
        return count > 0;
    }

    public List<Disciplina> findByProfessor(int professorId) {
        String sql = "SELECT * FROM disciplina WHERE id_professor = ?";
        return jdbcTemplate.query(sql, disciplinaMapper, professorId);
    }

    public List<Disciplina> findByTurma(int turmaId) {
        String sql = "SELECT * FROM disciplina WHERE id_turma = ?";
        return jdbcTemplate.query(sql, disciplinaMapper, turmaId);
    }
}