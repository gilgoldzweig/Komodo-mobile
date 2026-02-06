# Plan 002: Core Domain DTOs

## Overview
Define all data transfer objects (DTOs) required to interact with the Komodo API. These models represent the contract between the API and the application, enabling type-safe serialization/deserialization.

## Prerequisites
- Plan 001 (Networking Infrastructure) - for error types reference

## Depends On
- `core-domain` module (exists, empty)
- OpenAPI.json schema definitions (lines 11715+)

## Deliverables

### 1. Base Types & Common Patterns

**Files to Create in `core-domain/src/commonMain/kotlin/ca/glong/komodo/core/domain/model/`:**

#### 1.1 Common Types
```kotlin
// common/ResourceId.kt
@JvmInline
value class ResourceId(val value: String)

// common/Timestamp.kt  
@JvmInline
value class Timestamp(val epochMillis: Long)

// common/I64.kt - API uses i64 for some numeric fields
typealias I64 = Long

// common/MongoId.kt
typealias MongoId = String
```

#### 1.2 Permission Types
```kotlin
// permission/PermissionLevel.kt
@Serializable
enum class PermissionLevel {
    @SerialName("none") NONE,
    @SerialName("read") READ,
    @SerialName("execute") EXECUTE,
    @SerialName("write") WRITE
}

// permission/UserTarget.kt
@Serializable
sealed class UserTarget {
    @Serializable @SerialName("User")
    data class User(val params: UserParams) : UserTarget()
    @Serializable @SerialName("UserGroup")  
    data class UserGroup(val params: UserGroupParams) : UserTarget()
}
```

### 2. Resource State Enums

**Files to Create in `core-domain/.../model/state/`:**

| File | Enum Values |
|------|-------------|
| `ServerState.kt` | `Ok`, `Disabled`, `NotOk`, `Unknown` |
| `DeploymentState.kt` | `Deploying`, `Running`, `Created`, `Restarting`, `Removing`, `Paused`, `Exited`, `Dead`, `Unhealthy`, `NotDeployed`, `Unknown` |
| `StackState.kt` | `Running`, `Paused`, `Stopped`, `Restarting`, `Removing`, `Dead`, `Unknown` |
| `BuildState.kt` | `Unknown`, `None`, `Queued`, `Building`, `Ok`, `Failed` |
| `RepoState.kt` | `Unknown`, `Ok`, `Cloning`, `Pulling`, `Building`, `Failed` |
| `ProcedureState.kt` | `Unknown`, `Ok`, `Running`, `Failed` |
| `ActionState.kt` | `Unknown`, `Ok`, `Running`, `Failed` |
| `SyncState.kt` | `Unknown`, `Ok`, `Syncing`, `Failed`, `Pending` |
| `ContainerStateStatus.kt` | `Running`, `Created`, `Paused`, `Restarting`, `Exited`, `Removing`, `Dead`, `Empty` |

**Pattern for all state enums:**
```kotlin
@Serializable
enum class DeploymentState {
    @SerialName("deploying") Deploying,
    @SerialName("running") Running,
    // ... etc
}
```

### 3. Core Resource Types

**Files to Create in `core-domain/.../model/resource/`:**

#### 3.1 Server
```kotlin
// server/Server.kt
@Serializable
data class Server(
    @SerialName("_id") val id: MongoId? = null,
    val name: String,
    val description: String = "",
    val tags: List<String> = emptyList(),
    val template: String? = null,
    @SerialName("updated_at") val updatedAt: Timestamp? = null,
    @SerialName("base_permission") val basePermission: PermissionLevel = PermissionLevel.NONE,
    val config: ServerConfig,
    val info: ServerInfo? = null
)

// server/ServerConfig.kt
@Serializable
data class ServerConfig(
    val address: String,
    val region: String = "",
    val enabled: Boolean = true,
    @SerialName("send_alerts") val sendAlerts: Boolean = true,
    @SerialName("cpu_alert") val cpuAlert: Double = 90.0,
    @SerialName("mem_alert") val memAlert: Double = 90.0,
    @SerialName("disk_alert") val diskAlert: Double = 90.0
)

// server/ServerInfo.kt
@Serializable
data class ServerInfo(
    val state: ServerState,
    val stats: ServerStats? = null
)

// server/ServerStats.kt
@Serializable
data class ServerStats(
    @SerialName("cpu_perc") val cpuPerc: Double,
    @SerialName("mem_perc") val memPerc: Double,
    @SerialName("mem_used_gb") val memUsedGb: Double,
    @SerialName("mem_total_gb") val memTotalGb: Double,
    @SerialName("disk_perc") val diskPerc: Double,
    @SerialName("disk_used_gb") val diskUsedGb: Double,
    @SerialName("disk_total_gb") val diskTotalGb: Double
)

// server/ServerListItem.kt
@Serializable
data class ServerListItem(
    val id: MongoId,
    val name: String,
    val tags: List<String>,
    val state: ServerState,
    @SerialName("region") val region: String = "",
    @SerialName("send_alerts") val sendAlerts: Boolean = true
)
```

