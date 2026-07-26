# Test Plan and Deliverable Evidence

## Automated unit evidence

`MatchServiceTest` supplies the required true/false demonstration:

- **True case:** two students share CS 3354 and overlapping Tuesday availability; the service returns a ranked match with explanations.
- **False case:** two students share no active course; the service returns no match.

Run with `mvn test` in `apps/api`, or rely on the API job in GitHub Actions.

## Acceptance scenarios

1. Register, log in, complete onboarding, add course and availability, and view explained matches.
2. Create a course group, request to join from another account, approve the request, and verify that messages/sessions are inaccessible before approval.
3. Create a group session and group message as a coordinator/member.
4. Submit a report as a student; verify the admin queue sees it and can deactivate an account or remove a message.
5. Verify one student cannot read another student’s email, detailed availability, private group data, audit logs, or administration routes.

## Manual quality checks

- Keyboard-only navigation and visible focus on forms and navigation.
- Mobile layout at 375px and desktop layout at 1280px.
- Invalid/duplicate course, invalid session time range, invalid availability range, full group, and unauthorized membership paths show a clear error.
