---
description: Description of the custom chat mode.
tools: ['insert_edit_into_file', 'replace_string_in_file', 'create_file', 'run_in_terminal', 'get_terminal_output', 'get_errors', 'show_content', 'open_file', 'list_dir', 'read_file', 'file_search', 'grep_search', 'validate_cves', 'run_subagent', 'semantic_search']
---
# Architecture & Development Agents: KMM + Navigation3 + Koin Annotations

This document defines the mandatory standards for developing features within this Kotlin Multiplatform Mobile (KMM) project. We utilize **Koin Annotations** and **KSP** for compile-time safety and the **Navigation 3** strategy for modularity.

## 1. Modular Structure (API/IMPL Split)

Every feature must be strictly divided into two Gradle modules to enable type-safe, decoupled navigation.

### `:feature:[name]:api`
* **Purpose**: Contract and Navigation.
* **Navigation Keys**: Define `@Serializable` keys that implement `NavKey`.
* **Dependencies**: Only `androidx.navigation3:navigation3-runtime`.

### `:feature:[name]:impl`
* **Purpose**: Logic and UI.
* **Annotations**: Use `@Module`, `@Single`, `@Factory`, and `@KoinViewModel`.
* **KSP**: The implementation module must apply the KSP plugin to generate Koin DSL.

---

## 2. Koin Annotations Strategy

We avoid manual `module { ... }` blocks in favor of annotations.

### ViewModel Declaration
Use `@KoinViewModel` in the implementation module's `commonMain`.

```kotlin
@KoinViewModel
class DetailViewModel(
    private val repository: DetailRepository,
    // Navigation 3 arguments can be passed via parametersOf in the UI
    private val id: String 
) : ViewModel()

```

### Module Definition & Component Scan

Each feature implementation should have a module class that scans its own package.

```kotlin
@Module
@ComponentScan("com.project.feature.detail")
class DetailModule

```

### Generated Access

To include these in the app, use the generated `.module` property.

```kotlin
// In the main App entry point
startKoin {
    modules(
        DetailModule().module,
        HomeModule().module
    )
}

```

---

## 3. Navigation 3 + Koin Integration

We use the state-driven backstack where keys are defined in the `api` module and resolved in the `impl` module.

### Route Definition (api)

```kotlin
@Serializable
data class ProfileKey(val userId: String) : NavKey

```

### Entry Provider (impl)

In the implementation module, define how the key maps to a screen. Use `koinViewModel` to inject the annotated ViewModel.

```kotlin
fun EntryProviderScope<NavKey>.featureProfileEntries() {
    entry<ProfileKey> { key ->
        // Inject ViewModel with the navigation argument 'userId'
        val viewModel = koinViewModel<ProfileViewModel> { 
            parametersOf(key.userId) 
        }
        ProfileScreen(viewModel)
    }
}

```

---

## 4. Platform-Specific Dependencies

For dependencies requiring `actual` implementations (like local databases or engine factories), use the annotation on the `actual` class.

**commonMain:**

```kotlin
@Module
expect class PlatformModule()

```

**androidMain:**

```kotlin
@Module
actual class PlatformModule {
    @Single
    fun provideDriver(context: Context): SqlDriver = ...
}

```

---

## 5. Agent Checklist

1. **Annotate**: Ensure all classes intended for DI are marked (`@Single`, `@Factory`, `@KoinViewModel`).
2. **Scan**: Ensure your feature has a `@Module` class with `@ComponentScan`.
3. **Key**: Ensure the `NavKey` is `@Serializable`.
4. **Wiring**: Add the new `FeatureModule().module` to the `startKoin` configuration in the `:app` module.
5. **Build**: Run `./gradlew kspKotlinCommonMain` (or similar) if you encounter unresolved references to generated modules.