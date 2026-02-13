#!/usr/bin/env bash
set -euo pipefail

DOCKERHUB_USERNAME="${DOCKERHUB_USERNAME:-slubambo}"
IMAGE_TAG="${IMAGE_TAG:-latest}"
PLATFORMS="${PLATFORMS:-linux/amd64,linux/arm64}"
BUILDER_NAME="${BUILDER_NAME:-multiarch}"

if ! docker buildx inspect "${BUILDER_NAME}" >/dev/null 2>&1; then
  docker buildx create --name "${BUILDER_NAME}"
fi

docker buildx use "${BUILDER_NAME}"
docker buildx inspect --bootstrap

build_and_push() {
  local service_dir="$1"
  local image_name="$2"

  docker buildx build \
    --platform "${PLATFORMS}" \
    -t "${DOCKERHUB_USERNAME}/${image_name}:${IMAGE_TAG}" \
    -f "${service_dir}/Dockerfile" \
    "${service_dir}" \
    --push
}

build_and_push naming-server springboot-ms-naming-server
build_and_push user-service springboot-ms-user-service
build_and_push template-service springboot-ms-template-service
build_and_push api-gateway springboot-ms-api-gateway

printf '\nPushed images:\n'
printf '  %s/springboot-ms-naming-server:%s\n' "${DOCKERHUB_USERNAME}" "${IMAGE_TAG}"
printf '  %s/springboot-ms-user-service:%s\n' "${DOCKERHUB_USERNAME}" "${IMAGE_TAG}"
printf '  %s/springboot-ms-template-service:%s\n' "${DOCKERHUB_USERNAME}" "${IMAGE_TAG}"
printf '  %s/springboot-ms-api-gateway:%s\n' "${DOCKERHUB_USERNAME}" "${IMAGE_TAG}"