#### 3.2 Deployment
```kotlin
// deployment/Deployment.kt
@Serializable
data class Deployment(
    @SerialName("_id") val id: MongoId? = null,
    val name: String,
    val description: String = "",
    val tags: List<String> = emptyList(),
    val template: String? = null,
    @SerialName("updated_at") val updatedAt: Timestamp? = null,
    @SerialName("base_permission") val basePermission: PermissionLevel = PermissionLevel.NONE,
    val config: DeploymentConfig,
    val info: DeploymentInfo? = null
)

// deployment/DeploymentConfig.kt
@Serializable
data class DeploymentConfig(
    @SerialName("server_id") val serverId: MongoId = "",
    @SerialName("swarm_id") val swarmId: MongoId? = null,
    val image: DeploymentImage? = null,
    @SerialName("image_registry_account") val imageRegistryAccount: String = "",
    val replicas: Int? = null,
    val restart: RestartMode = RestartMode.UNLESS_STOPPED,
    val network: String = "",
    val command: String = "",
    @SerialName("extra_args") val extraArgs: List<String> = emptyList(),
    val ports: List<Conversion> = emptyList(),
    val volumes: List<Conversion> = emptyList(),
    val environment: List<EnvironmentVar> = emptyList(),
    val labels: List<EnvironmentVar> = emptyList(),
    @SerialName("send_alerts") val sendAlerts: Boolean = true,
    @SerialName("auto_update") val autoUpdate: Boolean = false,
    @SerialName("poll_for_updates") val pollForUpdates: Boolean = false,
    @SerialName("redeploy_on_build") val redeployOnBuild: Boolean = false,
    val links: List<String> = emptyList(),
    @SerialName("skip_secret_interp") val skipSecretInterp: Boolean = false,
    @SerialName("termination_signal") val terminationSignal: TerminationSignal = TerminationSignal.SIGTERM,
    @SerialName("termination_timeout") val terminationTimeout: Int = 10,
    @SerialName("term_signal_labels") val termSignalLabels: Boolean = false
)

// deployment/DeploymentImage.kt - oneOf discriminated union
@Serializable
sealed class DeploymentImage {
    @Serializable @SerialName("Image")
    data class Image(val params: ImageParams) : DeploymentImage()
    
    @Serializable @SerialName("Build")
    data class Build(val params: BuildParams) : DeploymentImage()
}

@Serializable
data class ImageParams(val image: String)

@Serializable
data class BuildParams(
    @SerialName("build_id") val buildId: MongoId,
    val version: String? = null
)

// deployment/DeploymentInfo.kt
@Serializable
data class DeploymentInfo(
    val state: DeploymentState,
    @SerialName("container") val container: ContainerSummary? = null
)

// deployment/DeploymentListItem.kt
@Serializable
data class DeploymentListItem(
    val id: MongoId,
    val name: String,
    val tags: List<String>,
    val state: DeploymentState,
    @SerialName("server_id") val serverId: MongoId,
    val image: String = ""
)
```

