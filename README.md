# Plataforma Cursos Backend

Backend Quarkus da plataforma de cursos online.

## Requisitos

- Java 17 ou superior
- Maven 3.9+
- Docker Desktop ou outro runtime compativel com Docker Compose

## Banco de dados

O projeto usa PostgreSQL em desenvolvimento:

- host: `localhost`
- porta: `5433`
- database: `plataforma_cursos`
- usuario: `postgres`
- senha: `postgres`

Na raiz do workspace:

```sh
docker compose up -d
```

## Seed de desenvolvimento

Ao subir em `dev`, o backend cria dados iniciais automaticamente:

- admin: `admin@plataforma.com` / `admin123`
- aluno: `aluno@plataforma.com` / `aluno123`
- professores, cursos e aulas de exemplo
- uma matricula ativa do aluno demo com progresso parcial

## Subir em desenvolvimento

No diretorio `plataforma-cursos`:

```sh
mvn quarkus:dev
```

API local: `http://localhost:8180`

## Rodar testes

```sh
mvn test
```

Os testes usam H2 em memoria no perfil `test`, sem depender do PostgreSQL local.

## Configuracao atual

- PostgreSQL em `dev`
- H2 em memoria em `test`
- Geracao de schema com `drop-and-create` em `dev` e `test`
- Schema desabilitado em `prod`
- JWT assinado com `privateKey.pem` e validado com `publicKey.pem`

## Observacoes

- O frontend Angular esta em `../frontend-angular`.
- O script `../start-dev.ps1` sobe banco, backend e frontend.
