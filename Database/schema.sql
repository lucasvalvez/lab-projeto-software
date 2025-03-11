CREATE TYPE tipo_usuario AS ENUM ('ALUNO', 'PROFESSOR', 'SECRETARIA');

CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    tipo tipo_usuario NOT NULL
);

CREATE TABLE turma (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    ano INTEGER NOT NULL,
    CHECK (ano >= 2000)
);

CREATE TABLE disciplina (
    id SERIAL PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    carga_horaria INTEGER NOT NULL CHECK (carga_horaria > 0),
    id_professor INTEGER NOT NULL REFERENCES usuario(id),
    id_turma INTEGER NOT NULL REFERENCES turma(id)
);

CREATE TABLE matricula (
    id SERIAL PRIMARY KEY,
    id_aluno INTEGER NOT NULL REFERENCES usuario(id),
    id_disciplina INTEGER NOT NULL REFERENCES disciplina(id),
    nota NUMERIC(4,2) CHECK (nota >= 0 AND nota <= 10),
    UNIQUE(id_aluno, id_disciplina)
);

-- Instead of CHECK constraints, use triggers to validate user types
CREATE OR REPLACE FUNCTION check_professor_type()
RETURNS TRIGGER AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM usuario 
        WHERE id = NEW.id_professor AND tipo = 'PROFESSOR'
    ) THEN
        RAISE EXCEPTION 'Only professors can be assigned to disciplines';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER ensure_professor_type
BEFORE INSERT OR UPDATE ON disciplina
FOR EACH ROW EXECUTE FUNCTION check_professor_type();

CREATE OR REPLACE FUNCTION check_aluno_type()
RETURNS TRIGGER AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM usuario 
        WHERE id = NEW.id_aluno AND tipo = 'ALUNO'
    ) THEN
        RAISE EXCEPTION 'Only students can be enrolled in disciplines';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER ensure_aluno_type
BEFORE INSERT OR UPDATE ON matricula
FOR EACH ROW EXECUTE FUNCTION check_aluno_type();