#### 3.3 Stack
```kotlin
// stack/Stack.kt
@Serializable
data class Stack(
    @SerialName("_id") val id: MongoId? = null,
    val name: String,
    val description: String = "",
    val tags: List<String> = emptyList(),
    val template: String? = null,
    @SerialName("updated_at") val updatedAt: Timestamp? = null,
    @SerialName("base_permission") val basePermission: PermissionLevel = PermissionLevel.NONE,
    val config: StackConfig,
    val info: StackInfo? = null
)

// stack/StackConfig.kt
@Serializable
data class StackConfig(
    @SerialName("server_id") val serverId: MongoId = "",
    @SerialName("run_directory") val runDirectory: String = "",
    @SerialName("file_paths") val filePaths: List<String> = emptyList(),
    @SerialName("registry_account") val registryAccount: String = "",
    @SerialName("git_provider") val gitProvider: String = "",
    @SerialName("git_account") val gitAccount: String = "",
    @SerialName("repo") val repo: String = "",
    @SerialName("branch") val branch: String = "",
    @SerialName("commit") val commit: String = "",
    val environment: List<EnvironmentVar> = emptyList(),
    @SerialName("extra_args") val extraArgs: List<String> = emptyList(),
    @SerialName("send_alerts") val sendAlerts: Boolean = true,
    @SerialName("ignore_services") val ignoreServices: List<String> = emptyList()
)

// stack/StackInfo.kt
@Serializable
data class StackInfo(
    val state: StackState,
    val services: List<StackServiceState> = emptyList()
)

// stack/StackServiceState.kt
@Serializable
data class StackServiceState(
    val service: String,
    val state: DeploymentState,
    val container: ContainerSummary? = null
)

// stack/StackListItem.kt
@Serializable
data class StackListItem(
    val id: MongoId,
    val name: String,
    val tags: List<String>,
    val state: StackState,
    @SerialName("server_id") val serverId: MongoId,
    @SerialName("service_count") val serviceCount: Int = 0
)
```

#### 3.4 Build
```kotlin
// build/Build.kt
@Serializable
data class Build(
    @SerialName("_id") val id: MongoId? = null,
    val name: String,
    val description: String = "",
    val tags: List<String> = emptyList(),
    val template: String? = null,
    @SerialName("updated_at") val updatedAt: Timestamp? = null,
    @SerialName("base_permission") val basePermission: PermissionLevel = PermissionLevel.NONE,
    val config: BuildConfig,
    val info: BuildInfo? = null
)

// build/BuildConfig.kt
@Serializable
data class BuildConfig(
    @SerialName("builder_id") val builderId: MongoId = "",
    @SerialName("git_provider") val gitProvider: String = "",
    @SerialName("git_account") val gitAccount: String = "",
    val repo: String = "",
    val branch: String = "main",
    val commit: String = "",
    val dockerfile: String = "Dockerfile",
    @SerialName("build_path") val buildPath: String = ".",
    @SerialName("image_registry") val imageRegistry: ImageRegistry = ImageRegistry.None(Unit),
    @SerialName("image_name") val imageName: String = "",
    @SerialName("build_args") val buildArgs: List<EnvironmentVar> = emptyList(),
    @SerialName("secret_args") val secretArgs: List<EnvironmentVar> = emptyList(),
    val labels: List<EnvironmentVar> = emptyList(),
    @SerialName("extra_args") val extraArgs: List<String> = emptyList()
)

// build/BuildInfo.kt
@Serializable
data class BuildInfo(
    val state: BuildState,
    val version: String = "0.0.0"
)

// build/BuildListItem.kt
@Serializable
data class BuildListItem(
    val id: MongoId,
    val name: String,
    val tags: List<String>,
    val state: BuildState,
    @SerialName("builder_id") val builderId: MongoId,
    val version: String = "0.0.0"
)

// build/ImageRegistry.kt - oneOf
@Serializable
sealed class ImageRegistry {
    @Serializable @SerialName("None")
    data class None(val params: Unit) : ImageRegistry()
    
    @Serializable @SerialName("DockerHub")
    data class DockerHub(val params: DockerHubParams) : ImageRegistry()
    
    @Serializable @SerialName("Ghcr")
    data class Ghcr(val params: GhcrParams) : ImageRegistry()
    
    @Serializable @SerialName("Custom")
    data class Custom(val params: CustomRegistryParams) : ImageRegistry()
}
```

