---
name: Code Philosophy
description: KMP &amp; CMP Multi-Module Architecture
---
# Code Philosophy: KMP &amp; CMP Multi-Module Architecture

This document outlines the engineering principles for our Kotlin Multiplatform (KMP) and Compose Multiplatform (CMP) project. Our North Star is **platform-agnosticism**. By prioritizing strict abstractions today, we ensure that adding Desktop, Web, or Wasm in the future requires zero architectural refactoring.

---

## 1. The "Platform-Blind" Core

The shared module must remain "blind" to the platform it is running on.

* **Logic over Implementation:** Shared code defines *what* happens; platform-specific code defines *how* it interacts with the OS.
* **Expect/Actual vs. Interface Injection:** Favor **Interface Injection** (via Dependency Injection) over `expect`/`actual` for business logic. Reserve `expect`/`actual` only for low-level APIs where an interface is syntactically impossible.

## 2. Uncompromising Abstraction

Since we are currently targeting iOS and Android but eyeing future expansion, we follow the **"Interface First"** rule:

* **Boundary Protection:** No platform-specific library (like CoreData or Room) should leak into the `commonMain` business logic.
* **UI Portability:** Use CMP for the UI layer. Ensure that `@Composable` functions are kept pure and rely on ViewModels/StateHolders that reside in `commonMain`.
* **Resource Management:** All strings, fonts, and images must be managed through a multiplatform resource abstraction (e.g., MOKO resources or Compose Resources) to avoid `R.string` or `Named` string dependencies.

## 3. Strict Code Style &amp; Quality

Consistency is our primary tool for managing complexity in a multi-module environment.

> **Mandatory Compliance:** All code must strictly adhere to the project's `detekt` configuration. We do not treat linting as a suggestion; it is a build requirement.

* **Ruleset Location:** `$rootDirPath/codestyle/detekt/detekt.yaml`
* **Pre-commit Requirement:** Run `./gradlew detektCheck` before any push.
* **Suppression:** Code smells should be fixed, not suppressed. `@Suppress` is permitted only in exceptional cases with a documented `Reason:`.

## 4. Module Boundaries

To maintain a fast build and clean separation, we follow a feature-based modularization strategy:

| Module Type | Responsibility |
| --- | --- |
| **:feature:** | Contains UI (CMP) and Feature-specific logic. |
| **:core:** | Common utilities, networking wrappers, and base themes. |
| **:domain:** | Pure Kotlin entities and UseCases (Zero dependencies). |
| **:data:** | Repository implementations and data sources (SQLDelight, Ktor). |

---

## 5. Summary of Best Practices

* **Immutability:** Use `val` and `data class` by default.
* **Concurrency:** Use Kotlin Coroutines and Flow exclusively. Avoid platform-specific threading (like GCD or java.util.concurrent).
* **Dependency Injection:** Use a KMP-native DI framework koin/koin-annotations (everything is automatically configured by applying the "komodo.koin" convention plugin) to provide platform-specific implementations to the common core.
