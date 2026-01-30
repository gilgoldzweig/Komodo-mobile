# Komodo Native Client - Master Plan

## Project Overview
A native, cross-platform mobile client for [Komodo](https://github.com/moghtech/komodo), built using Kotlin Multiplatform (KMP) and Compose Multiplatform. This client aims to provide full management capabilities for container fleets, including monitoring, deployment management, and real-time logging.

## Core Technologies
- **Language**: Kotlin (Multiplatform)
- **UI Framework**: Compose Multiplatform
- **Concurrency**: Kotlin Coroutines & Flow
- **Networking**: Ktor Client (REST & WebSockets)
- **Dependency Injection**: Koin
- **Navigation**: Voyager
- **Local Storage**: Multiplatform Settings
- **Serialization**: Kotlinx Serialization

## Roadmap

### Phase 1: Foundation & Infrastructure
*Detailed plan: [01-ARCHITECTURE.md](./01-ARCHITECTURE.md)*
- [x] Set up build dependencies (`libs.versions.toml`).
- [x] Configure `KomodoApiClient` with Ktor.
- [x] Implement Dependency Injection (Koin) structure.
- [x] Set up Navigation (Voyager).
- [x] Implement Settings Repository for secure credential storage.

### Phase 2: Authentication & Core
*Detailed specs: [02-API_SPEC.md](./02-API_SPEC.md) & [03-UI_UX.md](./03-UI_UX.md)*
- [x] Create `ServerSetupScreen` (URL, User, Password).
- [x] Implement "Test Connection" functionality.
- [x] Persist session tokens.
- [ ] Build Main Dashboard with real-time stats (WebSocket/Polling).

### Phase 3: Resource Management
*Detailed specs: [03-UI_UX.md](./03-UI_UX.md)*
- [ ] **Stacks**: List, Detail, and Configuration views.
- [ ] **Services**: Start, Stop, Restart actions.
- [ ] **Deployments**: History and status tracking.

### Phase 4: Observability
*Detailed plan: [04-OBSERVABILITY.md](./04-OBSERVABILITY.md)*
- [ ] **Log Viewer**: High-performance streaming log component (WebSockets).
- [ ] **System Stats**: Charts for CPU/RAM usage.

### Phase 5: Polish & Release
*Detailed plan: [05-POLISH_AND_RELEASE.md](./05-POLISH_AND_RELEASE.md)*
- [ ] Error handling & empty states.
- [ ] Theming & Dark Mode support.
- [ ] iOS specific refinements.
