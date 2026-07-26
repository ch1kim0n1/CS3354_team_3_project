# API Guide

The API is versioned under `/api/v1`. Interactive OpenAPI documentation is served at `/api/docs` when the API is running.

## Session and CSRF flow

1. Request `GET /auth/csrf` before any state-changing request.
2. Send the returned token in the `X-CSRF-TOKEN` header with `POST` and `PUT` requests.
3. The server creates an HTTP-only session cookie after `POST /auth/login`.
4. The session expires after 30 minutes of inactivity.

## Endpoint groups

| Area | Routes |
| --- | --- |
| Authentication | `GET /auth/csrf`, `POST /auth/register`, `POST /auth/login`, `POST /auth/logout`, `GET /auth/me` |
| Profile | `GET/PUT /profile`, `POST /profile/courses`, `PUT /profile/availability` |
| Matching | `GET /matches` |
| Groups | `GET/POST /groups`, group join requests, membership decisions, sessions, and messages beneath `/groups/{id}` |
| Reporting | `POST /reports` |
| Administration | `GET /admin/reports`, account status and message moderation routes; admin role required |

All request and response models are defined as DTOs. Database entities and password hashes are never serialized to clients.
