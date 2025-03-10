package com.example.JWT_RestAPI.model;

import java.util.List;

public class Disciplina {
    private int id;
    private String codigo;
    private String nome;
    private int creditos;
    private Usuario professor;
    private Turma turma; // Relacionamento com Turma
    private List<Aluno> alunos;

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public Usuario getProfessor() {
        return professor;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    // Métodos para adicionar e remover alunos
    public boolean adicionarAluno(Aluno aluno) {
        if (alunos.size() < turma.getMaxAlunos()) {  // Verifica se a turma tem espaço
            return alunos.add(aluno);
        }
        return false;  // Retorna falso se a turma estiver cheia
    }

    public boolean removerAluno(Aluno aluno) {
        return alunos.remove(aluno);  // Remove o aluno da lista
    }

    public void setProfessor(Usuario usuario) {
        this.professor = usuario;
    }
}
