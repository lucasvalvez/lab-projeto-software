package com.example.JWT_RestAPI.model;

import java.util.List;

public class Disciplina {
    private Long id;
    private String codigo;
    private String nome;
    private int creditos;
    private Professor professor;
    private List<Aluno> alunos;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getCreditos() { return creditos; }
    public void setCreditos(int creditos) { this.creditos = creditos; }
    public Professor getProfessor() { return professor; }
    public void setProfessor(Professor professor) { this.professor = professor; }
    public List<Aluno> getAlunos() { return alunos; }
    public void setAlunos(List<Aluno> alunos) { this.alunos = alunos; }

    public boolean adicionarAluno(Aluno aluno) {
        return alunos.size() < 30 ? alunos.add(aluno) : false;
    }

    public boolean removerAluno(Aluno aluno) {
        return alunos.remove(aluno);
    }
}