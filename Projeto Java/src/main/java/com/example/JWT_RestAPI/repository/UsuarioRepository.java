package com.example.JWT_RestAPI.repository;

import com.example.JWT_RestAPI.enums.RoleEnum;
import com.example.JWT_RestAPI.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Usuario save(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, email, senha, role) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, usuario.getNome(), usuario.getEmail(), 
                          usuario.getSenha(), usuario.getRole().toString());
        return usuario;
    }

    public Usuario findByEmail(String email) {
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{email}, 
            (rs, rowNum) -> {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getLong("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setRole(RoleEnum.valueOf(rs.getString("role")));
                return usuario;
            });
    }
}