-- Tabela de Usuários (clientes e agentes)
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    tipo_usuario VARCHAR(20) NOT NULL CHECK (tipo_usuario IN ('cliente', 'empresa', 'banco')),
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ativo BOOLEAN DEFAULT true
);

-- Tabela de Dados Pessoais dos Clientes
CREATE TABLE dados_pessoais (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER REFERENCES usuarios(id),
    cpf VARCHAR(11) UNIQUE NOT NULL,
    rg VARCHAR(20) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    endereco TEXT NOT NULL,
    profissao VARCHAR(100),
    CONSTRAINT fk_usuario_dados_pessoais FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Tabela de Empregadores e Rendimentos
CREATE TABLE empregadores (
    id SERIAL PRIMARY KEY,
    dados_pessoais_id INTEGER REFERENCES dados_pessoais(id),
    nome_empregador VARCHAR(100) NOT NULL,
    rendimento DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_dados_pessoais_empregadores FOREIGN KEY (dados_pessoais_id) REFERENCES dados_pessoais(id)
);

-- Tabela de Dados das Empresas
CREATE TABLE dados_empresas (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER REFERENCES usuarios(id),
    cnpj VARCHAR(14) UNIQUE NOT NULL,
    razao_social VARCHAR(100) NOT NULL,
    nome_fantasia VARCHAR(100),
    endereco TEXT NOT NULL,
    CONSTRAINT fk_usuario_dados_empresas FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Tabela de Dados dos Bancos
CREATE TABLE dados_bancos (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER REFERENCES usuarios(id),
    cnpj VARCHAR(14) UNIQUE NOT NULL,
    razao_social VARCHAR(100) NOT NULL,
    nome_fantasia VARCHAR(100),
    endereco TEXT NOT NULL,
    CONSTRAINT fk_usuario_dados_bancos FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Tabela de Automóveis
CREATE TABLE automoveis (
    id SERIAL PRIMARY KEY,
    matricula VARCHAR(50) UNIQUE NOT NULL,
    placa VARCHAR(7) UNIQUE NOT NULL,
    ano INTEGER NOT NULL,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    proprietario_tipo VARCHAR(20) NOT NULL CHECK (proprietario_tipo IN ('cliente', 'empresa', 'banco')),
    proprietario_id INTEGER NOT NULL,
    disponivel BOOLEAN DEFAULT true
);

-- Tabela de Pedidos de Aluguel
CREATE TABLE pedidos_aluguel (
    id SERIAL PRIMARY KEY,
    cliente_id INTEGER REFERENCES usuarios(id),
    automovel_id INTEGER REFERENCES automoveis(id),
    data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('pendente', 'aprovado', 'rejeitado', 'cancelado', 'concluido')),
    valor_diaria DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_cliente_pedidos FOREIGN KEY (cliente_id) REFERENCES usuarios(id),
    CONSTRAINT fk_automovel_pedidos FOREIGN KEY (automovel_id) REFERENCES automoveis(id)
);

-- Tabela de Contratos de Crédito
CREATE TABLE contratos_credito (
    id SERIAL PRIMARY KEY,
    pedido_id INTEGER REFERENCES pedidos_aluguel(id),
    banco_id INTEGER REFERENCES usuarios(id),
    valor_total DECIMAL(10,2) NOT NULL,
    taxa_juros DECIMAL(5,2) NOT NULL,
    prazo_meses INTEGER NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('pendente', 'aprovado', 'rejeitado', 'cancelado')),
    data_contrato TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_pedido_contratos FOREIGN KEY (pedido_id) REFERENCES pedidos_aluguel(id),
    CONSTRAINT fk_banco_contratos FOREIGN KEY (banco_id) REFERENCES usuarios(id)
);

-- Tabela de Avaliações de Pedidos
CREATE TABLE avaliacoes_pedidos (
    id SERIAL PRIMARY KEY,
    pedido_id INTEGER REFERENCES pedidos_aluguel(id),
    agente_id INTEGER REFERENCES usuarios(id),
    data_avaliacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    parecer TEXT NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('aprovado', 'rejeitado')),
    CONSTRAINT fk_pedido_avaliacoes FOREIGN KEY (pedido_id) REFERENCES pedidos_aluguel(id),
    CONSTRAINT fk_agente_avaliacoes FOREIGN KEY (agente_id) REFERENCES usuarios(id)
);

-- Índices para melhor performance
CREATE INDEX idx_pedidos_status ON pedidos_aluguel(status);
CREATE INDEX idx_automoveis_disponivel ON automoveis(disponivel);
CREATE INDEX idx_usuarios_tipo ON usuarios(tipo_usuario);
CREATE INDEX idx_contratos_status ON contratos_credito(status);