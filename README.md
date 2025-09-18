# URL shortener

A simple URL shortener built with Java and Spring Boot.

## Features

- Shorten long URLs to compact, shareable links
- Redirect short URLs to original destinations
- Track click statistics for each short URL
- Database Integration with PostgreSQL(Docker)
- Global Exception Handling
- API Documentation with Swagger

## Getting Started
### Prerequisites
- Java 11
- Maven
- Docker (for PostgreSQL)

### Installation
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

## Technologies Used
- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Docker