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

```
Frontend (Angular)
⬇
Backend API (Spring Boot)
⬇
EJB Module (Regras de Negócio / Transações)
⬇
Banco de Dados (PostgreSQL)
```

Cada camada possui responsabilidade bem definida:

| Camada         | Responsabilidade                          |
| -------------- | ----------------------------------------- |
| Frontend       | Interface de usuário                      |
| Backend        | API REST e integração                     |
| EJB            | Regras de negócio e controle transacional |
| Banco de dados | Persistência                              |

---

# 📂 Estrutura do Projeto

```
bip-teste-integrado
│
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
```

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

```bash
docker compose up -d
```

Este comando inicia os seguintes containers:

| Serviço    | Porta | Descrição                           |
| ---------- | ----- | ----------------------------------- |
| PostgreSQL | 5442  | Banco de dados da aplicação         |
| pgAdmin    | 5050  | Interface de administração do banco |

---

# 🌐 Acessando o pgAdmin

Abra no navegador:

```
http://localhost:5050
```

Credenciais:

```
Email: admin@admin.com
Senha: admin
```

---

# 🔌 Conectando ao banco

Criar um novo servidor no pgAdmin com os seguintes dados:

| Campo    | Valor      |
| -------- | ---------- |
| Host     | postgres   |
| Port     | 5432       |
| Database | beneficios |
| User     | admin      |
| Password | admin      |

---

# 🗄️ Executando os scripts do banco

Após conectar ao banco, executar os scripts presentes na pasta `db`.

### 1️⃣ Criar estrutura do banco

Executar:

```
db/schema.sql
```

Este script cria a estrutura da tabela **beneficio**.

---

### 2️⃣ Inserir dados iniciais

Executar:

```
db/seed.sql
```

Este script insere dados iniciais para teste da aplicação.

---

# ✔️ Validação

Após executar os scripts, rodar a seguinte consulta:

```sql
SELECT * FROM beneficio;
```

Resultado esperado:

| id | nome        | valor |
| -- | ----------- | ----- |
| 1  | Beneficio A | 1000  |
| 2  | Beneficio B | 500   |

---

# 🐞 Sprint 1 — Correção do Bug no EJB

O módulo EJB continha um bug crítico no método de transferência de benefícios:

```
BeneficioEjbService.transfer(...)
```

A implementação original:

* não validava parâmetros
* permitia saldo negativo
* não tratava concorrência
* podia gerar **lost updates**

---

## 🔧 Melhorias implementadas

Foram adicionadas validações e controle transacional para garantir consistência de dados.

### Validações implementadas

* `fromId` e `toId` obrigatórios
* `amount` obrigatório
* valor da transferência deve ser maior que zero
* transferência para o mesmo benefício não é permitida
* validação de saldo suficiente

---

### Controle de concorrência

Foi implementado **Pessimistic Locking** utilizando:

```
LockModeType.PESSIMISTIC_WRITE
```

Isso garante que duas transações não modifiquem o mesmo registro simultaneamente.

Além disso, foi aplicada uma estratégia para **evitar deadlocks**, realizando o lock sempre na mesma ordem de IDs.

---

### Garantia de rollback

Em caso de erro (saldo insuficiente, benefício inexistente, etc.), são lançadas exceções que causam **rollback automático da transação** no container EJB.

---

## ✔️ Build do módulo EJB

O módulo foi compilado utilizando Maven:

```
mvn -f ejb-module/pom.xml clean package
```

Resultado:

```
BUILD SUCCESS
```

Gerando o artefato:

```
ejb-module/target/ejb-module-1.0.0.jar
```

---

# 📊 Status do Projeto

| Sprint   | Descrição                     | Status         |
| -------- | ----------------------------- | -------------- |
| Sprint 0 | Setup do banco de dados       | ✅ Concluído    |
| Sprint 1 | Correção do bug no EJB        | ✅ Concluído    |
| Sprint 2 | Backend CRUD + Integração EJB | ⏳ Em andamento |
| Sprint 3 | Frontend Angular              | ⏳ Pendente     |
| Sprint 4 | Testes                        | ⏳ Pendente     |
| Sprint 5 | Documentação final            | ⏳ Pendente     |

---

# 👨‍💻 Autor

Lucas Washington Menezes Guiron
Desenvolvedor Fullstack

Stack principal:

```
Java • Spring Boot • Angular • Node • React
```
