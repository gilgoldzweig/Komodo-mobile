# Plan 004: Read API Client

## Overview
Implement the Read API client covering all read-only RPC operations (~80 endpoints). This is the largest API client, providing list/get operations for all resource types plus stats, logs, and search.

## Prerequisites
- Plan 001 (Networking Infrastructure) - KomodoRpcClient, ApiError
- Plan 002 (Core Domain DTOs) - All resource types and list items

## Depends On
- `core-network` module with KomodoRpcClient
- `core-domain` module with resource DTOs

## Deliverables

### 1. Read API Client Interface

**File: `core-network/src/commonMain/kotlin/ca/glong/komodo/core/network/api/ReadApiClient.kt`**

The interface is organized by resource type for maintainability:

```kotlin
interface ReadApiClient {
    // ═══════════════════════════════════════════════════════════════
    // SERVER OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun listServers(query: ResourceQuery? = null): Result<List<ServerListItem>>
    suspend fun getServer(id: String): Result<Server>
    suspend fun getServerInfo(server: String): Result<ServerInfo>
    suspend fun getServerStats(server: String): Result<ServerStats>
    suspend fun getSystemInfo(server: String): Result<SystemInfo>
    suspend fun getSystemStats(server: String): Result<SystemStats>
    suspend fun getDockerVersion(server: String): Result<DockerVersion>
    
    // Container operations on server
    suspend fun listContainers(server: String): Result<List<ContainerListItem>>
    suspend fun inspectContainer(server: String, container: String): Result<Container>
    suspend fun getContainerLog(server: String, container: String, tail: Int? = null): Result<Log>
    suspend fun getContainerStats(server: String, container: String): Result<ContainerStats>
    
    // Network/Volume/Image operations on server
    suspend fun listNetworks(server: String): Result<List<Network>>
    suspend fun listVolumes(server: String): Result<List<Volume>>
    suspend fun listImages(server: String): Result<List<Image>>

    // ═══════════════════════════════════════════════════════════════
    // DEPLOYMENT OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun listDeployments(query: ResourceQuery? = null): Result<List<DeploymentListItem>>
    suspend fun getDeployment(id: String): Result<Deployment>
    suspend fun getDeploymentLog(deployment: String, tail: Int? = null): Result<Log>
    suspend fun getDeploymentStats(deployment: String): Result<ContainerStats>
    suspend fun getDeploymentActionState(deployment: String): Result<DeploymentActionState>

    // ═══════════════════════════════════════════════════════════════
    // STACK OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun listStacks(query: ResourceQuery? = null): Result<List<StackListItem>>
    suspend fun getStack(id: String): Result<Stack>
    suspend fun getStackServiceLog(
        stack: String, 
        service: String, 
        tail: Int? = null
    ): Result<Log>
    suspend fun getStackServices(stack: String): Result<List<StackServiceState>>
    suspend fun getStackComposeContents(stack: String): Result<StackComposeContents>
    
    // ═══════════════════════════════════════════════════════════════
    // BUILD OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun listBuilds(query: ResourceQuery? = null): Result<List<BuildListItem>>
    suspend fun getBuild(id: String): Result<Build>
    suspend fun getBuildLog(build: String, tail: Int? = null): Result<Log>
    suspend fun getBuildVersions(build: String): Result<List<BuildVersion>>
    suspend fun getBuildActionState(build: String): Result<BuildActionState>
    
    // ═══════════════════════════════════════════════════════════════
    // BUILDER OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun listBuilders(query: ResourceQuery? = null): Result<List<BuilderListItem>>
    suspend fun getBuilder(id: String): Result<Builder>
    
    // ═══════════════════════════════════════════════════════════════
    // REPO OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun listRepos(query: ResourceQuery? = null): Result<List<RepoListItem>>
    suspend fun getRepo(id: String): Result<Repo>
    suspend fun getRepoWebhook(repo: String): Result<RepoWebhook>
    suspend fun getRepoActionState(repo: String): Result<RepoActionState>
    
    // ═══════════════════════════════════════════════════════════════
    // ACTION OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun listActions(query: ResourceQuery? = null): Result<List<ActionListItem>>
    suspend fun getAction(id: String): Result<Action>
    suspend fun getActionActionState(action: String): Result<ActionActionState>
    
    // ═══════════════════════════════════════════════════════════════
    // PROCEDURE OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun listProcedures(query: ResourceQuery? = null): Result<List<ProcedureListItem>>
    suspend fun getProcedure(id: String): Result<Procedure>
    suspend fun getProcedureActionState(procedure: String): Result<ProcedureActionState>
    
    // ═══════════════════════════════════════════════════════════════
    // ALERTER OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun listAlerters(query: ResourceQuery? = null): Result<List<AlerterListItem>>
    suspend fun getAlerter(id: String): Result<Alerter>
    
    // ═══════════════════════════════════════════════════════════════
    // RESOURCE SYNC OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun listResourceSyncs(query: ResourceQuery? = null): Result<List<ResourceSyncListItem>>
    suspend fun getResourceSync(id: String): Result<ResourceSync>
    suspend fun getResourceSyncActionState(sync: String): Result<SyncActionState>
    
    // ═══════════════════════════════════════════════════════════════
    // SWARM OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun listSwarms(query: ResourceQuery? = null): Result<List<SwarmListItem>>
    suspend fun getSwarm(id: String): Result<Swarm>
    suspend fun listSwarmNodes(swarm: String): Result<List<SwarmNode>>
    suspend fun listSwarmServices(swarm: String): Result<List<SwarmService>>
    suspend fun listSwarmConfigs(swarm: String): Result<List<SwarmConfig>>
    suspend fun listSwarmSecrets(swarm: String): Result<List<SwarmSecret>>
    suspend fun getSwarmServiceLog(swarm: String, service: String, tail: Int? = null): Result<Log>
    
    // ═══════════════════════════════════════════════════════════════
    // USER & GROUP OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun listUserGroups(): Result<List<UserGroup>>
    suspend fun getUserGroup(id: String): Result<UserGroup>
    suspend fun listUsers(): Result<List<User>>
    suspend fun getUser(id: String): Result<User>
    suspend fun getUsername(id: String): Result<String>
    
    // ═══════════════════════════════════════════════════════════════
    // VARIABLE & TAG OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun listVariables(): Result<List<Variable>>
    suspend fun getVariable(name: String): Result<Variable>
    suspend fun listTags(): Result<List<Tag>>
    suspend fun getTag(id: String): Result<Tag>
    
    // ═══════════════════════════════════════════════════════════════
    // TERMINAL OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun listTerminals(target: TerminalTarget? = null): Result<List<Terminal>>
    
    // ═══════════════════════════════════════════════════════════════
    // ACCOUNT OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun listDockerRegistryAccounts(): Result<List<DockerRegistryAccount>>
    suspend fun listGitProviderAccounts(): Result<List<GitProviderAccount>>
    
    // ═══════════════════════════════════════════════════════════════
    // SEARCH & STATS OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun findResources(query: String, resourceTypes: List<ResourceType>? = null): Result<FindResourcesResponse>
    suspend fun getResourceCount(): Result<ResourceCount>
    suspend fun searchLog(
        resourceType: ResourceType,
        resourceId: String,
        terms: List<String>,
        combinator: SearchCombinator = SearchCombinator.AND,
        invert: Boolean = false
    ): Result<SearchLogResponse>
    
    // ═══════════════════════════════════════════════════════════════
    // ALERT OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun listAlerts(query: AlertQuery? = null): Result<List<Alert>>
    
    // ═══════════════════════════════════════════════════════════════
    // UPDATE OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun listUpdates(query: UpdateQuery? = null): Result<List<Update>>
    
    // ═══════════════════════════════════════════════════════════════
    // PERMISSION OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun getPermissionLevel(
        target: UserTarget,
        resourceType: ResourceType,
        resourceId: String
    ): Result<PermissionLevel>
    suspend fun listPermissions(
        resourceType: ResourceType,
        resourceId: String
    ): Result<List<Permission>>
}
```