#### 3.5 Builder
```kotlin
// builder/Builder.kt
@Serializable
data class Builder(
    @SerialName("_id") val id: MongoId? = null,
    val name: String,
    val description: String = "",
    val tags: List<String> = emptyList(),
    val template: String? = null,
    @SerialName("updated_at") val updatedAt: Timestamp? = null,
    @SerialName("base_permission") val basePermission: PermissionLevel = PermissionLevel.NONE,
    val config: BuilderConfig
)

// builder/BuilderConfig.kt - oneOf
@Serializable
sealed class BuilderConfig {
    @Serializable @SerialName("Url")
    data class Url(val params: UrlBuilderConfig) : BuilderConfig()
    
    @Serializable @SerialName("Server")
    data class Server(val params: ServerBuilderConfig) : BuilderConfig()
    
    @Serializable @SerialName("Aws")
    data class Aws(val params: AwsBuilderConfig) : BuilderConfig()
}

// builder/BuilderListItem.kt
@Serializable
data class BuilderListItem(
    val id: MongoId,
    val name: String,
    val tags: List<String>,
    @SerialName("builder_type") val builderType: String
)
```

### 4. Supporting Resource Types

#### 4.1 Action & Procedure
```kotlin
// action/Action.kt, action/ActionConfig.kt, action/ActionListItem.kt
// procedure/Procedure.kt, procedure/ProcedureConfig.kt, procedure/ProcedureListItem.kt
// procedure/Execution.kt - large discriminated union
```

#### 4.2 Alerter
```kotlin
// alerter/Alerter.kt
// alerter/AlerterConfig.kt - Custom|Slack|Discord|Ntfy|Pushover
// alerter/AlerterListItem.kt
```

#### 4.3 Repo
```kotlin
// repo/Repo.kt
// repo/RepoConfig.kt
// repo/RepoListItem.kt
```

#### 4.4 ResourceSync
```kotlin
// sync/ResourceSync.kt
// sync/ResourceSyncConfig.kt
// sync/ResourceSyncListItem.kt
```

#### 4.5 Swarm
```kotlin
// swarm/Swarm.kt
// swarm/SwarmConfig.kt
// swarm/SwarmListItem.kt
// swarm/SwarmNode.kt
// swarm/SwarmService.kt
```

### 5. Container Types

**Files to Create in `core-domain/.../model/container/`:**

```kotlin
// Container.kt - Full container inspect response
@Serializable
data class Container(
    @SerialName("Id") val id: String,
    @SerialName("Image") val image: String,
    @SerialName("Name") val name: String,
    @SerialName("State") val state: ContainerState,
    @SerialName("Config") val config: ContainerConfig,
    @SerialName("NetworkSettings") val networkSettings: NetworkSettings? = null,
    @SerialName("Mounts") val mounts: List<Mount> = emptyList(),
    @SerialName("HostConfig") val hostConfig: HostConfig? = null
)

// ContainerState.kt
@Serializable
data class ContainerState(
    @SerialName("Running") val running: Boolean,
    @SerialName("Paused") val paused: Boolean,
    @SerialName("Restarting") val restarting: Boolean,
    @SerialName("Dead") val dead: Boolean,
    @SerialName("ExitCode") val exitCode: Int,
    @SerialName("Status") val status: ContainerStateStatus
)

// ContainerConfig.kt
@Serializable
data class ContainerConfig(
    @SerialName("Cmd") val cmd: List<String>? = null,
    @SerialName("Entrypoint") val entrypoint: List<String>? = null,
    @SerialName("Env") val env: List<String>? = null,
    @SerialName("Image") val image: String,
    @SerialName("Labels") val labels: Map<String, String> = emptyMap(),
    @SerialName("Volumes") val volumes: Map<String, Any?>? = null,
    @SerialName("WorkingDir") val workingDir: String = ""
)

// ContainerListItem.kt - For list APIs
@Serializable
data class ContainerListItem(
    val name: String,
    val state: ContainerStateStatus,
    val id: String,
    val image: String,
    val ports: String = "",
    val stats: ContainerStats? = null
)

// ContainerStats.kt
@Serializable
data class ContainerStats(
    val name: String,
    @SerialName("cpu_perc") val cpuPerc: String,
    @SerialName("mem_perc") val memPerc: String,
    @SerialName("mem_usage") val memUsage: String,
    @SerialName("net_io") val netIo: String,
    @SerialName("block_io") val blockIo: String,
    val pids: String
)
```

### 6. Auth Types

**Files to Create in `core-domain/.../model/auth/`:**

