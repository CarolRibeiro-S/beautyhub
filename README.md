![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-green)
![MySQL](https://img.shields.io/badge/MySQL-Database-orange)

📌 BeautyHub

Sistema de agendamento para salões de beleza, desenvolvido como projeto de Engenharia de Software.

🚀 Sobre o projeto

O BeautyHub é uma aplicação que permite:

cadastro de usuários
listagem de serviços
agendamento de atendimentos
gerenciamento básico de dados de salão

O projeto está estruturado seguindo uma arquitetura moderna com separação entre backend e frontend.

🧠 Arquitetura

O sistema é dividido em duas partes:

🔹 Backend (Java + Spring Boot)

Responsável por:

regras de negócio
criação e exposição da API REST
conexão com o banco de dados
gerenciamento de usuários, serviços e agendamentos
🔹 Frontend (Kotlin - Android)

Responsável por:

interface do usuário
navegação entre telas
consumo da API
🛠️ Tecnologias utilizadas
Backend
Java 17
Spring Boot
Spring Data JPA
Hibernate
MySQL
Maven
📂 Estrutura do projeto
src/
 └── main/
     └── java/com/beautyhub/
         ├── config/
         ├── controller/
         ├── dto/
         ├── entity/
         ├── repository/
         ├── service/
         ├── security/
🗄️ Banco de dados

O projeto utiliza MySQL.

As tabelas são geradas automaticamente a partir das entidades JPA:

users
services
appointments
saloes
⚙️ Configuração do ambiente
🔹 Pré-requisitos

Antes de rodar o projeto, é necessário ter instalado:

Java 17
MySQL
Maven (ou usar o wrapper do projeto)
Git
🧪 Como rodar o backend
1. Clonar o repositório
git clone https://github.com/SEU-USUARIO/beautyhub.git
cd beautyhub
2. Criar o banco de dados

No MySQL:

CREATE DATABASE beautyhub_db;
3. Configurar o application.properties

Localização:

src/main/resources/application.properties

Exemplo:

spring.datasource.url=jdbc:mysql://localhost:3306/beautyhub_db
spring.datasource.username=root
spring.datasource.password=SUA_SENHA

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
4. Rodar o projeto
./mvnw spring-boot:run

Ou no Windows:

mvnw.cmd spring-boot:run
🌐 Acessando a aplicação

Após iniciar o backend:

http://localhost:8080
🔌 Endpoints disponíveis
Serviços
GET /services

Retorna a lista de serviços cadastrados.

Autenticação
POST /auth/register
POST /auth/login
Agendamentos
POST /appointments
GET /appointments/user/{userId}
📌 Observações importantes
O banco de dados não é versionado no GitHub
Cada desenvolvedor precisa configurar seu MySQL localmente
As tabelas são criadas automaticamente pelo Hibernate
Para funcionamento completo, é necessário ajustar as credenciais no application.properties
📈 Próximos passos
integração com o app mobile (Kotlin)
implementação de autenticação com JWT
melhorias na estrutura de segurança
criação de interface de usuário
👩‍💻 Autora

Carol Ribeiro 🚀
