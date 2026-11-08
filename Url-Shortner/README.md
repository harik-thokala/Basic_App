# Smart URL Shortener

A production-grade URL shortener built with Java, Spring Boot, MySQL, and Redis, fully containerized with Docker.

## 🧠 Concepts Covered
### Data Structures & Algorithms
*   **Base62 Encoding:** Converts auto-incremented database IDs into compact, unique string codes (A-Z, a-z, 0-9) without hash collisions.

### Spring Boot Framework
*   **Layered Architecture:** Controller -> Service -> Repository.
*   **Spring Data JPA:** Using `JpaRepository` for ORM operations.
*   **Spring Caching:** Using `@Cacheable` and Redis to drastically reduce database reads.
*   **HTTP Status Codes:** Using `302 FOUND` for URL redirection.

### DevOps
*   **Multi-Stage Docker Build:** Compiles the Maven project in one stage and runs only the compiled `.jar` in the final minimal Alpine image.
*   **Docker Compose:** Orchestrates the Spring Boot App, MySQL Database, and Redis Cache into a single network.

## 🚀 How to Run
1. Ensure Docker Desktop is running.
2. Run: `docker-compose up --build`
3. Create a short URL: `POST http://localhost:8080/api/shorten` (Body: `https://google.com`)
4. Visit the returned URL in your browser.

## 🗄️ Database Schema
*   **Table:** `url_entity`
*   **Columns:** `id` (PK, Auto-Increment), `original_url` (VARCHAR 2048), `created_at` (DATETIME), `click_count` (BIGINT).