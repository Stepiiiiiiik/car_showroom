# Car Showroom

Pet-проект на Spring Boot с REST API для автосалона: каталог автомобилей, управление запчастями, оформление обычных и кастомных заказов, запись на тест-драйвы и ролевой доступ через Keycloak

## О проекте

Проект реализован как backend-сервис с многослойной архитектурой:

- Presentation layer: REST-контроллеры, DTO, MapStruct-мапперы
- Core layer: бизнес-логика, доменные сущности, спецификации, безопасность, конфигурация
- Data layer: репозитории Spring Data JPA для PostgreSQL

Основная цель: показать практическое применение Spring-стека, OAuth2/JWT-аутентификации и миграций БД в реалистичном домене автосалона

## Технологии

- Java 21
- Spring Boot 3.5.13
- Spring Web / Spring MVC
- Spring Data JPA
- Spring Security (OAuth2 Resource Server + OAuth2 Client)
- Spring Validation
- OpenAPI (springdoc-openapi + Swagger UI)
- Keycloak (realm/cars, role-based access)
- PostgreSQL 16
- Liquibase
- MapStruct
- Lombok
- Testcontainers + JUnit 5
- Gradle

## Архитектура

Пакеты организованы по слоям:

- `src/main/java/ru/gorlov/presentation`
	- `controllers`: REST endpoints (`/cars`, `/orders/common`, `/orders/custom`, `/testdrives`, `/users`, `/sparepart`)
	- `dto`: request/response модели
	- `mapper`: преобразования DTO <-> Entity через MapStruct
- `src/main/java/ru/gorlov/core`
	- `services`: бизнес-операции и транзакционные сценарии
	- `entity`: JPA-сущности и value objects
	- `config`: JPA auditing, Security, Keycloak client, OpenAPI
	- `filters` + `specifications`: фильтрация (например, для поиска авто)
	- `exceptions`, `utils`
- `src/main/java/ru/gorlov/data/postgres`
	- репозитории Spring Data JPA

Дополнительно:

- Soft delete через Hibernate `@SoftDelete(columnName = "removed")` в базовой сущности
- Аудит `created_at` / `updated_at` через Spring Data JPA Auditing

## REST API

Основные группы эндпоинтов:

- `/cars`
	- создание, получение, фильтрация, обновление, удаление автомобилей
- `/sparepart`
	- CRUD для запчастей
- `/users`
	- регистрация пользователей и выдача ролей
- `/orders/common`
	- стандартные заказы
- `/orders/custom`
	- кастомные заказы с конфигурацией запчастей
- `/testdrives`
	- управление записями на тест-драйв

Документация API:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Безопасность и Keycloak

В проекте используется Keycloak как провайдер аутентификации и ролей

- OAuth2 Resource Server валидирует JWT-токены
- Права доступа заданы через `@PreAuthorize` на уровне контроллеров
- Realm-роли: `user`, `manager`, `warehouse_manager`, `admin`
- Публичные маршруты:
	- `/users/register-user`
	- `/swagger-ui/**`
	- `/v3/api-docs/**`

Realm-конфигурация импортируется автоматически из `keycloak-export/realm-export.json`

## База данных

- СУБД: PostgreSQL
- Доступ к данным: Spring Data JPA
- Управление схемой: Liquibase

## Локальный запуск (Docker Compose)

Ниже инструкция для полного локального поднятия проекта через Docker Compose (PostgreSQL + Liquibase + App + Keycloak)

### 1. Требования

- Docker
- Docker Compose

### 2. Создать `.env`

В проекте есть готовый шаблон `.env.example`. Создайте `.env` на его основе:

```bash
cp .env.example .env
```

При необходимости измените значения в `.env`:

```env
POSTGRES_DB=cars_db
POSTGRES_USER=admin
POSTGRES_PASSWORD=sa
POSTGRES_PORT=5432
SERVER_PORT=8080
KEYCLOAK_PORT=8081
```

### 3. Запустить сервисы

```bash
docker compose up --build
```

Что поднимется:

- `db` (PostgreSQL)
- `liquibase` (применяет миграции)
- `app` (Spring Boot API)
- `keycloak` (с импортом realm)

### 5. Остановка

```bash
docker compose down
```

Для остановки с удалением volume БД:

```bash
docker compose down -v
```

## Тесты

- Integration tests:

```bash
./gradlew integrationTest
```

Интеграционные тесты используют Testcontainers (PostgreSQL) и проверяют:

- применение миграций и seed-данных
- работу репозиториев
- базовые сценарии REST API + проверку авторизации
