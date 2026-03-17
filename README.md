# PEDEAI API 🍽️🛵

API REST desenvolvida para gerenciamento de pedidos e restaurantes, inspirada em plataformas de delivery como o iFood. A aplicação permite cadastro de usuários, gerenciamento de restaurantes e criação de pedidos, com autenticação segura e controle de acesso por perfis.

## ⚙️ Funcionalidades
- Cadastro e autenticação de usuários
- Controle de acesso baseado em perfis (roles)
- Cadastro e gerenciamento de restaurantes
- Criação e gerenciamento de pedidos
- Autenticação via JWT
- Documentação interativa das rotas com Swagger
- Ambiente dockerizado para execução da aplicação
---
## 🛠️ Tecnologias Utilizadas
- **Backend:** Java, Spring Boot( Security, JWT, Hibernate)
- **Banco de Dados:** PostgreSQL
- **DevOps/Infra:** Docker & Docker Compose
- **Documentação e testes:** Swagger, Postman

---
## 📄 Documentação da API
Todas as rotas da aplicação estão documentadas utilizando Swagger, permitindo visualizar e testar os endpoints **públicos** diretamente pelo navegador.
Após iniciar a aplicação, acesse:
```bash
http://localhost:8080/swagger-ui.html

```
---
## 🔒Segurança
O sistema utiliza autenticação baseada em **JWT (JSON Web Token)**.
Após realizar login, o usuário recebe um token que lhe da acesso as rotas protegidas por autenticação que  foram testadas utilizando o **Postman**, onde o token é enviado no header:
Exemplo de header:
```bash
Authorization: Bearer SEU_TOKEN
```
#### **Além disso, o controle de acesso é feito com roles, garantindo que apenas usuários autorizados possam acessar determinados endpoints.**
---
## 📂 Estrutura do Projeto
```bash
src
└── main
    ├── java/com/guilherme/pedeai
    │
    │   ├── controller        # Endpoints da API (camada REST)
    │   ├── service           # Regras de negócio da aplicação
    │   ├── repository        # Interfaces de acesso ao banco (Spring Data JPA)
    │   ├── model             # Entidades que representam as tabelas do banco
    │   │
    │   ├── DTO
    │   │   ├── Request       # Objetos utilizados para receber dados da requisição
    │   │   └── Response      # Objetos utilizados para retorno das respostas
    │   │
    │   ├── security          # Configurações de autenticação e JWT
    │   ├── config            # Configurações da aplicação (Swagger, etc)
    │   │
    │   └── PedeaiApiApplication.java   # Classe principal da aplicação
    │
    └── resources
        │
        ├── db
        │   └── migration     # Scripts de versionamento do banco (Flyway)
        │       ├── V1__create_tables.sql
        │       └── V2__insert_roles.sql
        │
        └── application.yaml  # Configurações da aplicação (DB, porta, etc)

```

## 🗄️ Modelagem do Banco de Dados


<img width="1310" height="379" alt="Captura de tela 2026-03-17 121846" src="https://github.com/user-attachments/assets/c5973fbc-1b51-47b0-9572-143fa80c38d8" />

---
## 🚀 Como executar o projeto
### 1️⃣ Clonar o repositório
```bash
https://github.com/guilhermebs7/pedeai-api-documentacao
```
### 2️⃣ entrar na pasta do projeto
```bash
cd pedeai-api
```
### 3️⃣ Criar um arquivo .env na raiz da pasta
```bash
POSTGRES_USER=seu_usuario_aqui
POSTGRES_PASSWORD=sua_senha_aqui
```
### 4️⃣ Executar com Docker
Digite no terminal: 
```bash
docker-compose up --build

```
