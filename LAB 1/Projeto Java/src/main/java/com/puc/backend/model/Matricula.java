package com.puc.backend.model;

import java.math.BigDecimal;

public class Matricula {
    private Integer id;
    private Integer alunoId;
    private Integer disciplinaId;
    private BigDecimal nota;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getAlunoId() { return alunoId; }
    public void setAlunoId(Integer alunoId) { this.alunoId = alunoId; }
    public Integer getDisciplinaId() { return disciplinaId; }
    public void setDisciplinaId(Integer disciplinaId) { this.disciplinaId = disciplinaId; }
    public BigDecimal getNota() { return nota; }
    public void setNota(BigDecimal nota) { this.nota = nota; }
}