# API Specification & Networking

## Networking Client (`KomodoApiClient`)
The Komodo Core API uses a **RPC-style** pattern over HTTP POST requests, rather than standard REST. All requests are sent to specific endpoint categories (`/auth`, `/read`, `/write`, `/execute`) with a wrapped payload.

### Configuration
- **Base URL**: `https://<komodo_address>` (User configurable)
- **Method**: `POST` (Used for almost all standard API requests)
- **Content-Type**: `application/json`

### Authentication Headers
One of the following must be included:
- **JWT**: `Authorization: <token>` (Obtained via `/auth`)
- **API Key**: `X-Api-Key: <key>` AND `X-Api-Secret: <secret>`

---

## Request & Response Structure

### Request Wrapper
All API calls follow a standard wrapper pattern:
```json
{
  "type": "OperationName",
  "params": {
    "key": "value"
  }
}
```

### Error Handling
Non-200 status codes return a JSON body:
```json
{
  "error": "Short description of the error",
  "trace": ["Detailed traceback message 1", "Detailed traceback message 2"]
}
```

---

## API Paths & Methods

### 1. Authentication (`/auth`)
Discovery and Session management.

| Request Type | Params | Response |
| :--- | :--- | :--- |
| **GetLoginOptions** | `{}` | `GetLoginOptionsResponse` |
| **LoginLocalUser** | `{ username, password }` | `JwtResponse` |
| **SignUpLocalUser** | `{ username, password }` | `JwtResponse` |
| **ExchangeForJwt** | `{ token }` | `JwtResponse` |
| **GetUser** | `{}` | `User` |

**DTO: GetLoginOptionsResponse**
```kotlin
@Serializable
data class GetLoginOptionsResponse(
    val local: Boolean,
    val github: Boolean,
    val google: Boolean,
    val oidc: Boolean,
    val registration_disabled: Boolean
)
```

### 2. Data Retrieval (`/read`)
Read-only access to system state.

| Request Type | Description | Response |
| :--- | :--- | :--- |
| **ListServers** | List all servers. | `List<ServerListItem>` |
| **GetServer** | Get full config. | `Server` |
| **ListDeployments** | List container deployments. | `List<DeploymentListItem>` |
| **GetSystemStats** | Live CPU/Mem/Disk stats. | `SystemStats` |
| **GetUpdate** | Retrieve status/logs for execution ID. | `Update` |
| **ListAlerts** | Get paginated alerts. | `ListAlertsResponse` |

### 3. Resource Management (`/write`)
CRUD operations. Usually merges partial config updates.
- **Resources**: `Server`, `Stack`, `Deployment`, `Build`, `Repo`, `Procedure`, `Action`, `UserGroup`, `Variable`.
- **Methods**: `Create...`, `Update...`, `Delete...`, `Rename...` 
    - *Example*: `CreateServer`, `UpdateStack`, `DeleteDeployment`.

### 4. Execution (`/execute`)
Triggers asynchronous operational tasks. Returns an `Update` object immediately.

| Request Type | Description |
| :--- | :--- |
| **Deploy** | Deploy/Redeploy a container. |
| **RunBuild** | Start an image build. |
| **DeployStack** | Run docker compose up. |
| **PruneSystem** | Clean up Docker resources. |
| **RunProcedure** | Run a sequence of stages. |

---

## Execution Pattern (Polling)
For `/execute` commands:
1.  **POST** to `/execute` (e.g., `RunBuild`).
2.  Receive `Update` DTO.
    - If `status` is `InProgress` or `Queued`, store `_id.$oid`.
3.  **Poll** `/read/GetUpdate` with the ID every 1-2 seconds.
4.  **Finish**: When `status` is `Complete`, inspect `success` (boolean) and `logs` array.

---

## WebSockets & Streams

### Updates WebSocket
- **URL**: `ws://<address>/ws/update`
- **Auth**: Send initial frame: `{ "type": "Jwt", "params": { "jwt": "..." } }`
- **Ack**: Wait for `"LOGGED_IN"` message.
- **Feed**: Receives `UpdateListItem` JSON objects on any resource change.

### Terminal / Shell WebSockets
Used for interactive shell access. Protocol switches to binary (arraybuffer) after login.
- **Server Shell**: `/ws/terminal?server=<id>&terminal=<name>`
- **Container Shell**: `/ws/container/terminal?server=<id>&container=<name>&shell=bash`

### Operational Streams
One-off command output streams.
- **Success Marker**: `__KOMODO_EXIT_CODE:0`
- **Failure Marker**: `__KOMODO_EXIT_CODE:1`

---

## Data Models (Common)

### Core Types
```kotlin
@Serializable
data class MongoId(@SerialName("$oid") val oid: String)

@Serializable
data class ResourceTarget(val type: String, val id: String)

@Serializable
data class Update(
    val _id: MongoId,
    val status: String, // InProgress, Queued, Complete
    val success: Boolean?,
    val logs: List<String>
)
```

## Implementation Tasks

- [ ] **Network Layer**
    - [ ] Create `KomodoRpcClient` generic wrapper.
    - [ ] Implement `post<T>(type: String, params: Any): T` helper.
    - [ ] Handle `error`/`trace` JSON responses in exception mapping.

- [ ] **Auth Implementation**
    - [ ] Implement `GetLoginOptions` flow.
    - [ ] Implement `LoginLocalUser` and token storage.

- [ ] **Data Implementation**
    - [ ] Implement `ListServers`, `ListDeployments`, `GetSystemStats` (RPC 'read').
    - [ ] Implement `GetUpdate` polling mechanism.

- [ ] **WebSocket Implementation**
    - [ ] Implement `/ws/update` listener for global state changes.

