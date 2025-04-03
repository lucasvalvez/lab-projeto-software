package com.example.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.model.Pedido;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class PedidoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Pedido> pedidoMapper = (rs, rowNum) -> {
        return new Pedido(
            rs.getInt("id"),
            rs.getInt("cliente_id"),
            rs.getInt("automovel_id"),
            rs.getDate("data_inicio"),
            rs.getDate("data_fim"),
            rs.getString("status"),
            rs.getDouble("valor_diaria")
        );
    };

    public Pedido save(Pedido pedido) {
        String sql = pedido.getId() == 0 ?
            "INSERT INTO pedido (cliente_id, automovel_id, data_inicio, data_fim, status, valor_diaria) VALUES (?, ?, ?, ?, ?, ?)" :
            "UPDATE pedido SET cliente_id = ?, automovel_id = ?, data_inicio = ?, data_fim = ?, status = ?, valor_diaria = ? WHERE id = ?";

        if (pedido.getId() == 0) {
            jdbcTemplate.update(sql,
                pedido.getClienteId(),
                pedido.getAutomovelId(),
                new java.sql.Timestamp(pedido.getDataAprovacao().getTime()),
                new java.sql.Timestamp(pedido.getDataCriacao().getTime()),
                pedido.getStatus(),
                pedido.getValorDiaria()
            );
            // Recuperar o ID gerado automaticamente
            return findLastInserted();
        } else {
            jdbcTemplate.update(sql,
                pedido.getClienteId(),
                pedido.getAutomovelId(),
                new java.sql.Timestamp(pedido.getDataAprovacao().getTime()),
                new java.sql.Timestamp(pedido.getDataCriacao().getTime()),
                pedido.getStatus(),
                pedido.getValorDiaria(),
                pedido.getId()
            );
            return pedido;
        }
    }

    public Pedido findById(int id) {
        String sql = "SELECT * FROM pedido WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, pedidoMapper, id);
    }

    private Pedido findLastInserted() {
        String sql = "SELECT * FROM pedido ORDER BY id DESC LIMIT 1";
        return jdbcTemplate.queryForObject(sql, pedidoMapper);
    }

    public void delete(int id) {
        String sql = "DELETE FROM pedido WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    public List<Pedido> findAll() {
        String sql = "SELECT * FROM pedido";
        return jdbcTemplate.query(sql, pedidoMapper);
    }
    public List<Pedido> findByClienteId(int clienteId) {
        String sql = "SELECT * FROM pedido WHERE cliente_id = ?";
        return jdbcTemplate.query(sql, pedidoMapper, clienteId);
    }
}