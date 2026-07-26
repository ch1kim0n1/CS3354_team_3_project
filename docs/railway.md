# Railway deployment

Railway hosts the complete application as one public Docker service and provisions MySQL in the same Railway project. The React client is built into the Spring Boot container, so authentication remains same-origin.

## Create the services

1. In Railway, create an **Empty Project** and add a **MySQL** database service.
2. Add a service from the GitHub repository `ch1kim0n1/CS3354_team_3_project`, using the `main` branch.
3. Keep the service root directory as the repository root. `railway.toml` selects `apps/api/Dockerfile` and configures the health check.
4. In the application service's Variables tab, use Railway reference variables for the MySQL service. Replace `MySQL` below with the database service name if it differs:

```text
DB_URL=jdbc:mysql://${{MySQL.MYSQLHOST}}:${{MySQL.MYSQLPORT}}/${{MySQL.MYSQLDATABASE}}
DB_USERNAME=${{MySQL.MYSQLUSER}}
DB_PASSWORD=${{MySQL.MYSQLPASSWORD}}
SPRING_PROFILES_ACTIVE=prod
SESSION_COOKIE_SECURE=true
ALLOWED_EMAIL_DOMAIN=utdallas.edu
```

5. Generate a public Railway domain for the application service. Railway will deploy from GitHub and only route traffic after `/actuator/health` returns HTTP 200.

## Verify and operate

- Confirm `https://YOUR-DOMAIN/actuator/health` returns `{"status":"UP"}`.
- Register a new UTD email address; production intentionally does not load development seed accounts.
- Keep the MySQL service private. Do not enable or share its public TCP proxy unless a controlled administrative connection is required.
- Set up managed database backups in Railway before major updates. Roll back by redeploying the prior healthy application deployment; use forward Flyway migrations for schema corrections.

No passwords, tokens, or production database URLs belong in Git or GitHub Actions secrets unless a deployment workflow explicitly requires them.
