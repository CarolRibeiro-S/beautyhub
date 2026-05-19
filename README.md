# 💄 BeautyHub — Backend API

API REST do sistema de agendamento de serviços de beleza e bem-estar, desenvolvida em Java com Spring Boot e hospedada no Railway.

---

## 🚀 Tecnologias Utilizadas

- **Java 21** — Linguagem principal
- **Spring Boot 3.2.3** — Framework principal
- **Spring Security + JWT** — Autenticação e autorização
- **Spring Data JPA + Hibernate** — Acesso ao banco de dados
- **MySQL** — Banco de dados em produção
- **Railway** — Hospedagem do backend e banco de dados
- **Maven** — Gerenciador de dependências

---

## 🌐 URL de Produção
https://beautyhub-production.up.railway.app

---

## 📋 Endpoints da API

### Autenticação
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| POST | /auth/login | Login com email e senha | Não |
| POST | /auth/register | Cadastro de novo usuário | Não |

### Serviços
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| GET | /services | Lista todos os serviços | Não |

### Agendamentos
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| POST | /appointments | Cria novo agendamento | Sim |
| GET | /appointments/my | Lista agendamentos do usuário | Sim |
| POST | /appointments/{id}/cancel | Cancela um agendamento | Sim |
| GET | /appointments/ocupados | Retorna horários ocupados por serviço e data | Não |

---

## 🗄️ Banco de Dados

### Tabelas
| Tabela | Descrição |
|---|---|
| users | Usuários cadastrados |
| saloes | Salões de beleza |
| services | Serviços oferecidos |
| appointments | Agendamentos realizados |

### Serviços Cadastrados
19 serviços em 5 categorias: Cabelo, Unhas, Estética, Massagem e Maquiagem.

---

## 🔐 Autenticação

O sistema usa **JWT (JSON Web Token)**. Após o login, o token deve ser enviado no header de todas as requisições autenticadas:
Authorization: Bearer {token}

O token expira em **6 horas**.

---

## ✅ Validações Implementadas

- Email único por usuário
- Senha criptografada com BCrypt
- Validação de conflito de horário — impede dois agendamentos no mesmo serviço e horário
- Agendamentos cancelados não contam como conflito
- Retorno de erro 409 (Conflict) para horário já ocupado
- Retorno de erro 401 para requisições não autenticadas

---

## 🗂️ Estrutura do Projeto
src/main/java/com/beautyhub/
├── controller/       — Controllers REST
├── service/          — Regras de negócio
├── repository/       — Acesso ao banco de dados
├── entity/           — Entidades JPA
├── dto/              — Objetos de transferência de dados
├── security/         — Filtro JWT e serviço de token
├── config/           — Configurações de segurança
└── exception/        — Tratamento global de erros

---

## ⚙️ Como Rodar Localmente

### Pré-requisitos
- Java 21
- Maven
- MySQL rodando localmente

### Variáveis de Ambiente necessárias
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/beautyhub
DB_USERNAME=root
DB_PASSWORD=sua_senha
JWT_SECRET=beautyhub_secret_key_2024
JWT_ISSUER=beautyhub

### Passos
```bash
git clone https://github.com/CarolRibeiro-S/beautyhub.git
cd beautyhub
mvn clean package -DskipTests
java -jar target/*.jar
```

---

## 🔗 Frontend

O app Android está disponível em:
👉 [beautyhub-android (Kotlin)](https://github.com/CarolRibeiro-S/beautyhub-android)

---

## 👩‍💻 Desenvolvido por

**Carol Ribeiro** — Engenharia de Software

© 2026 Carol Ribeiro. Todos os direitos reservados.
