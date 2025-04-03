package com.example.service;

import com.example.model.Pedido;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final List<Pedido> pedidos = new ArrayList<>();

    public Pedido save(Pedido pedido) {
        if (pedido.getId() == 0) {
            pedido.setId(pedidos.size() + 1);
            Date datacriacao = (Date) Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
            pedido.setDataCriacao(datacriacao);
            pedido.setStatus("PENDENTE");
            pedidos.add(pedido);
        } else {
            pedidos.set(pedido.getId() - 1, pedido);
        }
        return pedido;
    }

    public Optional<Pedido> findById(int id) {
        return pedidos.stream().filter(p -> p.getId() == id).findFirst();
    }

    public List<Pedido> findAll() {
        return pedidos;
    }

    public void deleteById(int id) {
        pedidos.removeIf(p -> p.getId() == id);
    }

    public Pedido aprovarPedido(int id) {
        Pedido pedido = findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedido.setStatus("APROVADO");
        Date dataAprovacao = (Date) Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        pedido.setDataAprovacao(dataAprovacao); // Corrigido para usar LocalDateTime.now()
        return pedido;
    }
}