### 2. Query Parameter DTOs

**File: `core-domain/.../model/query/`**

```kotlin
// ResourceQuery.kt - Common query params for list operations
@Serializable
data class ResourceQuery(
    val tags: List<String>? = null,
    @SerialName("tag_behavior") val tagBehavior: TagBehavior? = null,
    val search: String? = null,
    val page: Int? = null,
    @SerialName("page_size") val pageSize: Int? = null
)

@Serializable
enum class TagBehavior {
    @SerialName("all") ALL,
    @SerialName("any") ANY
}

// AlertQuery.kt
@Serializable
data class AlertQuery(
    val target: AlertTarget? = null,
    val resolved: Boolean? = null,
    val page: Int? = null,
    @SerialName("page_size") val pageSize: Int? = null
)

// UpdateQuery.kt
@Serializable
data class UpdateQuery(
    val target: UpdateTarget? = null,
    val page: Int? = null,
    @SerialName("page_size") val pageSize: Int? = null
)

// TerminalTarget.kt
@Serializable
sealed class TerminalTarget {
    @Serializable @SerialName("Server")
    data class Server(val params: ServerTargetParams) : TerminalTarget()
    @Serializable @SerialName("Deployment")
    data class Deployment(val params: DeploymentTargetParams) : TerminalTarget()
    @Serializable @SerialName("Container")
    data class Container(val params: ContainerTargetParams) : TerminalTarget()
}

// ResourceType.kt
@Serializable
enum class ResourceType {
    @SerialName("Server") SERVER,
    @SerialName("Deployment") DEPLOYMENT,
    @SerialName("Stack") STACK,
    @SerialName("Build") BUILD,
    @SerialName("Builder") BUILDER,
    @SerialName("Repo") REPO,
    @SerialName("Action") ACTION,
    @SerialName("Procedure") PROCEDURE,
    @SerialName("Alerter") ALERTER,
    @SerialName("ResourceSync") RESOURCE_SYNC,
    @SerialName("Swarm") SWARM
}

// SearchCombinator.kt
@Serializable
enum class SearchCombinator {
    @SerialName("and") AND,
    @SerialName("or") OR
}
```

