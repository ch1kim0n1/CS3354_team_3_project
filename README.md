# Study Buddy Finder

Study Buddy Finder helps university students discover compatible classmates, form study groups, schedule sessions, and communicate inside approved groups. It is the CS3354 Group 3 course project.

The original course scope files - [`ProjectScope.md`](ProjectScope.md) and [`project scope.txt`](project%20scope.txt) - are intentionally retained as historical deliverables.

## Features

- Secure student/admin accounts with server-side sessions and a 30-minute inactivity timeout.
- Private student profiles, courses, study preferences, and availability windows.
- Explainable matching based on shared courses, interests, study mode, and availability overlap.
- Approval-based group membership, group sessions, and group-only discussion messages.
- Reporting, account deactivation, message removal, and audit logs for administrators.

## Quick start

### Docker (recommended)

1. Copy `.env.example` to `.env` and choose local-only database passwords.
2. Run `docker compose -f infra/docker-compose.yml up --build`.
3. Open `http://localhost:8080` and sign in with `ada@utdallas.edu` / `StudyBuddy123!`.
4. API docs are available at `http://localhost:8080/api/docs` and health at `/actuator/health`.

### Local development

Start MySQL with `docker compose -f infra/docker-compose.yml up db`, then run the API from `apps/api` with Maven and `SPRING_PROFILES_ACTIVE=dev`. In a second terminal, run `npm install` then `npm run dev` in `apps/web`.

The local environment provides Java 17 compatibility; the project can also run on Java 21. The production Docker image uses Java 17 for reproducible builds.

## Demo accounts

Development seed data is only enabled when the `dev` profile is active:

| Role | Email | Password |
| --- | --- | --- |
| Student | `ada@utdallas.edu` | `StudyBuddy123!` |
| Student | `sam@utdallas.edu` | `StudyBuddy123!` |
| Admin | `admin@utdallas.edu` | `StudyBuddy123!` |

Never enable these credentials in production.

## Project structure

```text
apps/api/       Spring Boot REST API, Flyway migrations, unit tests
apps/web/       React and TypeScript client
infra/          Docker Compose
docs/           diagrams, API guide, testing, course artifacts, deployment guide
```

## Security and privacy

Authentication is required for all application data. Match cards expose only safe public fields; email addresses, detailed availability, audit logs, and group messages remain private. Joining a group requires the coordinator’s approval. See [the API guide](docs/api.md) and [test plan](docs/test-plan.md).

## Contribution workflow

Create focused feature branches, open pull requests into `main`, and keep the build green. GitHub Actions runs the Java tests, frontend production build, and production container build. Do not commit `.env`, credentials, databases, or generated `node_modules`/`target` files.

## Deployment

Use an always-on container platform and managed MySQL for production availability; a local machine or sleeping free-tier service does not satisfy the availability goal. Follow [deployment.md](docs/deployment.md) for environment variables, health checks, migrations, backups, and rollback.
