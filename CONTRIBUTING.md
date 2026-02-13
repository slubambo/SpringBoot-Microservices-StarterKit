# Contributing Guide

Thanks for contributing to `SpringBoot-Microservices-StarterKit`.

This project is designed as a practical starter kit, so contributions should optimize for:
- onboarding speed
- clarity of architecture and docs
- reliable local and CI behavior

## What to contribute

Useful contribution areas:
- bug fixes and regression tests
- starter-kit improvements (DX, config, docs, templates)
- security hardening and sane defaults
- CI/CD and release workflow improvements

## Prerequisites

- Java 21
- Maven 3.9+
- Docker + Docker Compose
- Git

## Local setup

1. Fork and clone the repository.
2. Create a branch from `main`.
3. Copy environment config:

```bash
cp .env.example .env
```

4. Start the stack (optional for integration testing):

```bash
docker compose up -d
```

## Development workflow

1. Keep changes focused and scoped to one concern.
2. Prefer environment-driven configuration over hardcoded values.
3. Add/update tests when behavior changes.
4. Update docs when user-facing behavior changes.

## Branch naming

Use descriptive branch names, for example:
- `fix/jwt-validation-edge-case`
- `feat/ci-compose-health-check`
- `docs/improve-onboarding`

## Test and verification

Before opening a PR, run:

```bash
(cd naming-server && mvn -B -ntp verify)
(cd user-service && mvn -B -ntp verify)
(cd template-service && mvn -B -ntp verify)
(cd api-gateway && mvn -B -ntp verify)
```

For Compose-related changes, also run:

```bash
docker compose up -d --build
docker compose ps
docker compose down -v
```

## Pull request expectations

Each PR should include:
- clear summary of what changed and why
- testing notes (commands run + outcomes)
- any migration/config impact

PR checklist:
- [ ] changes are minimal and focused
- [ ] tests pass locally
- [ ] docs updated if needed
- [ ] no secrets committed (`.env`, tokens, credentials)

## Commit style

Use short imperative messages, for example:
- `Add compose smoke test for CI`
- `Fix user-service JDBC auth setting`
- `Document Docker publish tag workflow`

## Issues and labels

- Use issue templates for bugs and feature requests.
- Maintainers should triage issues using the label guide in `.github/LABELS.md`.
- Apply `good first issue` only to small, low-risk, well-scoped tasks with clear acceptance criteria.

## Security reports

Do not open public issues for sensitive vulnerabilities.
Use private reporting through GitHub Security Advisories (or contact maintainers directly).
