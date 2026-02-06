# PROJECT KNOWLEDGE BASE

**Generated:** 2026-02-05 16:03:11
**Commit:** 3c7a660
**Branch:** main

## OVERVIEW
Kotlin Multiplatform (KMP) monorepo targeting Android and iOS. Uses Compose Multiplatform for shared UI and a layered feature-module architecture.

## STRUCTURE
```
.
├── androidApp/          # Android-specific entry point and resources
├── build-logic/         # Gradle convention plugins (komodo.*)
├── core-auth/           # Shared authentication logic and native iOS interop
├── feature-*-api/       # Public interface, NavKeys, and models for features
├── feature-*-impl/      # UI, ViewModels, and DI registration for features
├── shared-infra/        # Low-level primitives (kdco-primitives, state management)
├── komodo-core/         # Shared App entry point, aggregator of features
└── theme/               # Shared Design System and Compose tokens
```

## WHERE TO LOOK
| Task | Location | Notes |
|------|----------|-------|
| New Feature | `feature-{name}-api` & `impl` | Follow api/impl split pattern |
| UI Components | `theme/` or `feature-impl` | Theme for primitives, feature for specific UI |
| Shared Primitives| `shared-infra/` | kdco-primitives (Mutex, Logger, etc.) |
| Native Interop | `core-auth/` | iosMain contains manual CF memory management |
| DI / Navigation | `*Module.kt` | Registered via Koin in each module |

## CONVENTIONS
- **Feature Split**: ALWAYS separate features into `:api` and `:impl` modules.
- **Dependency Inversion**: `impl` depends on `api`. Never depend on `impl` directly.
- **Navigation**: Use the shared `Navigator` abstraction. Define `NavKey` in `:api`.
- **DI**: Use Koin. Register components in a `Module.kt` file within the `:impl` package.
- **State Management**: Use `MutableStatefulFlow` from `shared-infra` for predictable state updates.

## ANTI-PATTERNS (THIS PROJECT)
- **Direct Impl Dependencies**: NEVER let one feature impl depend on another feature impl.
- **Generated Code Edits**: DO NOT manually edit files with "Do not edit" headers (e.g., OpenAPI models).
- **Manual Nav Stack**: AVOID managing local navigation stacks if the shared `Navigator` can be used.
- **Verbose Comments**: Use telegraphic style for documentation. Avoid boilerplate.

## UNIQUE STYLES
- **KMP Bridge**: Shared `App()` in `komodo-core` is the common UI root.
- **iOS Interop**: Native modules in `core-auth/iosMain` use manual memory management for CoreFoundation types.

## COMMANDS
```bash
./gradlew assembleDebug      # Build Android
./gradlew test               # Run all tests (mandated by TDD policy)
./gradlew :core-auth:iosX64Test # Run iOS tests
```

## NOTES
- **README Discrepancies**: README refers to `:composeApp` which is replaced by `komodo-core`.
- **iOS Targets**: `iosApp` and `KomodoIOS` exist; `KomodoIOS` is the primary development target.
