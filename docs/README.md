# 🏗️ Fullstack Integrador

![Java](https://img.shields.io/badge/Java-17-red)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
![Docker](https://img.shields.io/badge/Docker-Enabled-blue)
![Maven](https://img.shields.io/badge/Maven-Build-orange)

Projeto desenvolvido como parte de um **desafio técnico Fullstack**, com objetivo de implementar uma aplicação em **arquitetura multicamadas** utilizando Java, Spring Boot, EJB e PostgreSQL.

A aplicação permite **gerenciamento de benefícios e transferência de valores entre contas**, aplicando boas práticas de:

- arquitetura em camadas
- transações
- controle de concorrência
- integração backend / serviços
- persistência de dados

---

# 📐 Arquitetura da Aplicação

A solução segue o padrão **Layered Architecture**.

            +-------------------+
            |   Angular Frontend |
            +-------------------+
                     │
                     ▼
            +-------------------+
            | Spring Boot API   |
            | (backend-module)  |
            +-------------------+
                     │
                     ▼
            +-------------------+
            |  EJB Module       |
            | Business Rules    |
            +-------------------+
                     │
                     ▼
            +-------------------+
            |  PostgreSQL DB    |
            +-------------------+

Cada camada possui responsabilidade específica:

| Camada | Responsabilidade |
|------|----------------|
| Frontend | Interface do usuário |
| Backend | API REST |
| EJB | Regras de negócio e controle transacional |
| Database | Persistência |

---

# 📂 Estrutura do Projeto

bip-teste-integrado
│
├── db
│ ├── schema.sql
│ └── seed.sql
│
├── ejb-module
│ ├── src
│ └── pom.xml
│
├── backend-module
│ ├── src
│ └── pom.xml
│
├── frontend
│
├── docs
│
└── docker-compose.yml

---

# 🧰 Tecnologias Utilizadas

## Backend

- Java 17
- Spring Boot
- Spring Data JPA
- Jakarta EE (EJB)
- Maven

## Infraestrutura

- PostgreSQL
- Docker
- Docker Compose
- WildFly

## Ferramentas

- Postman
- pgAdmin
- IntelliJ IDEA

---

# 🚀 Como Executar o Projeto

---

## 1️⃣ Subindo o banco de dados

Na raiz do projeto execute:

docker compose up -d

Isso irá iniciar os containers:

| Serviço | Porta | Descrição |
|------|------|-----------|
| PostgreSQL | 5442 | Banco de dados |
| pgAdmin | 5050 | Interface de administração |
| WildFly | 8080 | Servidor de aplicação |

---

## 2️⃣ Acessar pgAdmin


http://localhost:5050

Credenciais:

Email: admin@admin.com  
Senha: admin

---

## 3️⃣ Configurar conexão com o banco

| Campo | Valor |
|------|------|
| Host | postgres |
| Port | 5432 |
| Database | beneficios |
| User | admin |
| Password | admin |

---

## 4️⃣ Executar scripts SQL

Executar na ordem:

Criar estrutura do banco

db/schema.sql
db/seed.sql

---

## 5️⃣ Validar dados

SELECT * FROM beneficio;

Resultado esperado:

| id |	nome |	  valor |
|------|------|-----------|
|1 |Beneficio A|1000
|2 |Beneficio B|500

---

# 🐞 Sprint 1 — Correção do Bug no EJB

O método de transferência possuía falhas críticas:

BeneficioEjbService.transfer(...)

Problemas identificados:

* ausência de validações

* possibilidade de saldo negativo

* concorrência não controlada

* risco de **lost update**

---

## 🔧 Melhorias implementadas

Validações adicionadas:

* `fromId` obrigatório

* `toId` obrigatório

* `amount` obrigatório

* valor maior que zero

* transferência para o mesmo benefício bloqueada

* verificação de saldo suficiente

---

## 🔒 Controle de concorrência

Foi utilizado **Pessimistic Locking**:

LockModeType.PESSIMISTIC_WRITE

Isso garante que duas transações não alterem o mesmo registro simultaneamente.

Também foi aplicada **ordenação de IDs** para evitar deadlocks.

---

## 🔁 Rollback automático

Em caso de erro (saldo insuficiente, benefício inexistente etc.), exceções são lançadas e o container EJB realiza **rollback automático da transação**.

---

# ⚙️ Sprint 2 — Backend REST API

Foi implementada uma API REST utilizando **Spring Boot** para gerenciamento de benefícios.

---

Estrutura:

backend-module
│
└── src/main/java/com/example/backend
│
├── model
│   └── Beneficio.java
│
├── repository
│   └── BeneficioRepository.java
│
├── dto
│   └── TransferRequest.java
│
├── controller
│   └── BeneficioController.java
│
└── BackendApplication.java

---

# 🔗 Sprint 3 — Integração Backend + EJB

Nesta etapa foi realizada a integração entre:

- **Spring Boot**
- **EJB Module**

A lógica de transferência passou a ser executada dentro do **EJB Service**, garantindo:

- controle transacional
- controle de concorrência
- rollback automático

Comunicação realizada via **JNDI Lookup**.

---

# 💻 Sprint 4 — Frontend Angular

Foi implementada uma interface simples em **Angular** para interação com a API.

Funcionalidades disponíveis:

- listagem de benefícios
- formulário de transferência
- atualização automática dos valores
- mensagens de sucesso e erro

Interface executando em:

http://localhost:4200


Fluxo completo:

            +-------------------+
            |   Angular Frontend |
            +-------------------+
                     │
                     ▼
            +-------------------+
            | Spring Boot API   |
            | (backend-module)  |
            +-------------------+
                     │
                     ▼
            +-------------------+
            |  EJB Module       |
            | Business Rules    |
            +-------------------+
                     │
                     ▼
            +-------------------+
            |  PostgreSQL DB    |
            +-------------------+

---


# 🌐 API Endpoints

Base URL:

http://localhost:8080/api/v1/beneficios

---

## Benefícios

| Método | Endpoint | Descrição |
|------|------|------|
| GET | /beneficios | Listar benefícios |
| GET | /beneficios/{id} | Buscar por ID |
| POST | /beneficios | Criar benefício |
| PUT | /beneficios/{id} | Atualizar benefício |
| DELETE | /beneficios/{id} | Remover benefício |

---

## 💸 Transferência

Endpoint responsável por transferir valores entre benefícios.

POST /beneficios/transfer


Exemplo de request:

```json
{
  "fromId": 1,
  "toId": 2,
  "amount": 100.00
}
```

Fluxo executado:

1. Backend recebe requisição

2. Valida dados

3. Invoca serviço EJB

4. EJB executa regras de negócio

4. Transação aplicada no banco

---

# 🧪 Testes

Testes realizados com:

- Postman
- Interface Angular

Fluxos validados:

1. Listar benefícios  
2. Criar benefício  
3. Atualizar benefício  
4. Deletar benefício  
5. Transferência entre benefícios  

---

# 📊 Roadmap do Projeto

| Sprint | Descrição | Status |
|------|------|------|
| Sprint 0 | Setup banco | ✅ |
| Sprint 1 | Correção bug EJB | ✅ |
| Sprint 2 | Backend CRUD | ✅ |
| Sprint 3 | Integração Backend + EJB | ✅ |
| Sprint 4 | Frontend Angular | ✅ |
| Sprint 5 | Testes automatizados | ⏳ |
| Sprint 6 | Documentação final | ⏳ |

---

# 👨‍💻 Autor

Lucas Washington Menezes Guiron  

Fullstack Developer

Stack principal:

Java • Spring Boot • Angular • Node • React

---