
# 🏗️ Fullstack Integrador

Projeto desenvolvido como parte de um desafio técnico para implementação de uma aplicação **Fullstack em arquitetura multicamadas**, envolvendo:

* Banco de dados
* Serviços EJB
* Backend Spring Boot
* Frontend Angular

O objetivo é construir uma aplicação funcional que permita gerenciamento e transferência de benefícios, aplicando boas práticas de arquitetura, concorrência e transações.

---

# 📐 Arquitetura do Projeto

A solução segue uma arquitetura em camadas:

Frontend (Angular)  
⬇  
Backend API (Spring Boot)  
⬇  
EJB Module (Regras de Negócio / Transações)  
⬇  
Banco de Dados (PostgreSQL)

Cada camada possui responsabilidade bem definida:

| Camada | Responsabilidade |
|------|----------------|
| Frontend | Interface de usuário |
| Backend | API REST e integração |
| EJB | Regras de negócio e controle transacional |
| Banco de dados | Persistência |

---

# 📂 Estrutura do Projeto

bip-teste-integrado

├── db  
│   ├── schema.sql  
│   └── seed.sql  
│
├── ejb-module  
│   ├── src  
│   └── pom.xml  
│
├── backend-module  
│
├── frontend  
│
├── docs  
│
└── docker-compose.yml  

---

# 🚀 Sprint 0 — Setup do Banco de Dados

Nesta etapa foi configurado o ambiente de banco de dados utilizando **Docker** e **PostgreSQL**, permitindo que qualquer desenvolvedor execute o ambiente local com apenas um comando.

## 🧰 Tecnologias utilizadas

* Docker
* Docker Compose
* PostgreSQL 15
* pgAdmin 4

---

# 🐳 Subindo o banco de dados

Na raiz do projeto execute:

docker compose up -d

Este comando inicia os seguintes containers:

| Serviço | Porta | Descrição |
|------|------|-----------|
| PostgreSQL | 5442 | Banco de dados da aplicação |
| pgAdmin | 5050 | Interface de administração do banco |

---

# 🌐 Acessando o pgAdmin

http://localhost:5050

Credenciais:

Email: admin@admin.com  
Senha: admin

---

# 🔌 Conectando ao banco

| Campo | Valor |
|------|------|
| Host | postgres |
| Port | 5432 |
| Database | beneficios |
| User | admin |
| Password | admin |

---

# 🗄️ Executando os scripts

1️⃣ Criar estrutura do banco

db/schema.sql

2️⃣ Inserir dados iniciais

db/seed.sql

Consulta de validação:

SELECT * FROM beneficio;

---

# 🐞 Sprint 1 — Correção do Bug no EJB

O módulo EJB continha um bug crítico no método de transferência de benefícios.

BeneficioEjbService.transfer(...)

Problemas existentes:

* ausência de validações
* possibilidade de saldo negativo
* ausência de controle de concorrência
* risco de lost update

## Melhorias implementadas

Validações:

* fromId obrigatório
* toId obrigatório
* amount obrigatório
* valor maior que zero
* transferência para mesmo benefício bloqueada
* verificação de saldo suficiente

### Controle de concorrência

Utilizado:

LockModeType.PESSIMISTIC_WRITE

Garantindo que duas transações não alterem o mesmo registro simultaneamente.

Também foi aplicada ordenação de IDs para evitar deadlocks.

### Rollback automático

Exceções geradas durante a operação provocam rollback automático da transação EJB.

Build do módulo:

mvn -f ejb-module/pom.xml clean package

---

# ⚙️ Sprint 2 — Backend REST API

Nesta sprint foi desenvolvido o backend utilizando **Spring Boot**, responsável por expor uma API REST para gerenciamento dos benefícios.

## Tecnologias

* Java 17
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Maven

## Estrutura do módulo backend

backend-module

src/main/java/com/example/backend

model/Beneficio.java  
repository/BeneficioRepository.java  
dto/TransferRequest.java  
BeneficioController.java  
BackendApplication.java  

---

# 🌐 Endpoints da API

Base URL:

http://localhost:8080/api/v1/beneficios

### Listar benefícios

GET /api/v1/beneficios

### Buscar por ID

GET /api/v1/beneficios/{id}

### Criar benefício

POST /api/v1/beneficios

Exemplo:

{
 "nome": "Beneficio C",
 "descricao": "Descrição C",
 "valor": 250.00,
 "ativo": true
}

### Atualizar benefício

PUT /api/v1/beneficios/{id}

### Deletar benefício

DELETE /api/v1/beneficios/{id}

---

# 💸 Endpoint de Transferência

POST /api/v1/beneficios/transfer

Exemplo:

{
 "fromId": 1,
 "toId": 2,
 "amount": 100.00
}

Nesta etapa o endpoint recebe e valida a requisição.  
A lógica de transferência será integrada ao módulo EJB na próxima sprint.

---

# 🧪 Testes

Endpoints testados via **Postman**:

1. Criar benefício
2. Listar benefícios
3. Buscar por ID
4. Atualizar benefício
5. Deletar benefício
6. Testar requisição de transferência

---

# 📊 Status do Projeto

| Sprint | Descrição | Status |
|------|-----------|--------|
| Sprint 0 | Setup do banco | ✅ Concluído |
| Sprint 1 | Correção bug EJB | ✅ Concluído |
| Sprint 2 | Backend CRUD + API | ✅ Concluído |
| Sprint 3 | Integração Backend + EJB | ⏳ Próxima |
| Sprint 4 | Frontend Angular | ⏳ Pendente |
| Sprint 5 | Testes e documentação | ⏳ Pendente |

---

# 👨‍💻 Autor

Lucas Washington Menezes Guiron

Desenvolvedor Fullstack