package com.example.model;

import java.util.Date;

public class Contrato {
    private int id;
    private int pedidoId;
    private int bancoId;
    private double valorTotal;
    private double taxaJuros;
    private int prazoMeses;
    private String status;
    private Date dataContrato;

    // Construtor
    public Contrato(int id, int pedidoId, int bancoId, double valorTotal, double taxaJuros, int prazoMeses, String status, Date dataContrato) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.bancoId = bancoId;
        this.valorTotal = valorTotal;
        this.taxaJuros = taxaJuros;
        this.prazoMeses = prazoMeses;
        this.status = status;
        this.dataContrato = dataContrato;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    public int getBancoId() {
        return bancoId;
    }

    public void setBancoId(int bancoId) {
        this.bancoId = bancoId;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(double taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    public int getPrazoMeses() {
        return prazoMeses;
    }

    public void setPrazoMeses(int prazoMeses) {
        this.prazoMeses = prazoMeses;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDataContrato() {
        return dataContrato;
    }

    public void setDataContrato(Date dataContrato) {
        this.dataContrato = dataContrato;
    }
}