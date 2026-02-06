# BUILD-LOGIC CONVENTION KNOWLEDGE BASE

## OVERVIEW
Centralized Gradle configuration via `komodo.*` convention plugins. Avoids duplication across monorepo modules.

## STRUCTURE
- **Plugins**: Registered in `build-logic/convention/build.gradle.kts`.
- **Implementations**: Kotlin classes in `ca.glong.komodo` package.

## WHERE TO LOOK
- **Multiplatform Settings**: `KotlinMultiplatformConventionPlugin.kt`.
- **Compose Setup**: `ComposeConventionPlugin.kt`.
- **DI/Network**: `KoinConventionPlugin.kt`.
- **Helpers**: `ProjectExtensions.kt` (dependency bundles, implLib).

## CONVENTIONS
- **Single Source**: Always use the `komodo.*` plugin instead of raw upstream plugins.
- **Compose over Config**: Prefer enabling features via plugins rather than manual build script code.
- **Bundles**: Use dependency bundles from `ProjectExtensions` to keep versions aligned.

## ANTI-PATTERNS
- **Manual Config**: Do NOT duplicate sourceSets or compiler flags in module `build.gradle.kts`.
- **Version Hardcoding**: Avoid hardcoding versions in module files; use the convention constants.
- **Ad-hoc Flags**: Don't add custom flags if the `komodo` extension can handle it.
