#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"

CONFIGURATION_NAME="${CONFIGURATION:-Debug}"
PLATFORM_NAME_VALUE="${PLATFORM_NAME:-iphonesimulator}"

if [[ "${PLATFORM_NAME_VALUE}" == iphonesimulator* ]]; then
  KMP_TARGET="iosSimulatorArm64"
else
  KMP_TARGET="iosArm64"
fi

TASK=":komodo-core:link${CONFIGURATION_NAME}Framework${KMP_TARGET}"

"${ROOT_DIR}/gradlew" -p "${ROOT_DIR}" "${TASK}"
