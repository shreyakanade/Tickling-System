backend/ — Spring Boot scaffold

TicketingApplication.java (main)

model/ — User, Ticket, Comment entity classes

repository/ — UserRepository, TicketRepository

controller/ — AuthController (stub), TicketController (basic CRUD/status update)

resources/application.properties — Postgres connection + JPA settings

pom.xml — minimal dependencies (Spring Web, Data JPA, Postgres)

frontend/ — Next.js scaffold

pages/index.js — minimal homepage

package.json — scripts and dependencies

docker-compose.yml — services for db (Postgres), backend, frontend

README.md — instructions to run and next steps

ticketing-project-report.pdf — project report with diagram and extension guidance

Important notes & next steps (what you should implement to reach production-ready)

Authentication & Authorization

Add Spring Security + JWT.

Hash passwords with BCrypt; store roles (USER, AGENT, ADMIN).

Protect endpoints: users can access only their tickets; agents can manage assigned tickets; admins can override.

Backend features to add

Services layer (business logic), DTOs, and mappers.

Full controllers for admin actions (user management, force reassign/close).

Email notifications (SMTP) using Spring Mail and templating (Thymeleaf or simple text templates).

Search & filter endpoints (by subject/status/priority/user).

Frontend features to add

Login/logout, token storage (HttpOnly cookie recommended), role-aware UI.

User dashboard: create ticket, list tickets, ticket detail with comments.

Admin panel: manage users and all tickets.

Support agent views & assignment controls.

Testing & deployment

Unit & integration tests for backend.

CI pipeline and production-ready Dockerfiles.

Configure CORS and environment secrets (never commit passwords).
