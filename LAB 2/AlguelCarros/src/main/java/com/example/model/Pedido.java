package com.example.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
@Table(name = "pedidos_aluguel")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "cliente_id", nullable = false)
    private int clienteId;

    @Column(nullable = false)
    private String status;

    @Column(name = "data_pedido", nullable = false)
    private Date dataCriacao;

    @Column(name = "data_inicio", nullable = false)
    private Date dataAprovacao;

    @Column(name = "data_fim", nullable = false)
    private LocalDateTime dataFim;
    
    @Column(name = "automovel_id", nullable = false)
    public int automovelId;
    @Column(name = "valor_diaria", nullable = false)
    private double valorDiaria;


    
    public Pedido(int id, int clienteId, int automovelId, Date dataInicio, Date dataFim, String status, double valorDiaria) {
        this.id = id;
        this.clienteId = clienteId;
        this.automovelId = automovelId;
        this.dataCriacao = dataInicio;
        this.dataAprovacao = dataFim;
        this.status = status;
        this.valorDiaria = valorDiaria;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(Date dataCriacao) { this.dataCriacao = dataCriacao; }
    public Date getDataAprovacao() { return dataAprovacao; }
    public void setDataAprovacao(Date dataAprovacao) { this.dataAprovacao = dataAprovacao; }
    public Object getValorDiaria() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getValorDiaria'");
    }
    public Object getAutomovelId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAutomovelId'");
    }
}