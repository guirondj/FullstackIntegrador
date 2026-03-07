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

---

# 📐 Arquitetura da Aplicação

A solução segue o padrão **Layered Architecture**.

Frontend (Angular)
│
▼
Backend API (Spring Boot)
│
▼
EJB Module (Regras de negócio / Transações)
│
▼
PostgreSQL Database


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

### Backend

- Java 17
- Spring Boot
- Spring Data JPA
- Jakarta EE (EJB)
- Maven

### Infraestrutura

- PostgreSQL
- Docker
- Docker Compose

### Ferramentas

- Postman
- pgAdmin
- IntelliJ IDEA

---

# 🚀 Como Executar o Projeto

---

## 1️⃣ Subindo o banco de dados

Na raiz do projeto execute:

docker compose up -d

Este comando inicia os seguintes containers:

| Serviço | Porta | Descrição |
|------|------|-----------|
| PostgreSQL | 5442 | Banco de dados da aplicação |
| pgAdmin | 5050 | Interface de administração do banco |

---

## 2️⃣ Acessar o pgAdmin

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

### Executar na ordem:

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

* risco de lost update

---

## 🔧 Melhorias implementadas

Validações adicionadas:

* fromId obrigatório

* toId obrigatório

* amount obrigatório

* valor maior que zero

* transferência para o mesmo benefício bloqueada

* verificação de saldo suficiente

---

## 🔒 Controle de concorrência

Foi utilizado Pessimistic Locking:

LockModeType.PESSIMISTIC_WRITE

Isso garante que duas transações não alterem o mesmo registro simultaneamente.

Também foi aplicada ordenação de IDs para evitar deadlocks.

---

## 🔁 Rollback automático

Em caso de erro (saldo insuficiente, benefício inexistente etc.), exceções são lançadas e o container EJB realiza rollback automático da transação.

---

# ⚙️ Sprint 2 — Backend REST API

Foi implementada uma API REST utilizando Spring Boot para gerenciamento de benefícios.

---

## Estrutura

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

## 🌐 Endpoints da API

### Base URL:

http://localhost:8080/api/v1/beneficios

### Listar benefícios : 

GET /api/v1/beneficios

### Buscar por ID:

GET /api/v1/beneficios/{id}

###  Criar benefício:

POST /api/v1/beneficios

#### Exemplo:

{
  "nome": "Beneficio C",
  "descricao": "Descrição C",
  "valor": 250.00,
  "ativo": true
}

---

### Atualizar benefício:

PUT /api/v1/beneficios/{id}

---

### Remover benefício:

DELETE /api/v1/beneficios/{id}

---

# 💸 Endpoint de Transferência

---

POST /api/v1/beneficios/transfer

#### Exemplo:

---

{
  "fromId": 1,
  "toId": 2,
  "amount": 100.00
}

---

Nesta fase o endpoint recebe e valida a requisição.

A lógica completa de transferência será conectada ao EJB Module na próxima etapa.

---

# 🧪 Testes

Os endpoints foram testados utilizando Postman.

---

## Fluxo validado:

1. Criar benefício

2. Listar benefícios

3. Buscar por ID

4. Atualizar benefício

5. Deletar benefício

6. Enviar requisição de transferência

---

# 📊 Roadmap do Projeto

---

|Sprint | Descrição| Status|
|------|------|------|
| Sprint 0 |Setup do banco|✅ |
| Sprint 1 |Correção bug EJB |✅ |
| Sprint 2 | Backend CRUD |✅
| Sprint 3 | Integração Backend + EJB | ⏳ |
| Sprint 4 | Frontend Angular| ⏳ |
| Sprint 5 | Testes | ⏳ |
| Sprint 6 | Documentação final | ⏳ |

---

# 👨‍💻 Autor

----

Lucas Washington Menezes Guiron

Desenvolvedor Fullstack

Stack principal: 

Java • Spring Boot • Angular • Node • React


---