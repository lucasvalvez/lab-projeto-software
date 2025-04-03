package com.example.model;

public class RegistroPropriedade {
    private int id;
    private String tipoProprietario;
    private int proprietarioId;

    // Construtor
    public RegistroPropriedade(int id, String tipoProprietario, int proprietarioId) {
        this.id = id;
        this.tipoProprietario = tipoProprietario;
        this.proprietarioId = proprietarioId;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoProprietario() {
        return tipoProprietario;
    }

    public void setTipoProprietario(String tipoProprietario) {
        this.tipoProprietario = tipoProprietario;
    }

    public int getProprietarioId() {
        return proprietarioId;
    }

    public void setProprietarioId(int proprietarioId) {
        this.proprietarioId = proprietarioId;
    }
}