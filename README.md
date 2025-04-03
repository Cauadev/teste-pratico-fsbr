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



<br>
<br>
<br>
Caso tenha alguma dúvida, entre em contato!

---
Desenvolvido por Cauã Nunes.