### 3. Read API Client Implementation

**File: `core-network/.../api/ReadApiClientImpl.kt`**

```kotlin
@Single(binds = [ReadApiClient::class])
class ReadApiClientImpl(
    private val rpcClient: KomodoRpcClient
) : ReadApiClient {

    // ═══════════════════════════════════════════════════════════════
    // SERVER OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    
    override suspend fun listServers(query: ResourceQuery?): Result<List<ServerListItem>> =
        rpcClient.call(
            endpoint = RpcEndpoint.READ,
            operation = "ListServers",
            params = ListServersParams(query),
            paramsSerializer = ListServersParams.serializer(),
            responseSerializer = ListSerializer(ServerListItem.serializer())
        )

    override suspend fun getServer(id: String): Result<Server> =
        rpcClient.call(
            endpoint = RpcEndpoint.READ,
            operation = "GetServer",
            params = GetServerParams(id),
            paramsSerializer = GetServerParams.serializer(),
            responseSerializer = Server.serializer()
        )

    override suspend fun getServerInfo(server: String): Result<ServerInfo> =
        rpcClient.call(
            endpoint = RpcEndpoint.READ,
            operation = "GetServerInfo",
            params = ServerIdParams(server),
            paramsSerializer = ServerIdParams.serializer(),
            responseSerializer = ServerInfo.serializer()
        )

    override suspend fun listContainers(server: String): Result<List<ContainerListItem>> =
        rpcClient.call(
            endpoint = RpcEndpoint.READ,
            operation = "ListContainers",
            params = ServerIdParams(server),
            paramsSerializer = ServerIdParams.serializer(),
            responseSerializer = ListSerializer(ContainerListItem.serializer())
        )

    override suspend fun inspectContainer(
        server: String, 
        container: String
    ): Result<Container> =
        rpcClient.call(
            endpoint = RpcEndpoint.READ,
            operation = "InspectContainer",
            params = InspectContainerParams(server, container),
            paramsSerializer = InspectContainerParams.serializer(),
            responseSerializer = Container.serializer()
        )

    // ═══════════════════════════════════════════════════════════════
    // DEPLOYMENT OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    
    override suspend fun listDeployments(query: ResourceQuery?): Result<List<DeploymentListItem>> =
        rpcClient.call(
            endpoint = RpcEndpoint.READ,
            operation = "ListDeployments",
            params = ListDeploymentsParams(query),
            paramsSerializer = ListDeploymentsParams.serializer(),
            responseSerializer = ListSerializer(DeploymentListItem.serializer())
        )

    override suspend fun getDeployment(id: String): Result<Deployment> =
        rpcClient.call(
            endpoint = RpcEndpoint.READ,
            operation = "GetDeployment",
            params = GetDeploymentParams(id),
            paramsSerializer = GetDeploymentParams.serializer(),
            responseSerializer = Deployment.serializer()
        )

    override suspend fun getDeploymentLog(
        deployment: String, 
        tail: Int?
    ): Result<Log> =
        rpcClient.call(
            endpoint = RpcEndpoint.READ,
            operation = "GetDeploymentLog",
            params = GetDeploymentLogParams(deployment, tail),
            paramsSerializer = GetDeploymentLogParams.serializer(),
            responseSerializer = Log.serializer()
        )

    // ... continue for all ~80 operations following same pattern
    
    // ═══════════════════════════════════════════════════════════════
    // SEARCH & STATS
    // ═══════════════════════════════════════════════════════════════
    
    override suspend fun findResources(
        query: String, 
        resourceTypes: List<ResourceType>?
    ): Result<FindResourcesResponse> =
        rpcClient.call(
            endpoint = RpcEndpoint.READ,
            operation = "FindResources",
            params = FindResourcesParams(query, resourceTypes),
            paramsSerializer = FindResourcesParams.serializer(),
            responseSerializer = FindResourcesResponse.serializer()
        )

    override suspend fun getResourceCount(): Result<ResourceCount> =
        rpcClient.call(
            endpoint = RpcEndpoint.READ,
            operation = "GetResourceCount",
            params = EmptyParams,
            paramsSerializer = EmptyParams.serializer(),
            responseSerializer = ResourceCount.serializer()
        )
}
```

