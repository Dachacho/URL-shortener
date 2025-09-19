# URL shortener

A simple URL shortener built with Spring Boot and PostgreSQL. 
Create, manage, and track short links—fully tested, Dockerized, and API-documented.

## Features

- Shorten any URL: POST a long URL, get a short code.
- Redirect: GET a short code, instantly redirect to the original link.
- Expiration: All links expire after 24 hours (configurable).
- Click analytics: Track how many times each link is used.
- Custom aliases: (Optional) Reserve your own short code.
- Info endpoint: GET metadata about any short link.
- Robust validation: Safe, normalized URLs only.
- Global error handling: Consistent 400/404 responses.
- API docs: Swagger UI included.
- Tests: Full coverage for utils, services, and controllers.
- Dockerized: One command to launch Postgres and the app.

## Tech Stack
- Java 17
- Spring Boot
- PostgreSQL (via docker-compose)
- JPA/Hibernate
- JUnit & Mockito
- Swagger/OpenAPI
- Docker

## Getting Started

1. Clone the repository:
   ```bash
   git clone
    cd url-shortener
    ```
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Run PostgreSQL using Docker:
   ```bash
   docker-compose up -d
   ```
4. Configure database settings in `src/main/resources/application.properties`:

5. Run the application 
6. go over at `http://localhost:8080/swagger-ui.html` for to test the API.

## Project Structure
- `controllers/` — API endpoints
- `services/` — Business logic
- `models/` — JPA entities
- `dtos/` — Request/response objects
- `repositories/` — JPA repositories
- `util/` — URL normalization, ID generation, validation
- `exception/` — Global error handler