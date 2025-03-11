package com.puc.backend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.puc.backend.model.Usuario;
import com.puc.backend.model.Usuario.TipoUsuario;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Usuario> usuarioMapper = (rs, rowNum) -> {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setTipo(TipoUsuario.valueOf(rs.getString("tipo")));
        return usuario;
    };

    public Usuario save(Usuario usuario) {
        String sql = usuario.getId() == null ?
            "INSERT INTO usuario (nome, email, senha, tipo) VALUES (?, ?, ?, ?::tipo_usuario)" :
            "UPDATE usuario SET nome = ?, email = ?, senha = ?, tipo = ?::tipo_usuario WHERE id = ?";

        if (usuario.getId() == null) {
            jdbcTemplate.update(sql,
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getTipo().toString()
            );
            return findByEmail(usuario.getEmail());
        } else {
            jdbcTemplate.update(sql,
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getTipo().toString(),
                usuario.getId()
            );
            return usuario;
        }
    }

    public Optional<Usuario> findById(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(sql, usuarioMapper, id)
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Usuario findByEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, usuarioMapper, email);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Usuario> findAll() {
        return jdbcTemplate.query("SELECT * FROM usuario", usuarioMapper);
    }

    public List<Usuario> findByTipo(TipoUsuario tipo) {
        String sql = "SELECT * FROM usuario WHERE tipo = ?::tipo_usuario";
        return jdbcTemplate.query(sql, usuarioMapper, tipo.toString());
    }

    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM usuario WHERE id = ?", id);
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE email = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count > 0;
    }
}