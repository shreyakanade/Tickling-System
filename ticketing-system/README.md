
Ticketing System (Demo)
Backend: Spring Boot (Java 11) + PostgreSQL
Frontend: Next.js (React)

This is a minimal demo scaffold implementing role-based ticketing features.

Backend run:
- Ensure PostgreSQL running and update backend/src/main/resources/application.properties with DB credentials.
- Build and run:
  mvn -f backend/pom.xml spring-boot:run

API highlights:
- POST /api/auth/register {username,password}
- POST /api/auth/login {username,password} -> returns token
- Use header Authorization: Bearer <token>
- POST /api/tickets/create
- GET /api/tickets/my
- POST /api/tickets/{id}/comment
- POST /api/tickets/{id}/status
- POST /api/tickets/{id}/assign
- Admin endpoints under /api/admin/*

Note: For demo only. Passwords stored plaintext. Not production secure.
