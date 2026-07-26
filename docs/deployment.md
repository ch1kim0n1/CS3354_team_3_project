# Deployment and Operations

## Required configuration

Set `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` for a managed MySQL database. Do not reuse local development credentials. Configure the application with an always-on container service and a persistent database volume/service.

## Release process

1. Merge a passing pull request to `main`.
2. Build the production Docker image from `apps/api/Dockerfile`; it compiles the React client and packages it into the API container.
3. Run Flyway migrations as part of application startup against a database backup-tested staging environment.
4. Confirm `/actuator/health` is `UP`, then use rolling deployment so the old healthy instance remains available until the replacement is ready.

## Backup and rollback

- Schedule encrypted managed-MySQL backups and retain a tested restoration point before each migration.
- Roll back application images by redeploying the previous immutable image tag.
- Do not delete or modify an applied Flyway migration. Add a forward corrective migration instead.
