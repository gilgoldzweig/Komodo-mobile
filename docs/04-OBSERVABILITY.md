# Observability & Monitoring

This phase focuses on the "real-time" aspect of the Komodo client, specifically **Log Streaming** and **System Statistics**.

## 1. Log Viewer
The log viewer is a critical component for debugging. In Komodo Core, logs are typically part of an `Update` object (during execution) or accessed via specific endpoints.

### Technical Approach
- **Execution Logs**: when running a task (like `Deploy`), we receive an `Update` ID.
- **Pattern**: Polling `/read/GetUpdate` every 1-2s. The response contains a `logs` list.
- **State Management**:
    - Append new logs from the `logs` array to the UI state.
    - Handle `status: "Complete"` or `Success/Failure` markers.
- **Historical/Container Logs**:
    - For *running* containers, we likely use `GetDeployment` or `GetUpdate` history, or the interactive Shell WebSocket if a live PTY is needed.
    - *Assumption*: Primary "log viewing" for deployments uses the standard `logs` field in `Deployment` or `Update` DTOs.

### UI Components
- **Log Toolbar**:
    - [Search/Filter] input field.
    - [Pause/Resume] toggle (stops polling).
    - [Clear] button.
    - [Top/Bottom] jump buttons.
- **Log Row**: Monospaced font, dense height.
- **ANSI Parsing**: Essential for colored output.

## 2. System Statistics
Visualizing server resource usage.

### Technical Approach
- **Data Source**: `/read/GetSystemStats` RPC call.
- **Polling**: Since these are live stats, we poll this endpoint (e.g., every 5s) or use the general `/ws/update` if it emits stat changes (specification implies general resource updates, explicitly `GetSystemStats` is under `/read`).
- **Metrics**:
    - CPU Usage %
    - RAM Usage (Used / Total)
    - Disk Usage

### Visualization
- **Sparklines**: Simple path drawing on a Canvas for the last 60 seconds of history.
- **Gauges**: Circular progress indicators for current load.

## Implementation Tasks

- [ ] **Log Engine**
    - [ ] Create `LogRepository` that handles `GetUpdate` polling.
    - [ ] Implement ANSI-to-AnnotatedString parser.
    - [ ] Implement `CircularBuffer` for log storage.

- [ ] **Log UI**
    - [ ] Build `LogViewer` composable.
    - [ ] Implement "Auto-scroll to bottom" logic.

- [ ] **Stats Engine**
    - [ ] Create `StatsRepository`.
    - [ ] Implement polling mechanism for `GetSystemStats`.

- [ ] **Stats UI**
    - [ ] Create `ResourceChart` composable (Canvas based).
    - [ ] Integrate charts into `DashboardScreen`.
