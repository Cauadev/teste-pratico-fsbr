CREATE TABLE solicitantes (
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  cpf VARCHAR(14) UNIQUE
);

CREATE TABLE vagas (
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  codigo_vaga VARCHAR(255) UNIQUE,
  valor_hora DECIMAL(10,2) NOT NULL,
  tipo_vaga VARCHAR(50) NOT NULL,
  status VARCHAR(50) NOT NULL
);

CREATE TABLE reservas (
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  dt_entrada TIMESTAMP NOT NULL,
  dt_saida TIMESTAMP,
  customer_id BIGINT NOT NULL,
  parking_spot_id BIGINT NOT NULL,
  FOREIGN KEY (customer_id) REFERENCES solicitantes(id),
  FOREIGN KEY (parking_spot_id) REFERENCES vagas(id)
);
