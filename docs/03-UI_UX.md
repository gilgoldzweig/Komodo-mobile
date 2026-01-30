# UI/UX & Design System

## Design Philosophy
- **Material 3**: Using the latest Material Design guidelines.
- **Adaptive**: Responsive layouts that work on Phones (single pane) and Tablets/Desktop (master-detail optional).
- **Theme**: Dark mode by default (typical for dev tools).

## Screen Definitions

### 1. Server Setup (Login)
**Goal**: Connect the client to a Komodo instance.
- **Fields**: 
    - Server URL (e.g., `https://komodo.myserver.com`)
    - Username
    - Password / API Key
- **Actions**:
    - "Test Connection" (Validates reachability and auth)
    - "Connect" (Saves and navigates to Dashboard)

### 2. Dashboard (`DashboardScreen`)
**Goal**: High-level overview of the infrastructure.
- **Components**:
    - **Header**: Connected server name + connection status indicator.
    - **Stats Cards**: 
        - CPU Load (with mini sparkline chart)
        - RAM Usage
        - Active Containers / Total Containers
    - **Recent Alerts/Activity**: Short list of recent deployment events.

### 3. Stacks List (`StacksScreen`)
**Goal**: Browse and manage deployments.
- **Layout**: `LazyColumn` of Stack Cards.
- **Stack Card Item**:
    - Stack Name
    - Status Icon (Green/Red/Amber)
    - Service count badge
    - "More Options" menu (Restart, Stop, Delete)

### 4. Stack Detail (`StackDetailScreen`)
**Goal**: Deep dive into a specific stack.
- **Tabs**:
    - **Overview**: Service list, health status.
    - **Config**: View/Edit Compose file (read-only initially).
    - **Logs**: Aggregate logs (optional).

### 5. Log Viewer (Component)
**Goal**: Read container output efficiently.
- **Tech**: `LazyColumn` with highly optimized item rendering.
- **Features**:
    - "Follow" mode (auto-scroll to bottom).
    - Text search/filter.
    - ANSI color rendering (convert ANSI codes to Compose `SpanStyle`).

## Navigation Graph (Voyager)

```mermaid
graph TD
    Root[Root Navigator] --> Login[LoginScreen]
    Login --> Main[Main Shell (TabNavigator)]
    
    Main --> Dash[DashboardTab]
    Main --> Stacks[StacksTab]
    Main --> Settings[SettingsTab]
    
    Stacks --> Detail[StackDetailScreen]
    Detail --> Logs[LogViewerScreen]
```

## Implementation Tasks

- [ ] **Theme & Design System**
    - [ ] Define Color Palette (Komodo Brand).
    - [ ] Create `AppTheme` composable wrapper.
    - [ ] Create generic components: `KCard`, `KButton`, `KTextField`.

- [ ] **Login Flows**
    - [ ] Implement `ServerSetupScreen` UI.
    - [ ] Implement `LoginScreen` (Username/Pass).
    - [ ] Implement OAuth placeholders/webviews (GitHub/Google).

- [ ] **Dashboard**
    - [ ] Create `StatsCard` component.
    - [ ] Create `DashboardScreen` layout.
    - [ ] Connect `DashboardViewModel`.

- [ ] **Stacks & Services**
    - [ ] Create `StackListScreen` (LazyColumn).
    - [ ] Create `StackItem` card.
    - [ ] Create `StackDetailScreen` with TabRow (Overview, Config, Logs).

- [ ] **Utilities**
    - [ ] Implement Toast/Snackbar manager for errors.
