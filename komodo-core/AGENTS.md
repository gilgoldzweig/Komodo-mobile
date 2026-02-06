# KOMODO-CORE KNOWLEDGE BASE

## OVERVIEW
komodo-core is the shared UI and app wiring module — the Compose-based entry point and aggregator of feature modules. It holds the common App() root, platform entry wiring (Android/iOS glue), core navigation primitives, and the central Koin registration.

## STRUCTURE
- **App entry**: Shared Composable root and bootstrapping logic.
- **DI**: Central Koin registration and platform helpers for iOS/Android.
- **Navigation**: Core NavKey serializer and CoreNavigationModule mapping.
- **UI shells**: Launcher, Dashboard, and Login screens.

## WHERE TO LOOK
- **App entry**: `komodo-core/src/commonMain/kotlin/ca/glong/komodo/App.kt`
- **Koin DI**: `komodo-core/src/commonMain/kotlin/ca/glong/komodo/di/AppModule.kt`
- **Navigation wiring**: `komodo-core/src/commonMain/kotlin/ca/glong/komodo/navigation/CoreNavigationModule.kt`
- **iOS Wiring**: `komodo-core/src/iosMain/kotlin/ca/glong/komodo/MainViewController.kt`

## CONVENTIONS
- **Host Only**: Keep feature logic in `:impl` modules; `komodo-core` only wires them.
- **Navigation**: Use `NavKeySerializer` for persistence; do not hardcode routes.
- **DI**: Features must expose a module provider function; `komodo-core` aggregates them.

## ANTI-PATTERNS
- **Feature Logic**: Do NOT place feature business logic or repositories here.
- **Direct Impl Deps**: Avoid direct dependencies on feature-impl modules where possible; use DI.
- **Hardwired Nav**: Don't map navigation directly to implementation classes; use NavKeys.