### 4. Request Parameter DTOs

**Files in `core-domain/.../model/read/params/`**

```kotlin
// ServerParams.kt
@Serializable
data class ListServersParams(val query: ResourceQuery? = null)

@Serializable
data class GetServerParams(val server: String)

@Serializable
data class ServerIdParams(val server: String)

@Serializable
data class InspectContainerParams(
    val server: String,
    val container: String
)

@Serializable
data class GetContainerLogParams(
    val server: String,
    val container: String,
    val tail: Int? = null
)

// DeploymentParams.kt
@Serializable
data class ListDeploymentsParams(val query: ResourceQuery? = null)

@Serializable
data class GetDeploymentParams(val deployment: String)

@Serializable
data class GetDeploymentLogParams(
    val deployment: String,
    val tail: Int? = null
)

// StackParams.kt
@Serializable
data class ListStacksParams(val query: ResourceQuery? = null)

@Serializable
data class GetStackParams(val stack: String)

@Serializable
data class GetStackServiceLogParams(
    val stack: String,
    val service: String,
    val tail: Int? = null
)

// BuildParams.kt
@Serializable
data class ListBuildsParams(val query: ResourceQuery? = null)

@Serializable
data class GetBuildParams(val build: String)

@Serializable
data class GetBuildLogParams(
    val build: String,
    val tail: Int? = null
)

// SwarmParams.kt
@Serializable
data class ListSwarmsParams(val query: ResourceQuery? = null)

@Serializable
data class GetSwarmParams(val swarm: String)

@Serializable
data class GetSwarmServiceLogParams(
    val swarm: String,
    val service: String,
    val tail: Int? = null
)

// SearchParams.kt
@Serializable
data class FindResourcesParams(
    val query: String,
    @SerialName("resource_types") val resourceTypes: List<ResourceType>? = null
)

@Serializable
data class SearchLogParams(
    @SerialName("resource_type") val resourceType: ResourceType,
    @SerialName("resource_id") val resourceId: String,
    val terms: List<String>,
    val combinator: SearchCombinator = SearchCombinator.AND,
    val invert: Boolean = false
)

// PermissionParams.kt
@Serializable
data class GetPermissionLevelParams(
    val target: UserTarget,
    @SerialName("resource_type") val resourceType: ResourceType,
    @SerialName("resource_id") val resourceId: String
)

@Serializable
data class ListPermissionsParams(
    @SerialName("resource_type") val resourceType: ResourceType,
    @SerialName("resource_id") val resourceId: String
)
```

