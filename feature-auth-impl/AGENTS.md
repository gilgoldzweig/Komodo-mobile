# FEATURE-AUTH-IMPL KNOWLEDGE BASE

## OVERVIEW
Implementation of Auth UX (Login and Server Setup). Coordinates logic between UI (Compose), ViewModels, and the domain/security layers.

## STRUCTURE
- **ui**: `LoginScreen`, `ServerSetupScreen` and their respective ViewModels.
- **AuthModule**: Koin DI registration for all implementation components.
- **navigation**: Maps `NavKeys` from `:api` to implementation composables.

## WHERE TO LOOK
- **DI Entry**: `feature-auth-impl/src/commonMain/kotlin/.../AuthModule.kt`.
- **Nav Mapping**: `feature-auth-impl/src/commonMain/kotlin/.../navigation/AuthNavigation.kt`.
- **Business Logic**: `LoginViewModel.kt` (State transitions Idle -> Loading -> Success).

## CONVENTIONS
- **ViewModel-First**: All logic and state transitions MUST live in ViewModels.
- **Stateless Screens**: Composables should only subscribe to state and emit events.
- **Explicit States**: Model UI states (Idle, Loading, etc.) as sealed classes or enums.

## ANTI-PATTERNS
- **Logic in UI**: Never perform validation or network calls inside composables.
- **Manual Nav**: Avoid managing the navigation stack directly; use the shared `Navigator`.
- **Direct Imp Deps**: Do not depend on this module from other features; use the `:api` module.
