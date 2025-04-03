package com.example.model;

import java.util.List;

public class Sistema {
    private List<Pedido> pedidos;
    private List<Contrato> contratos;
    private List<Veiculo> veiculos;

    // Métodos para gerenciar o sistema
    public void adicionarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public void adicionarContrato(Contrato contrato) {
        contratos.add(contrato);
    }

    public void adicionarVeiculo(Veiculo veiculo) {
        veiculos.add(veiculo);
    }

    // Getters e Setters
    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }
}
