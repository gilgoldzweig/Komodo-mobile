# SHARED-INFRA KNOWLEDGE BASE

## OVERVIEW
Low-level foundations: state models, flow wrappers, and navigation primitives. Generic and reusable building blocks for feature modules.

## STRUCTURE
- **state**: `State<T, E>` sealed class and `MutableStatefulFlow` wrappers.
- **navigation**: `Navigator` interface and `NavigationState` holder.
- **primitives**: Implementation of cross-cutting utilities (Mutex, Logger style).

## WHERE TO LOOK
- **State Model**: `shared-infra/src/commonMain/kotlin/.../state/State.kt`.
- **Flow Helpers**: `shared-infra/src/commonMain/kotlin/.../state/MutableStatefulFlow.kt`.
- **Navigation**: `shared-infra/src/commonMain/kotlin/.../navigation/Navigator.kt`.

## CONVENTIONS
- **State Envelopes**: Use `State<T, E>` for all async UI state (Created, Loading, Success, Error).
- **Flow Encapsulation**: Expose `ImmutableStateFlow` to consumers; keep `MutableStatefulFlow` internal.
- **Side Effects**: Use `emitLoading()` -> Action -> `emitSuccess()`/`emitError()`.

## ANTI-PATTERNS
- **Feature Logic**: Do NOT implement business logic here; infra must remain generic.
- **Heavy States**: Avoid storing large data objects in state flows; use repositories for caching.
- **Raw Flows**: Avoid exposing raw `MutableStateFlow` in public APIs.
