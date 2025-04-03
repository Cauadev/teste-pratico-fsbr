# README - Teste Prático FSB

## Instruções de Build, Execução e Testes

### 1. Requisitos

Antes de executar o projeto, verifique se os seguintes requisitos estão instalados:

- [JDK 17+](https://adoptopenjdk.net/)
- [Maven 3+](https://maven.apache.org/download.cgi)

### 2. Configuração do Banco de Dados

O projeto utiliza o banco de dados H2, que é um banco em memória utilizado para testes. Não é necessária nenhuma configuração adicional.

Para acessar o console web do H2, vá até:

```
http://localhost:3002/api/h2-console
```

Use a URL `jdbc:h2:mem:fsbr` para conectar.

**Credenciais do Banco H2:**

- **Usuário:** `admin`
- **Senha:** (vazio)

### 3. Compilando o Projeto

Para compilar o projeto, execute o seguinte comando no terminal:

```sh
mvn clean package
```

Isso gerará um arquivo `.jar` dentro da pasta `target/`.

### 4. Executando a Aplicação

Após compilar, execute a aplicação com o comando:

```sh
java -jar target/teste-pratico-fsb.jar
```

A aplicação iniciará na porta `3002`. Para testar, acesse:

```
http://localhost:3002/api
```

### 5. Rodando os Testes

O projeto possui testes automatizados. Para executá-los, use:

```sh
mvn test
```

Se quiser ver relatórios de cobertura de testes, use:

```sh
mvn verify
```

### 6. Endpoints Disponíveis

A aplicação expõe os seguintes endpoints:

## Reservas

### Criar uma reserva

```http
POST /reservations
```

**Body:**

```json
{
  "parkingSpotCode": "1",
  "customer": {
    "fullName": "Teste",
    "cpf": "99999999999"
  }
}
```

### Fechar uma reserva

```http
POST /reservations/{id}/close
```

## Vagas

### Criar vaga de estacionamento

```http
POST /parking/spots
```

**Body:**

```json
{
  "code": "1",
  "hourlyRate": 15.0,
  "type": "COMMON"
}
```

### Listar vagas de estacionamento

**Parâmetros de consulta (Opcional)**

- **code** : Código da vaga
- **type** : Tipo de vaga
  - COMMON
  - VIP
- **status** : Status da vaga
  - AVAILABLE
  - RESERVED

```http
GET /parking/spots
```

## Decisões Arquiteturais

Esta seção documenta as principais decisões arquiteturais tomadas durante o desenvolvimento do projeto. Aqui, podemos descrever os padrões, frameworks e abordagens utilizadas, bem como os motivos das escolhas.

### Banco de Dados

- Uso do H2 Database para facilitar testes e desenvolvimento local sem necessidade de um banco externo.

### Estrutura do Projeto

- Escolhi utilizar uma arquitetura MVC para o projeto, acredito que seja o suficiente para o desafio, busquei separar por modulos, parking, reservations, customer.
- Decidi criar a entidade Customer, caso necessário ela ajudara a ver um histórico dos clientes, pois adicionei o campo **CPF** sendo uma unique key, também evita criar registros desnecessários no banco.
- Utilizei o Flyway para termos um controle melhor do que é feito no banco
- Na listagem achei importante adicionar uma paginação caso sejam criadas muitas vagas pode acabar perdendo o desempenho da consulta.

### Tecnologias Utilizadas

- **Spring Boot** para criação da aplicação.
- **Maven** para gerenciamento de dependências e build.
- **H2 Database** para armazenamento temporário.
- **JUnit** para testes automatizados.
- **Specifications**  para um melhor aproveitamento das consultas

Caso tenha alguma dúvida, entre em contato!

---

Desenvolvido por Cauã Nunes.

