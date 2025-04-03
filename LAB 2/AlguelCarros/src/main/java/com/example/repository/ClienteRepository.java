package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.model.Cliente;

import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Cliente> clienteMapper = (rs, rowNum) -> {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id"));
        cliente.setNome(rs.getString("nome"));
        cliente.setEmail(rs.getString("email"));
        cliente.setTelefone(rs.getString("telefone"));
        return cliente;
    };

    public Cliente save(Cliente cliente) {
        String sql = cliente.getId() == 0 ?
            "INSERT INTO cliente (nome, email, telefone) VALUES (?, ?, ?)" :
            "UPDATE cliente SET nome = ?, email = ?, telefone = ? WHERE id = ?";

        if (cliente.getId() == 0) {
            jdbcTemplate.update(sql, cliente.getNome(), cliente.getEmail(), cliente.getTelefone());
            return findByEmail(cliente.getEmail());
        } else {
            jdbcTemplate.update(sql, cliente.getNome(), cliente.getEmail(), cliente.getTelefone(), cliente.getId());
            return cliente;
        }
    }

    public Optional<Cliente> findById(int id) {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, clienteMapper, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Cliente findByEmail(String email) {
        String sql = "SELECT * FROM cliente WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, clienteMapper, email);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Cliente> findAll() {
        return jdbcTemplate.query("SELECT * FROM cliente", clienteMapper);
    }

    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM cliente WHERE id = ?", id);
    }
}