### 5. Response DTOs (additions)

**Files in `core-domain/.../model/`**

```kotlin
// response/FindResourcesResponse.kt
@Serializable
data class FindResourcesResponse(
    val servers: List<ServerListItem> = emptyList(),
    val deployments: List<DeploymentListItem> = emptyList(),
    val stacks: List<StackListItem> = emptyList(),
    val builds: List<BuildListItem> = emptyList(),
    val builders: List<BuilderListItem> = emptyList(),
    val repos: List<RepoListItem> = emptyList(),
    val actions: List<ActionListItem> = emptyList(),
    val procedures: List<ProcedureListItem> = emptyList(),
    val alerters: List<AlerterListItem> = emptyList(),
    @SerialName("resource_syncs") val resourceSyncs: List<ResourceSyncListItem> = emptyList(),
    val swarms: List<SwarmListItem> = emptyList()
)

// response/ResourceCount.kt
@Serializable
data class ResourceCount(
    val servers: Int = 0,
    val deployments: Int = 0,
    val stacks: Int = 0,
    val builds: Int = 0,
    val builders: Int = 0,
    val repos: Int = 0,
    val actions: Int = 0,
    val procedures: Int = 0,
    val alerters: Int = 0,
    @SerialName("resource_syncs") val resourceSyncs: Int = 0,
    val swarms: Int = 0
)

// response/SearchLogResponse.kt
@Serializable
data class SearchLogResponse(
    val stdout: String,
    val stderr: String
)

// container/Network.kt
@Serializable
data class Network(
    @SerialName("Name") val name: String,
    @SerialName("Id") val id: String,
    @SerialName("Driver") val driver: String,
    @SerialName("Scope") val scope: String
)

// container/Volume.kt
@Serializable
data class Volume(
    @SerialName("Name") val name: String,
    @SerialName("Driver") val driver: String,
    @SerialName("Mountpoint") val mountpoint: String
)

// container/Image.kt
@Serializable
data class Image(
    @SerialName("Id") val id: String,
    @SerialName("RepoTags") val repoTags: List<String> = emptyList(),
    @SerialName("Size") val size: Long
)

// server/SystemInfo.kt
@Serializable
data class SystemInfo(
    @SerialName("Containers") val containers: Int,
    @SerialName("ContainersRunning") val containersRunning: Int,
    @SerialName("ContainersPaused") val containersPaused: Int,
    @SerialName("ContainersStopped") val containersStopped: Int,
    @SerialName("Images") val images: Int,
    @SerialName("KernelVersion") val kernelVersion: String,
    @SerialName("OperatingSystem") val operatingSystem: String,
    @SerialName("NCPU") val ncpu: Int,
    @SerialName("MemTotal") val memTotal: Long
)

// server/SystemStats.kt
@Serializable
data class SystemStats(
    @SerialName("cpu_perc") val cpuPerc: Double,
    @SerialName("mem_perc") val memPerc: Double,
    @SerialName("disk_perc") val diskPerc: Double,
    // ... additional stats fields
)

// server/DockerVersion.kt
@Serializable
data class DockerVersion(
    @SerialName("Version") val version: String,
    @SerialName("ApiVersion") val apiVersion: String,
    @SerialName("Os") val os: String,
    @SerialName("Arch") val arch: String
)
```

