---
applyTo: '**'
description: 'This raw markdown plan is designed to be ingested by an AI coding agent. It follows a strict **Test-Driven Development (TDD)** approach and adheres to the **API/IMPL split** modular architecture and **Navigation 3** strategy defined in `agents.md`.
'
---

---

# 🦎 Project Plan: Komodo Mobile Native (Kotlin Multiplatform)

## 🎯 High-Level Instructions for Agent

1. **Strict TDD**: For every feature, you must write the unit test (behavior/logic) or widget/ui test **before** writing the implementation.
2. **Modular Mandate**: Every feature must reside in two modules: `:feature:[name]:api` (Navigation Keys) and `:feature:[name]:impl` (Logic/UI).
3. **DI Requirement**: Use Metro
4. **Completion Rule**: A task/subtask is **only** complete once `./gradlew test` passes for the relevant modules.
5. **No Knowledge Assumption**: Schemas for every model and API contract are provided below. Use these as the source of truth.

---

## 🏗️ Phase 1: Core Infrastructure (`:core:network` & `:core:domain`)

Establish the networking, serialization, and base domain models translated from the backend orchestration logic.

### 1.1 Base Domain Models (`:core:domain`)

* [ ] Create `@Serializable` domain models.
* [ ] **Task**: Define `Resource` and `Deployment` entities.
* [ ] **Schema**:
```kotlin
@Serializable data class Deployment(
    val id: String, 
    val name: String, 
    val status: String, // "running", "stopped", "error"
    val image: String,
    val last_updated: Long
)

```




* [ ] **Task**: Define `Server` and `Stack` entities.
* [ ] **Schema**:
```kotlin
@Serializable data class Server(val id: String, val name: String, val address: String, val status: String)
@Serializable data class Stack(val id: String, val name: String, val services: List<String>)

```





### 1.2 Networking & Auth Interceptor (`:core:network`)

* [ ] **Task**: Configure Ktor Client with JSON and Logging.
* [ ] **Task**: Implement `TokenManager` using `MultiplatformSettings`.
* [ ] **Task**: Implement `AuthInterceptor` to append `Authorization: Bearer <token>` to all requests.
* [ ] **Test**: Write a test verifying that `AuthInterceptor` adds the token if present in `TokenManager`.

---

## 🔐 Phase 2: Feature: Authentication (`:feature:auth`)

### 2.1 API Module (`:feature:auth:api`)

* [ ] **Task**: Define `LoginKey`.
* [ ] **Schema**: `@Serializable object LoginKey : NavKey`



### 2.2 Implementation Module (`:feature:auth:impl`)

* [ ] **Test**: Write `AuthRepositoryTest` mocking a `401` and a successful `200` login.
* [ ] **Task**: Implement `AuthRepository` with `POST /api/auth/login`.
* [ ] **Schema**: Request body `data class LoginRequest(val username: String, val password: String)`


* [ ] **Test**: Write `LoginViewModelTest` verifying state changes from `Idle` -> `Loading` -> `Authenticated`.
* [ ] **Task**: Implement `LoginViewModel` with `@KoinViewModel`.
* [ ] **Task**: Create `LoginScreen` using Compose Multiplatform.

---

## 📊 Phase 3: Feature: Dashboard (`:feature:dashboard`)

### 3.1 API Module (`:feature:dashboard:api`)

* [ ] **Task**: Define `DashboardKey`.

### 3.2 Implementation Module (`:feature:dashboard:impl`)

* [ ] **Test**: Write `DashboardViewModelTest` verifying stats are fetched on init.
* [ ] **Task**: Implement `DashboardRepository` with `GET /api/read/stats`.
* [ ] **Schema**: `data class GlobalStats(val total_containers: Int, val active_deployments: Int, val server_count: Int)`


* [ ] **Task**: Implement `DashboardViewModel` and `DashboardScreen`.
* [ ] **Task**: Register `featureDashboardEntries()` in the `EntryProviderScope`.

---

