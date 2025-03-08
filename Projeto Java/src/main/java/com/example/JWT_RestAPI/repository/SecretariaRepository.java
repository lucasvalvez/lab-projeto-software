package com.example.JWT_RestAPI.repository;

import com.example.JWT_RestAPI.model.Secretaria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SecretariaRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Secretaria save(Secretaria secretaria) {
        String sql = "INSERT INTO secretarias (usuario_id, setor) VALUES (?, ?)";
        jdbcTemplate.update(sql, secretaria.getUsuario().getId(), secretaria.getSetor());
        return secretaria;
    }
}