## Read Operations Reference (from OpenAPI)

### Server Operations (~15)
| Operation | Params | Response |
|-----------|--------|----------|
| ListServers | `{query?}` | `List<ServerListItem>` |
| GetServer | `{server}` | `Server` |
| GetServerInfo | `{server}` | `ServerInfo` |
| GetServerStats | `{server}` | `ServerStats` |
| GetSystemInfo | `{server}` | `SystemInfo` |
| GetSystemStats | `{server}` | `SystemStats` |
| GetDockerVersion | `{server}` | `DockerVersion` |
| ListContainers | `{server}` | `List<ContainerListItem>` |
| InspectContainer | `{server, container}` | `Container` |
| GetContainerLog | `{server, container, tail?}` | `Log` |
| GetContainerStats | `{server, container}` | `ContainerStats` |
| ListNetworks | `{server}` | `List<Network>` |
| ListVolumes | `{server}` | `List<Volume>` |
| ListImages | `{server}` | `List<Image>` |

### Deployment Operations (~6)
| Operation | Params | Response |
|-----------|--------|----------|
| ListDeployments | `{query?}` | `List<DeploymentListItem>` |
| GetDeployment | `{deployment}` | `Deployment` |
| GetDeploymentLog | `{deployment, tail?}` | `Log` |
| GetDeploymentStats | `{deployment}` | `ContainerStats` |
| GetDeploymentActionState | `{deployment}` | `DeploymentActionState` |

### Stack Operations (~5)
| Operation | Params | Response |
|-----------|--------|----------|
| ListStacks | `{query?}` | `List<StackListItem>` |
| GetStack | `{stack}` | `Stack` |
| GetStackServiceLog | `{stack, service, tail?}` | `Log` |
| GetStackServices | `{stack}` | `List<StackServiceState>` |
| GetStackComposeContents | `{stack}` | `StackComposeContents` |

### Build/Builder Operations (~8)
| Operation | Params | Response |
|-----------|--------|----------|
| ListBuilds | `{query?}` | `List<BuildListItem>` |
| GetBuild | `{build}` | `Build` |
| GetBuildLog | `{build, tail?}` | `Log` |
| GetBuildVersions | `{build}` | `List<BuildVersion>` |
| GetBuildActionState | `{build}` | `BuildActionState` |
| ListBuilders | `{query?}` | `List<BuilderListItem>` |
| GetBuilder | `{builder}` | `Builder` |

### Additional Resource Operations (~30)
Similar patterns for: Repo, Action, Procedure, Alerter, ResourceSync, Swarm, UserGroup, User, Variable, Tag, Terminal, DockerRegistryAccount, GitProviderAccount

### Search & Stats (~4)
| Operation | Params | Response |
|-----------|--------|----------|
| FindResources | `{query, resource_types?}` | `FindResourcesResponse` |
| GetResourceCount | `{}` | `ResourceCount` |
| SearchLog | `{resource_type, resource_id, terms, combinator?, invert?}` | `SearchLogResponse` |

## Tests

### Unit Tests
**File: `core-network/src/commonTest/kotlin/.../api/ReadApiClientTest.kt`**

