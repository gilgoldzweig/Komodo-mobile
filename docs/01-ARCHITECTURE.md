# Architecture & Technical Stack

## High-Level Architecture
The application follows a **Clean Architecture** approach adapted for KMP, separating concerns into three main layers:

`Data` -> `Domain` -> `UI`

### 1. Data Layer (`commonMain`)
Responsible for raw data retrieval and persistence.
- **Networking**: `Ktor` client handling HTTP POST (RPC) and WebSocket connections.
- **DTOs**: `kotlinx.serialization` annotated classes matching the Rust API.
- **Persistence**: `Multiplatform Settings` for key-value storage (Auth tokens, Server URL).
- **Repositories**: specific implementations masking the data source specifics.

### 2. Domain Layer (`commonMain`)
Contains business logic and pure data models.
- **Models**: Clean, UI-agnostic data classes.
- **UseCases (Optional)**: If logic gets complex, specific UseCases (e.g., `ConnectToServerUseCase`) will be extracted.
- **Repository Interfaces**: Definitions of data contracts.

### 3. UI Layer (`commonMain` + Platform Specifics)
Driven by ViewModels and Compose.
- **ViewModels**: `androidx-lifecycle-viewmodel` or custom Voyager `ScreenModel`. We will use standard `ViewModel` + `StateFlow` pattern.
- **State Management**: Unidirectional Data Flow (UDF).
  - `Event` (User Action) -> `ViewModel` -> `State` (StateFlow) -> `UI` (Compose)
- **Navigation**: `Voyager` library for robust stack and tab navigation.

---

## Dependency Injection (Koin)
We will use Koin for Service Locator / DI.

### Modules
- `networkModule`: Provides `HttpClient` instances.
- `platformModule`: Platform-specific dependencies (e.g., Settings factory).
- `repositoryModule`: Binds Repositories.
- `viewModelModule`: Binds ViewModels.

```kotlin
val appModule = module {
    single { createHttpClient() }
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    viewModel { DashboardViewModel(get()) }
}
```

## Navigation Structure
We will use **Voyager**.

- **Main Navigation**: `Navigator(LoginScreen)`
- **Authenticated Shell**: `TabNavigator`
  - `DashboardTab`
  - `StacksTab`
  - `SettingsTab`

## Concurrency
- **Coroutines**: Primary mechanism for async work.
- **Structured Concurrency**: All `launch` calls scoped to `viewModelScope`.
- **Flows**: Used for observing data changes (WebSockets, Settings).

## Implementation Tasks

- [x] **Dependency Setup**
    - [x] Add `koin-core` and `koin-compose` to `libs.versions.toml`.
    - [x] Add `voyager-navigator` and `voyager-tab-navigator` to `libs.versions.toml`.
    - [x] Add `kotlinx-coroutines-core` to `libs.versions.toml`.
    - [x] Configure `build.gradle.kts` with new dependencies.

- [x] **Core Structure**
    - [x] Create `di` package and `Koin.kt` setup file.
    - [x] Define `appModule`, `networkModule`, `uiModule`.
    - [x] Initialize Koin in `BeforeApp` or Platform equivalent.

- [x] **Navigation Setup**
    - [x] Create `AppHelper` or `App` entry point using Voyager `Navigator`.
    - [x] Create `Route` sealed classes or just use Screen objects to define paths.

- [x] **Base Classes**
    - [x] Create `BaseViewModel` (if using custom VM implementation).
    - [x] Create generic `Resource/Result` state wrapper for UI.