```kotlin
// LoginOptions.kt (extend existing)
// JwtResponse.kt (extend existing)
// TwoFactorResponse.kt
@Serializable
sealed class JwtOrTwoFactor {
    @Serializable @SerialName("Jwt")
    data class Jwt(val params: JwtResponse) : JwtOrTwoFactor()
    
    @Serializable @SerialName("TwoFactor")
    data class TwoFactor(val params: TwoFactorParams) : JwtOrTwoFactor()
}

// ApiKey.kt
@Serializable
data class ApiKey(
    val name: String,
    @SerialName("created_at") val createdAt: I64,
    val expires: I64? = null
)

// TotpTypes.kt
@Serializable
data class TotpEnrollmentResponse(
    val secret: String,
    @SerialName("qr_code_base64") val qrCodeBase64: String
)

@Serializable
data class ConfirmTotpEnrollmentResponse(
    @SerialName("recovery_codes") val recoveryCodes: List<String>
)

// PasskeyTypes.kt - WebAuthn types
// User.kt, UserGroup.kt, Variable.kt, Tag.kt
```

### 7. Operation Types (Request/Response)

**Files to Create in `core-domain/.../model/operation/`:**

```kotlin
// Create*.kt - CreateServer, CreateDeployment, CreateStack, etc.
// Update*.kt - UpdateServer, UpdateDeployment, etc.
// Delete*.kt - DeleteServer{id}, DeleteDeployment{id}, etc.
// Copy*.kt - CopyServer{name, id}, CopyDeployment{name, id}, etc.
// Rename*.kt - RenameServer{id, name}, etc.

// Execute operations
// Deploy.kt, DeployStack.kt, DeployStackIfChanged.kt
// StartDeployment.kt, StopDeployment.kt, RestartDeployment.kt
// BuildParams.kt (RunBuild, CancelBuild)
// ActionParams.kt (RunAction)
// ProcedureParams.kt (RunProcedure)
```

### 8. Common Shared Types

**Files to Create in `core-domain/.../model/common/`:**

```kotlin
// Conversion.kt - Used for port/volume mappings
@Serializable
data class Conversion(
    val local: String,
    val container: String
)

// EnvironmentVar.kt
@Serializable
data class EnvironmentVar(
    val variable: String,
    val value: String
)

// RestartMode.kt
@Serializable
enum class RestartMode {
    @SerialName("no") NO,
    @SerialName("on-failure") ON_FAILURE,
    @SerialName("always") ALWAYS,
    @SerialName("unless-stopped") UNLESS_STOPPED
}

// TerminationSignal.kt
@Serializable
enum class TerminationSignal {
    @SerialName("SIGTERM") SIGTERM,
    @SerialName("SIGINT") SIGINT,
    @SerialName("SIGQUIT") SIGQUIT,
    @SerialName("SIGKILL") SIGKILL
}

// NoData.kt - Empty response
@Serializable
data object NoData

// Update.kt - Common update response
@Serializable
data class Update(
    val success: Boolean,
    val message: String = ""
)

// Log.kt
@Serializable
data class Log(
    val stdout: String,
    val stderr: String
)

// SearchResult.kt, ListQuery.kt, Pagination.kt
```