## 📦 Phase 4: Feature: Resource Management (`:feature:resources`)

This is the primary module for managing Servers, Stacks, and Deployments.

### 4.1 API Module (`:feature:resources:api`)

* [ ] **Task**: Define `ResourceListKey` and `ResourceDetailKey(val id: String, val type: ResourceType)`.

### 4.2 Resource List Implementation (`:feature:resources:impl`)

* [ ] **Test**: Write test for fetching all resources merged into a single list.
* [ ] **Task**: Implement `ResourceRepository` calling `GET /api/read/resource/all`.
* [ ] **Task**: Build `ResourceListScreen` with tabs for [Servers, Stacks, Deployments].

### 4.3 Deployment Actions Implementation

* [ ] **Test**: Write test for triggering a deployment and receiving a success status.
* [ ] **Task**: Implement `POST /api/execute/deployment/deploy/{id}`.
* [ ] **Task**: Build `DeploymentDetailScreen` showing status and "Redeploy" button.

---

## 🔌 Phase 5: Real-time Communication (WebSockets)

Komodo relies on WebSockets for real-time logs and terminal access.

### 5.1 WebSocket Service (`:core:network`)

* [ ] **Task**: Implement `WebSocketManager` using Ktor `webSocket`.
* [ ] **Schema**: `fun observeLogs(deploymentId: String): Flow<String>`


* [ ] **Test**: Mock a server sending log frames and verify the `Flow` emits correctly.

### 5.2 Terminal Component (`:feature:resources:impl`)

* [ ] **Task**: Create a `TerminalScreen` mapped to `TerminalKey(val serverId: String)`.
* [ ] **Task**: Connect to `ws://{host}/api/ws/server/{id}/terminal`.

---

## 🚨 Phase 6: Feature: Alerts & Monitoring (`:feature:alerts`)

### 6.1 Alert List

* [ ] **Task**: Define `AlertsKey : NavKey`.
* [ ] **Test**: Verify alert history is parsed correctly from JSON.
* [ ] **Task**: Implement `GET /api/read/alert` fetching historical alert events.
* [ ] **Task**: Create `AlertsScreen` showing a list of recent failures.

---

## 📱 Phase 7: App Integration (`:app`)

### 7.1 Navigation 3 Setup

* [ ] **Task**: Implement `MainNavigation` using `NavStack`.
* [ ] **Task**: Set up `startKoin` in `commonMain` to include all feature modules.
* [ ] **Task**: Implement the `Omnibar` (Global Search) as a floating overlay.

---

## 🛠️ API Reference Table (For Agent Implementation)

| Endpoint | Method | Entity Mapping | Module |
| --- | --- | --- | --- |
| `/api/auth/login` | POST | `AuthResponse` | `:feature:auth` |
| `/api/read/resource/all` | GET | `List<Resource>` | `:feature:resources` |
| `/api/read/server/{id}/info` | GET | `ServerInfo` | `:feature:resources` |
| `/api/execute/deployment/deploy/{id}` | POST | `ActionStatus` | `:feature:resources` |
| `/api/ws/stats` | WS | `LiveStats` | `:feature:dashboard` |
| `/api/ws/deployment/{id}/log` | WS | `String` (Stream) | `:feature:resources` |

## 🏗️ Class Template (Example for Agent)

**Feature Module Structure:**

```kotlin
// feature-resources-impl/.../ResourceModule.kt
@Module
@ComponentScan("com.komodo.feature.resources")
class ResourceModule

// feature-resources-impl/.../ResourceViewModel.kt
@KoinViewModel
class ResourceViewModel(
    private val repository: ResourceRepository
) : ViewModel() {
    private val _state = MutableStateFlow<ResourceState>(ResourceState.Loading)
    val state = _state.asStateFlow()
    
    fun load() { /* TDD Implementation */ }
}

```

---

## 🚦 Final Confirmation for Agent

Before moving from one Phase to the next, run:
`./gradlew check`
**Only proceed if build is successful and all tests pass.**