import java.util.List;

public class Disciplina {
    private String codigo;
    private String nome;
    private int creditos;
    private Professor professor;
    private List<Aluno> alunos;

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

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public boolean adicionarAluno(Aluno aluno) {
        return alunos.add(aluno);
    }

    public boolean removerAluno(Aluno aluno) {
        return alunos.remove(aluno);
    }

    public boolean verificarDisponibilidade() {
        return alunos.size() < 30;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Disciplina that = (Disciplina) obj;
        return codigo != null && codigo.equals(that.codigo);  
    }
}
