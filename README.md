# 💰 Controle Financeiro Pessoal

> Aplicação de controle financeiro pessoal desenvolvida com arquitetura de microsserviços, utilizando Java + Spring Boot, Angular e Docker.

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square&logo=springboot)
![Angular](https://img.shields.io/badge/Angular-19-red?style=flat-square&logo=angular)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=flat-square&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=flat-square&logo=docker)
![Status](https://img.shields.io/badge/Status-Em%20desenvolvimento-yellow?style=flat-square)

---

## 📋 Sobre o Projeto

Aplicação full-stack para controle de finanças pessoais, com foco em **aprendizado de arquitetura distribuída** e construção de **portfólio profissional**.

O projeto foi desenvolvido seguindo boas práticas de mercado: microsserviços independentes, comunicação via REST, autenticação JWT centralizada no API Gateway, resiliência com Resilience4j e orquestração com Docker Compose.

---

## 🏗️ Arquitetura

```
┌─────────────────────────────────────────────────────┐
│                  Angular (4200)                      │
└──────────────────────┬──────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────┐
│         API Gateway — Spring Cloud Gateway (8080)    │
│              JWT Validation · Rate Limiting          │
└──┬───────────────┬──────────────┬───────────────┬───┘
   │               │              │               │
┌──▼───┐      ┌───▼──┐      ┌───▼──┐      ┌────▼──────┐
│ BFF  │      │  ms- │      │  ms- │      │    ms-    │
│(8085)│      │usu.  │      │c.c.  │      │lanc.(8083)│
│      │      │(8081)│      │(8082)│      └───────────┘
│agrega│      └──────┘      └──────┘
│dados │      db_usuarios   db_centro      db_lanc.
│      │
│  ├──→ ms-usuarios
│  ├──→ ms-lancamentos        ┌──────────────────┐
│  └──→ ms-fluxo-caixa ──────►│ ms-fluxo-caixa   │
│                             │     (8084)        │
└─────────────────────────────│  Resilience4j     │
                              │  Circuit Breaker  │
                              └──────────────────┘
                                   db_fluxocaixa
```

### Por que essa arquitetura?

| Decisão | Motivo |
|---|---|
| Microsserviços | Escalabilidade independente por domínio |
| API Gateway | Ponto único de entrada, JWT centralizado |
| BFF | Agrega dados complexos para o frontend |
| Resilience4j | Evita falhas em cascata entre serviços |
| Um banco por serviço | Isolamento de dados, sem acoplamento |

---

## 🛠️ Stack Tecnológica

| Camada | Tecnologia |
|---|---|
| Backend | Java 17 + Spring Boot 3.x |
| API Gateway | Spring Cloud Gateway |
| Comunicação | REST + OpenFeign |
| Resiliência | Resilience4j (Circuit Breaker, Retry, Timeout) |
| Autenticação | JWT + Spring Security |
| Banco de Dados | PostgreSQL 16 |
| Frontend | Angular 19 |
| Orquestração | Docker + Docker Compose |
| Documentação | Swagger / OpenAPI 3 |
| Testes | JUnit 5 + Mockito |
| CI/CD | GitHub Actions (em breve) |

---

## 📦 Estrutura do Projeto

```
financeiro/
├── docker-compose.yml
├── api-gateway/             # Spring Cloud Gateway — porta 8080
├── bff-financeiro/          # Backend for Frontend — porta 8085
├── ms-usuarios/             # Microsserviço de Usuários — porta 8081
├── ms-centro-custo/         # Microsserviço de Centro de Custo — porta 8082
├── ms-lancamentos/          # Microsserviço de Lançamentos — porta 8083
├── ms-fluxo-caixa/          # Microsserviço de Fluxo de Caixa — porta 8084
└── frontend-financeiro/     # Aplicação Angular — porta 4200
```

---

## 📋 Módulos

### 👤 ms-usuarios (8081)
- Cadastro e autenticação de usuários
- Geração e validação de tokens JWT
- CRUD completo de usuários

### 🗂️ ms-centro-custo (8082)
- Cadastro de centros de custo
- CRUD completo
- Vinculação com lançamentos

### 💸 ms-lancamentos (8083)
- Registro de receitas e despesas
- CRUD completo com filtros por período e categoria
- Vinculação com centros de custo

### 📊 ms-fluxo-caixa (8084)
- Relatórios financeiros consolidados
- Extrato por período
- Saldo atual e projeções
- Consome `ms-lancamentos` e `ms-centro-custo` via OpenFeign + Resilience4j

---

## 🚀 Como Executar

### Pré-requisitos
- Docker e Docker Compose instalados
- Java 17+
- Node.js 18+ (para o frontend)

### Subindo o projeto completo

```bash
# Clone o repositório
git clone https://github.com/Taciolb/financeiro.git
cd financeiro

# Sobe todos os serviços
docker compose up -d

# Acompanha os logs
docker compose logs -f
```

### Serviços disponíveis após o start

| Serviço | URL |
|---|---|
| Frontend Angular | http://localhost:4200 |
| API Gateway | http://localhost:8080 |
| BFF | http://localhost:8085 |
| ms-usuarios | http://localhost:8081/swagger-ui.html |
| ms-centro-custo | http://localhost:8082/swagger-ui.html |
| ms-lancamentos | http://localhost:8083/swagger-ui.html |
| ms-fluxo-caixa | http://localhost:8084/swagger-ui.html |

---

## 🗺️ Roadmap

- [x] Definição da arquitetura
- [x] Configuração dos bancos de dados
- [x] Criação dos projetos Spring Boot
- [ ] ms-usuarios — CRUD + JWT
- [ ] ms-centro-custo — CRUD
- [ ] ms-lancamentos — CRUD
- [ ] ms-fluxo-caixa — Relatórios + Resilience4j
- [ ] BFF — Agregação de dados
- [ ] API Gateway — Roteamento + Segurança
- [ ] Docker Compose — Orquestração completa
- [ ] Frontend Angular
- [ ] CI/CD com GitHub Actions

---

## 🧪 Testes

```bash
# Roda os testes de um microsserviço
cd ms-usuarios
./mvnw test

# Roda com relatório de cobertura
./mvnw test jacoco:report
```

Meta de cobertura: **80%+**

---

## 👨‍💻 Autor

**Tacio** — [@Taciolb](https://github.com/Taciolb)

---

## 📄 Licença

Este projeto está sob a licença MIT.