## File Structure (Final)
```
core-domain/
└── src/commonMain/kotlin/ca/glong/komodo/core/domain/model/
    ├── common/
    │   ├── Conversion.kt
    │   ├── EnvironmentVar.kt
    │   ├── Log.kt
    │   ├── MongoId.kt
    │   ├── NoData.kt
    │   ├── RestartMode.kt
    │   ├── TerminationSignal.kt
    │   ├── Timestamp.kt
    │   └── Update.kt
    ├── state/
    │   ├── ActionState.kt
    │   ├── BuildState.kt
    │   ├── ContainerStateStatus.kt
    │   ├── DeploymentState.kt
    │   ├── ProcedureState.kt
    │   ├── RepoState.kt
    │   ├── ServerState.kt
    │   ├── StackState.kt
    │   └── SyncState.kt
    ├── permission/
    │   ├── PermissionLevel.kt
    │   └── UserTarget.kt
    ├── resource/
    │   ├── server/
    │   │   ├── Server.kt
    │   │   ├── ServerConfig.kt
    │   │   ├── ServerInfo.kt
    │   │   ├── ServerStats.kt
    │   │   └── ServerListItem.kt
    │   ├── deployment/
    │   │   ├── Deployment.kt
    │   │   ├── DeploymentConfig.kt
    │   │   ├── DeploymentImage.kt
    │   │   ├── DeploymentInfo.kt
    │   │   └── DeploymentListItem.kt
    │   ├── stack/
    │   │   ├── Stack.kt
    │   │   ├── StackConfig.kt
    │   │   ├── StackInfo.kt
    │   │   └── StackListItem.kt
    │   ├── build/
    │   │   ├── Build.kt
    │   │   ├── BuildConfig.kt
    │   │   ├── BuildInfo.kt
    │   │   ├── BuildListItem.kt
    │   │   └── ImageRegistry.kt
    │   ├── builder/
    │   │   ├── Builder.kt
    │   │   ├── BuilderConfig.kt
    │   │   └── BuilderListItem.kt
    │   ├── action/
    │   ├── procedure/
    │   ├── alerter/
    │   ├── repo/
    │   ├── sync/
    │   └── swarm/
    ├── container/
    │   ├── Container.kt
    │   ├── ContainerConfig.kt
    │   ├── ContainerListItem.kt
    │   ├── ContainerState.kt
    │   └── ContainerStats.kt
    ├── auth/
    │   ├── ApiKey.kt
    │   ├── JwtOrTwoFactor.kt
    │   ├── LoginOptions.kt
    │   ├── PasskeyTypes.kt
    │   ├── TotpTypes.kt
    │   ├── User.kt
    │   └── UserGroup.kt
    └── operation/
        ├── create/
        ├── update/
        ├── delete/
        ├── copy/
        ├── rename/
        └── execute/
```

## Tests

### Serialization Tests
**Files to Create in `core-domain/src/commonTest/kotlin/.../`:**

- `ServerSerializationTest.kt` - Round-trip JSON serialization
- `DeploymentSerializationTest.kt`
- `StackSerializationTest.kt`
- `BuildSerializationTest.kt`
- `ContainerSerializationTest.kt`
- `StateEnumSerializationTest.kt` - All enums serialize to correct @SerialName

**Test Pattern:**
```kotlin
class ServerSerializationTest {
    private val json = Json { ignoreUnknownKeys = true }
    
    @Test
    fun `deserialize server from API response`() {
        val jsonString = """{"_id":"abc123","name":"test-server",...}"""
        val server = json.decodeFromString<Server>(jsonString)
        assertEquals("abc123", server.id)
    }
    
    @Test  
    fun `serialize server for API request`() {
        val server = Server(name = "test", config = ServerConfig(...))
        val jsonString = json.encodeToString(server)
        assertTrue(jsonString.contains("\"name\":\"test\""))
    }
}
```

## Acceptance Criteria
- [ ] `./gradlew :core-domain:test` passes
- [ ] `./gradlew :core-domain:compileKotlinCommonMain` succeeds
- [ ] All DTOs match OpenAPI schema field names (via @SerialName)
- [ ] Discriminated unions (oneOf) work with sealed classes
- [ ] No LSP diagnostics errors
- [ ] Optional fields have defaults
- [ ] Nullable fields properly annotated

## Implementation Notes

### Discriminated Unions (oneOf)
OpenAPI `oneOf` maps to Kotlin sealed classes with `@SerialName` on subtypes:
```kotlin
@Serializable
sealed class DeploymentImage {
    @Serializable @SerialName("Image")
    data class Image(val params: ImageParams) : DeploymentImage()
    
    @Serializable @SerialName("Build")  
    data class Build(val params: BuildParams) : DeploymentImage()
}
```

### Field Naming
API uses snake_case, Kotlin uses camelCase. Always use `@SerialName`:
```kotlin
@SerialName("server_id") val serverId: String
```

### Optional vs Nullable
- Optional fields: Provide default values (`val field: String = ""`)
- Nullable fields: Use `?` (`val field: String? = null`)
- Check OpenAPI `required` array for each schema

## Estimated Effort
- State enums & common types: 2 hours
- Core resources (Server, Deployment, Stack, Build): 4 hours
- Supporting resources (Action, Procedure, Alerter, etc.): 3 hours
- Container types: 1 hour
- Auth types: 1 hour
- Operation types: 2 hours
- Tests: 3 hours
- **Total: 16-18 hours**
