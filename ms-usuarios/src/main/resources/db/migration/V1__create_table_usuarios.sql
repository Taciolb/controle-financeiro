CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE usuarios (
    id               UUID          DEFAULT gen_random_uuid() NOT NULL,
    nome             VARCHAR(100)  NOT NULL,
    email            VARCHAR(150)  NOT NULL,
    senha            VARCHAR(255)  NOT NULL,
    ativo            BOOLEAN       DEFAULT true NOT NULL,
    data_criacao     TIMESTAMP     DEFAULT now() NOT NULL,
    data_atualizacao TIMESTAMP     DEFAULT now() NOT NULL,

    CONSTRAINT pk_usuarios PRIMARY KEY (id),
    CONSTRAINT uq_usuarios_email UNIQUE (email)
);

CREATE INDEX idx_usuarios_email ON usuarios (email);

COMMENT ON TABLE  usuarios                  IS 'Tabela de usuarios do sistema';
COMMENT ON COLUMN usuarios.id               IS 'Identificador unico UUID';
COMMENT ON COLUMN usuarios.nome             IS 'Nome completo do usuario';
COMMENT ON COLUMN usuarios.email            IS 'Email de login - deve ser unico';
COMMENT ON COLUMN usuarios.senha            IS 'Senha armazenada com hash BCrypt';
COMMENT ON COLUMN usuarios.ativo            IS 'Soft delete - false = usuario desativado';
COMMENT ON COLUMN usuarios.data_criacao     IS 'Data de cadastro do usuario';
COMMENT ON COLUMN usuarios.data_atualizacao IS 'Data da ultima atualizacao do registro';