```kotlin
class ReadApiClientTest {
    private lateinit var mockRpcClient: MockKomodoRpcClient
    private lateinit var readClient: ReadApiClient

    @BeforeTest
    fun setup() {
        mockRpcClient = MockKomodoRpcClient()
        readClient = ReadApiClientImpl(mockRpcClient)
    }

    // Server tests
    @Test
    fun `listServers returns server list items`() = runTest {
        val servers = listOf(
            ServerListItem(id = "s1", name = "server-1", tags = emptyList(), state = ServerState.Ok),
            ServerListItem(id = "s2", name = "server-2", tags = listOf("prod"), state = ServerState.Ok)
        )
        mockRpcClient.mockResponse(servers)

        val result = readClient.listServers()

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrThrow().size)
    }

    @Test
    fun `listServers with query filters by tags`() = runTest {
        val query = ResourceQuery(tags = listOf("prod"), tagBehavior = TagBehavior.ALL)
        mockRpcClient.mockResponse(emptyList<ServerListItem>())

        readClient.listServers(query)

        mockRpcClient.verifyCall("ListServers", ListServersParams(query))
    }

    @Test
    fun `getServer returns full server details`() = runTest {
        val server = Server(
            id = "abc123",
            name = "test-server",
            config = ServerConfig(address = "192.168.1.1")
        )
        mockRpcClient.mockResponse(server)

        val result = readClient.getServer("abc123")

        assertTrue(result.isSuccess)
        assertEquals("test-server", result.getOrThrow().name)
    }

    // Container tests
    @Test
    fun `listContainers returns containers on server`() = runTest {
        val containers = listOf(
            ContainerListItem(name = "nginx", state = ContainerStateStatus.Running, id = "c1", image = "nginx:latest")
        )
        mockRpcClient.mockResponse(containers)

        val result = readClient.listContainers("server-1")

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrThrow().size)
    }

    // Deployment tests
    @Test
    fun `getDeploymentLog returns stdout and stderr`() = runTest {
        val log = Log(stdout = "Starting...\nRunning...", stderr = "")
        mockRpcClient.mockResponse(log)

        val result = readClient.getDeploymentLog("deploy-1", tail = 100)

        assertTrue(result.isSuccess)
        assertTrue(result.getOrThrow().stdout.contains("Running"))
    }

    // Search tests
    @Test
    fun `findResources returns matching resources`() = runTest {
        val response = FindResourcesResponse(
            servers = listOf(ServerListItem(id = "s1", name = "prod-server", tags = emptyList(), state = ServerState.Ok)),
            deployments = emptyList()
        )
        mockRpcClient.mockResponse(response)

        val result = readClient.findResources("prod")

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrThrow().servers.size)
    }

    // Error handling tests
    @Test
    fun `getServer returns NotFound for missing server`() = runTest {
        mockRpcClient.mockError(ApiError.NotFound("Server"))

        val result = readClient.getServer("nonexistent")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is ApiError.NotFound)
    }
}
```

## File Structure (Final)
```
core-network/
└── src/
    ├── commonMain/kotlin/ca/glong/komodo/core/network/api/
    │   ├── ReadApiClient.kt
    │   └── ReadApiClientImpl.kt
    └── commonTest/kotlin/ca/glong/komodo/core/network/api/
        └── ReadApiClientTest.kt

core-domain/
└── src/commonMain/kotlin/ca/glong/komodo/core/domain/model/
    ├── query/
    │   ├── AlertQuery.kt
    │   ├── ResourceQuery.kt
    │   ├── ResourceType.kt
    │   ├── SearchCombinator.kt
    │   ├── TagBehavior.kt
    │   ├── TerminalTarget.kt
    │   └── UpdateQuery.kt
    ├── read/
    │   └── params/
    │       ├── BuildParams.kt
    │       ├── DeploymentParams.kt
    │       ├── PermissionParams.kt
    │       ├── SearchParams.kt
    │       ├── ServerParams.kt
    │       ├── StackParams.kt
    │       └── SwarmParams.kt
    └── response/
        ├── FindResourcesResponse.kt
        ├── ResourceCount.kt
        └── SearchLogResponse.kt
```

## Acceptance Criteria
- [ ] `./gradlew :core-network:test` passes
- [ ] ReadApiClient covers all ~80 read operations
- [ ] List operations support optional query parameters
- [ ] Log operations support optional tail parameter
- [ ] All resource types have list/get operations
- [ ] Search and stats operations work correctly
- [ ] Container operations work with server context

## Estimated Effort
- Interface definition: 2 hours
- Implementation (~80 methods): 4 hours
- Query/Param DTOs: 2 hours
- Response DTOs: 2 hours
- Tests: 4 hours
- **Total: 14-16 hours**
