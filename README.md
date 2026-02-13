# Spring Boot Microservices Starter Kit

[![CI](https://github.com/slubambo/SpringBoot-Microservices-StarterKit/actions/workflows/ci.yml/badge.svg)](https://github.com/slubambo/SpringBoot-Microservices-StarterKit/actions/workflows/ci.yml)
[![Docker Publish](https://github.com/slubambo/SpringBoot-Microservices-StarterKit/actions/workflows/docker-publish.yml/badge.svg)](https://github.com/slubambo/SpringBoot-Microservices-StarterKit/actions/workflows/docker-publish.yml)

A practical starter kit for building Spring Boot microservices with service discovery, gateway routing, JWT authentication, and inter-service communication.

This project is designed for two outcomes:
- Faster onboarding for developers starting with Spring Boot microservices.
- A production-leaning base template you can fork, extend, containerize, and deploy.

## Stack

- Java 21
- Spring Boot 4.0.1
- Spring Cloud 2025.1.0
- Eureka (service discovery)
- Spring Cloud Gateway (routing + auth filter)
- OpenFeign (service-to-service calls)
- MySQL / MariaDB
- Docker + Docker Compose

## Services in this repo

- `naming-server`: Eureka naming server
- `api-gateway`: request gateway and JWT validation
- `user-service`: authentication, users, roles
- `template-service`: template microservice with Feign integration

## Architecture (request flow)

1. Client signs in via `api-gateway` (`/auth/sign-in` -> `user-service`).
2. `user-service` issues JWT token with `session-user` claim.
3. Client calls secured routes through `api-gateway`.
4. Gateway validates token and forwards user context to downstream services.
5. Services discover each other through `naming-server`.

## CI and Automation

- `CI` workflow runs on pull requests to `main`, pushes to `main`, and manual dispatch.
- CI validates each service with Maven `verify` and runs a Docker Compose smoke test for the full stack.
- `Docker Publish` workflow runs only on version tags (`v*`) or manual dispatch.
- Merging to `main` does not push images to Docker Hub by default.

Required GitHub repository secrets for image publishing:
- `DOCKERHUB_USERNAME`
- `DOCKERHUB_TOKEN`

## Quick Start (Docker Compose)

### 1. Configure environment

```bash
cp .env.example .env
```

Edit `.env` if needed (ports, DB password, JWT secret, Docker Hub username).

Default host ports use a predictable, lower-conflict scheme:
- MySQL: `13306`
- Naming server: `18761`
- API Gateway: `18765`
- User service: `18000`
- Template service: `18100`

Internal container ports remain standard (`3306`, `8761`, `8765`, `8000`, `8100`) for service-to-service communication.

If any of these are still taken on your machine, override values in `.env` (or inline):

```bash
API_GATEWAY_PORT=19765 docker compose up -d
```

### 2. Build and run everything

```bash
docker compose up -d
```

This pulls and runs the published Docker Hub images.

If you changed code locally and want to rebuild images from source, package each service first, then run:

```bash
(cd naming-server && mvn -q -DskipTests package)
(cd user-service && mvn -q -DskipTests package)
(cd template-service && mvn -q -DskipTests package)
(cd api-gateway && mvn -q -DskipTests package)

docker compose up --build -d
```

### 3. Check running services

```bash
docker compose ps
```

- Naming server: `http://localhost:18761`
- API Gateway: `http://localhost:18765`
- User service: `http://localhost:18000`
- Template service: `http://localhost:18100`

### 4. Stop services

```bash
docker compose down
```

To remove DB volume too:

```bash
docker compose down -v
```

## Local Run (without Docker)

Run each service in separate terminals:

```bash
cd naming-server && mvn spring-boot:run
cd user-service && mvn spring-boot:run
cd template-service && mvn spring-boot:run
cd api-gateway && mvn spring-boot:run
```

## Default environment variables

See `.env.example` for the full list.

Key values:
- `DB_PORT=13306`
- `USER_DB_NAME=users_db`
- `TEMPLATE_DB_NAME=canva`
- `JWT_SECRET=...`
- `DOCKERHUB_USERNAME=slubambo`

## Docker image publishing (multi-arch)

### Option A: GitHub Actions publish

- On release tag push (for example `v1.0.0`), GitHub Actions builds and publishes multi-arch images.
- You can also trigger publish manually from the `Docker Publish` workflow and provide a custom tag.

### Option B: Local manual publish

This repo includes `scripts/build-and-push-images.sh`, which follows your `buildx` approach and pushes all 4 service images.

1. Login to Docker Hub

```bash
docker login
```

2. Build and push

```bash
DOCKERHUB_USERNAME=slubambo IMAGE_TAG=latest ./scripts/build-and-push-images.sh
```

It pushes:
- `slubambo/springboot-ms-naming-server:latest`
- `slubambo/springboot-ms-user-service:latest`
- `slubambo/springboot-ms-template-service:latest`
- `slubambo/springboot-ms-api-gateway:latest`

### Equivalent manual `buildx` command style

```bash
docker buildx create --name multiarch --use
docker buildx inspect --bootstrap

docker buildx build \
  --platform linux/amd64,linux/arm64 \
  -t slubambo/springboot-ms-api-gateway:latest \
  -f api-gateway/Dockerfile \
  api-gateway \
  --push
```

Repeat for the other service folders and image tags.

## Notes

- `user-service` auto-creates `ROLE_USER` and `ROLE_ADMIN` at startup when missing.
- Use strong secrets in `.env` for real deployments.
- `spring-cloud-config` is optional and disabled in `docker-compose.yml`.

## Contributing

- Read `CONTRIBUTING.md` before opening issues or pull requests.
- Use the built-in GitHub issue templates for bugs and feature requests.
- Maintainers should apply labels using `.github/LABELS.md`.

## Repository Layout

```text
.
├── api-gateway/
├── naming-server/
├── template-service/
├── user-service/
├── docker-compose.yml
├── .env.example
└── scripts/build-and-push-images.sh
```

## License

Licensed under the MIT License.
