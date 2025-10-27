-- Criação das tabelas do PsychoManager

-- Tabela de pacientes
CREATE TABLE tb_paciente (
    id_paciente BIGSERIAL PRIMARY KEY,
    ds_nome VARCHAR(100) NOT NULL,
    ds_email VARCHAR(100),
    ds_profissao VARCHAR(100),
    ds_escolaridade VARCHAR(100),
    st_estadocivil CHAR(1),
    dt_nascimento DATE,
    ds_filhos VARCHAR(100),
    ds_endereco VARCHAR(100) NOT NULL,
    ds_bairro VARCHAR(100) NOT NULL,
    nr_cep VARCHAR(9) NOT NULL,
    ds_queixas VARCHAR(1000),
    ds_probsaude VARCHAR(200),
    ds_acompmedico VARCHAR(100),
    ds_remedios VARCHAR(100),
    st_bebe CHAR(1),
    st_fuma CHAR(1),
    st_drogas CHAR(1),
    st_insonia CHAR(1),
    ds_calmante VARCHAR(100),
    st_tratpsic CHAR(1),
    ds_resultado VARCHAR(1000),
    ds_observacao VARCHAR(1000),
    ds_ficha VARCHAR(200),
    st_catvalor CHAR(1)
);

-- Tabela de telefones
CREATE TABLE tb_fone (
    id_fone BIGSERIAL PRIMARY KEY,
    nr_fone VARCHAR(17) NOT NULL,
    tp_fone CHAR(1) NOT NULL,
    id_paciente BIGINT NOT NULL REFERENCES tb_paciente(id_paciente) ON DELETE CASCADE
);

-- Tabela de produtos/serviços
CREATE TABLE tb_produto (
    id_produto BIGSERIAL PRIMARY KEY,
    ds_nome VARCHAR(100) NOT NULL,
    vl_preco1 DECIMAL(10,2),
    vl_preco2 DECIMAL(10,2),
    vl_preco3 DECIMAL(10,2),
    vl_preco4 DECIMAL(10,2)
);

-- Tabela de agenda
CREATE TABLE tb_agenda (
    id_paciente BIGINT NOT NULL,
    dt_consulta TIMESTAMP NOT NULL,
    st_presenca BOOLEAN,
    id_produto BIGINT NOT NULL,
    ds_obs VARCHAR(100),
    dt_consulta_ate TIMESTAMP,
    vl_preco DECIMAL(10,2),
    st_pagamento INTEGER DEFAULT 0,
    PRIMARY KEY (id_paciente, dt_consulta),
    FOREIGN KEY (id_paciente) REFERENCES tb_paciente(id_paciente) ON DELETE CASCADE,
    FOREIGN KEY (id_produto) REFERENCES tb_produto(id_produto) ON DELETE RESTRICT
);

-- Tabela de senhas dos pacientes
CREATE TABLE tb_senha (
    id_paciente BIGINT PRIMARY KEY,
    ds_senha VARCHAR(8) NOT NULL,
    FOREIGN KEY (id_paciente) REFERENCES tb_paciente(id_paciente) ON DELETE CASCADE
);

-- Tabela de usuários do sistema
CREATE TABLE tb_aut_user (
    userid VARCHAR(10) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    ds_nome VARCHAR(100),
    id_lastview INTEGER DEFAULT 0,
    st_refreshauto BOOLEAN DEFAULT TRUE
);

-- Tabela de grupos de usuários
CREATE TABLE tb_aut_group (
    userid VARCHAR(10) NOT NULL,
    groupid VARCHAR(50) NOT NULL,
    PRIMARY KEY (userid, groupid),
    FOREIGN KEY (userid) REFERENCES tb_aut_user(userid) ON DELETE CASCADE
);

-- Índices para melhor performance
CREATE INDEX idx_paciente_nome ON tb_paciente(ds_nome);
CREATE INDEX idx_paciente_email ON tb_paciente(ds_email);
CREATE INDEX idx_agenda_data ON tb_agenda(dt_consulta);
CREATE INDEX idx_agenda_paciente ON tb_agenda(id_paciente);
CREATE INDEX idx_agenda_produto ON tb_agenda(id_produto);
CREATE INDEX idx_agenda_presenca ON tb_agenda(st_presenca);
CREATE INDEX idx_agenda_pagamento ON tb_agenda(st_pagamento);
CREATE INDEX idx_fone_paciente ON tb_fone(id_paciente);
CREATE INDEX idx_produto_nome ON tb_produto(ds_nome);

-- Comentários nas tabelas
COMMENT ON TABLE tb_paciente IS 'Tabela de pacientes do sistema';
COMMENT ON TABLE tb_fone IS 'Telefones dos pacientes';
COMMENT ON TABLE tb_produto IS 'Produtos/serviços oferecidos';
COMMENT ON TABLE tb_agenda IS 'Agendamentos de consultas';
COMMENT ON TABLE tb_senha IS 'Senhas de acesso dos pacientes';
COMMENT ON TABLE tb_aut_user IS 'Usuários do sistema';
COMMENT ON TABLE tb_aut_group IS 'Grupos de usuários';

-- Comentários nas colunas importantes
COMMENT ON COLUMN tb_agenda.st_pagamento IS '0=A Receber, 1=Pago, 2=Abonado, 3=Calote';
COMMENT ON COLUMN tb_fone.tp_fone IS 'C=Celular, R=Residencial, W=Comercial';
COMMENT ON COLUMN tb_paciente.st_estadocivil IS 'S=Solteiro, C=Casado, E=Separado, V=Viúvo, D=Divorciado';
COMMENT ON COLUMN tb_paciente.st_catvalor IS 'Categoria de valor para cálculo de preço (1-4)';
