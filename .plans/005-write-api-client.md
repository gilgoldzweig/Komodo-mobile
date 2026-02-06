# Plan 005: Write API Client

## Overview
Implement the Write API client covering all mutation RPC operations (~100 endpoints). This includes Create, Update, Delete, Copy, and Rename operations for all resource types.

## Prerequisites
- Plan 001 (Networking Infrastructure) - KomodoRpcClient, ApiError
- Plan 002 (Core Domain DTOs) - All resource types

## Depends On
- `core-network` module with KomodoRpcClient
- `core-domain` module with resource DTOs

## Deliverables

### 1. Write API Client Interface

**File: `core-network/src/commonMain/kotlin/ca/glong/komodo/core/network/api/WriteApiClient.kt`**

```kotlin
interface WriteApiClient {
    // ═══════════════════════════════════════════════════════════════
    // SERVER OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun createServer(name: String, config: ServerConfig? = null): Result<Server>
    suspend fun updateServer(id: String, config: ServerConfig): Result<Server>
    suspend fun deleteServer(id: String): Result<NoData>
    suspend fun copyServer(id: String, newName: String): Result<Server>
    suspend fun renameServer(id: String, newName: String): Result<Update>

    // ═══════════════════════════════════════════════════════════════
    // DEPLOYMENT OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun createDeployment(name: String, config: DeploymentConfig? = null): Result<Deployment>
    suspend fun createDeploymentFromContainer(
        name: String, 
        server: String, 
        container: String
    ): Result<Deployment>
    suspend fun updateDeployment(id: String, config: DeploymentConfig): Result<Deployment>
    suspend fun deleteDeployment(id: String): Result<NoData>
    suspend fun copyDeployment(id: String, newName: String): Result<Deployment>
    suspend fun renameDeployment(id: String, newName: String): Result<Update>

    // ═══════════════════════════════════════════════════════════════
    // STACK OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun createStack(name: String, config: StackConfig? = null): Result<Stack>
    suspend fun updateStack(id: String, config: StackConfig): Result<Stack>
    suspend fun deleteStack(id: String): Result<NoData>
    suspend fun copyStack(id: String, newName: String): Result<Stack>
    suspend fun renameStack(id: String, newName: String): Result<Update>

    // ═══════════════════════════════════════════════════════════════
    // BUILD OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun createBuild(name: String, config: BuildConfig? = null): Result<Build>
    suspend fun updateBuild(id: String, config: BuildConfig): Result<Build>
    suspend fun deleteBuild(id: String): Result<NoData>
    suspend fun copyBuild(id: String, newName: String): Result<Build>
    suspend fun renameBuild(id: String, newName: String): Result<Update>

    // ═══════════════════════════════════════════════════════════════
    // BUILDER OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun createBuilder(name: String, config: BuilderConfig? = null): Result<Builder>
    suspend fun updateBuilder(id: String, config: BuilderConfig): Result<Builder>
    suspend fun deleteBuilder(id: String): Result<NoData>
    suspend fun copyBuilder(id: String, newName: String): Result<Builder>

    // ═══════════════════════════════════════════════════════════════
    // REPO OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun createRepo(name: String, config: RepoConfig? = null): Result<Repo>
    suspend fun updateRepo(id: String, config: RepoConfig): Result<Repo>
    suspend fun deleteRepo(id: String): Result<NoData>
    suspend fun copyRepo(id: String, newName: String): Result<Repo>
    suspend fun renameRepo(id: String, newName: String): Result<Update>

    // ═══════════════════════════════════════════════════════════════
    // ACTION OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun createAction(name: String, config: ActionConfig? = null): Result<Action>
    suspend fun updateAction(id: String, config: ActionConfig): Result<Action>
    suspend fun deleteAction(id: String): Result<NoData>
    suspend fun copyAction(id: String, newName: String): Result<Action>
    suspend fun renameAction(id: String, newName: String): Result<Update>

    // ═══════════════════════════════════════════════════════════════
    // PROCEDURE OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun createProcedure(name: String, config: ProcedureConfig? = null): Result<Procedure>
    suspend fun updateProcedure(id: String, config: ProcedureConfig): Result<Procedure>
    suspend fun deleteProcedure(id: String): Result<NoData>
    suspend fun copyProcedure(id: String, newName: String): Result<Procedure>
    suspend fun renameProcedure(id: String, newName: String): Result<Update>

    // ═══════════════════════════════════════════════════════════════
    // ALERTER OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun createAlerter(name: String, config: AlerterConfig? = null): Result<Alerter>
    suspend fun updateAlerter(id: String, config: AlerterConfig): Result<Alerter>
    suspend fun deleteAlerter(id: String): Result<NoData>
    suspend fun copyAlerter(id: String, newName: String): Result<Alerter>
    suspend fun renameAlerter(id: String, newName: String): Result<Update>

    // ═══════════════════════════════════════════════════════════════
    // RESOURCE SYNC OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun createResourceSync(name: String, config: ResourceSyncConfig? = null): Result<ResourceSync>
    suspend fun updateResourceSync(id: String, config: ResourceSyncConfig): Result<ResourceSync>
    suspend fun deleteResourceSync(id: String): Result<NoData>
    suspend fun copyResourceSync(id: String, newName: String): Result<ResourceSync>
    suspend fun renameResourceSync(id: String, newName: String): Result<Update>

    // ═══════════════════════════════════════════════════════════════
    // SWARM OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun createSwarm(name: String, config: SwarmConfig? = null): Result<Swarm>
    suspend fun updateSwarm(id: String, config: SwarmConfig): Result<Swarm>
    suspend fun deleteSwarm(id: String): Result<NoData>
    suspend fun copySwarm(id: String, newName: String): Result<Swarm>

    // ═══════════════════════════════════════════════════════════════
    // USER & GROUP OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun createLocalUser(username: String, password: String): Result<NoData>
    suspend fun createServiceUser(username: String, description: String): Result<CreateServiceUserResponse>
    suspend fun deleteUser(userId: String): Result<NoData>
    suspend fun updateUserEnabled(userId: String, enabled: Boolean): Result<NoData>
    suspend fun updateUserAdmin(userId: String, admin: Boolean): Result<NoData>
    suspend fun updateUserPassword(userId: String, password: String): Result<NoData>
    
    suspend fun createUserGroup(name: String): Result<UserGroup>
    suspend fun updateUserGroup(id: String, users: List<String>): Result<UserGroup>
    suspend fun deleteUserGroup(id: String): Result<NoData>
    suspend fun renameUserGroup(id: String, newName: String): Result<Update>
    suspend fun addUserToGroup(groupId: String, userId: String): Result<UserGroup>
    suspend fun removeUserFromGroup(groupId: String, userId: String): Result<UserGroup>

    // ═══════════════════════════════════════════════════════════════
    // VARIABLE & TAG OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun createVariable(
        name: String, 
        value: String = "", 
        description: String = "",
        isSecret: Boolean = false
    ): Result<Variable>
    suspend fun updateVariable(name: String, value: String): Result<Variable>
    suspend fun deleteVariable(name: String): Result<NoData>
    
    suspend fun createTag(name: String, color: String? = null): Result<Tag>
    suspend fun updateTag(id: String, color: String): Result<Tag>
    suspend fun deleteTag(id: String): Result<NoData>

    // ═══════════════════════════════════════════════════════════════
    // TERMINAL OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun createTerminal(
        name: String,
        target: TerminalTarget,
        command: String? = null,
        mode: TerminalMode? = null,
        recreate: Boolean? = null
    ): Result<Terminal>
    suspend fun deleteTerminal(target: TerminalTarget, terminal: String): Result<NoData>

    // ═══════════════════════════════════════════════════════════════
    // ACCOUNT OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun createDockerRegistryAccount(account: DockerRegistryAccount): Result<NoData>
    suspend fun updateDockerRegistryAccount(account: DockerRegistryAccount): Result<NoData>
    suspend fun deleteDockerRegistryAccount(domain: String, username: String): Result<NoData>
    
    suspend fun createGitProviderAccount(account: GitProviderAccount): Result<NoData>
    suspend fun updateGitProviderAccount(account: GitProviderAccount): Result<NoData>
    suspend fun deleteGitProviderAccount(domain: String, username: String): Result<NoData>

    // ═══════════════════════════════════════════════════════════════
    // SERVER RESOURCE OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun createNetwork(server: String, name: String, driver: String? = null): Result<NoData>
    suspend fun deleteNetwork(server: String, name: String): Result<NoData>
    suspend fun deleteImage(server: String, name: String): Result<NoData>
    suspend fun deleteVolume(server: String, name: String): Result<NoData>

    // ═══════════════════════════════════════════════════════════════
    // PERMISSION OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun updatePermission(
        target: UserTarget,
        resourceType: ResourceType,
        resourceId: String,
        permission: PermissionLevel
    ): Result<NoData>

    // ═══════════════════════════════════════════════════════════════
    // DESCRIPTION & TAGS OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun updateDescription(
        resourceType: ResourceType,
        resourceId: String,
        description: String
    ): Result<NoData>
    
    suspend fun updateResourceTags(
        resourceType: ResourceType,
        resourceId: String,
        tags: List<String>
    ): Result<NoData>

    // ═══════════════════════════════════════════════════════════════
    // ONBOARDING KEY OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    suspend fun createOnboardingKey(
        name: String,
        expires: Long? = null,
        copyServer: String? = null,
        createBuilder: Boolean? = null,
        fixExistingServers: Boolean? = null,
        privateKey: String? = null,
        tags: List<String>? = null
    ): Result<OnboardingKey>
    suspend fun deleteOnboardingKey(publicKey: String): Result<NoData>
}
```

### 2. Write API Client Implementation

**File: `core-network/.../api/WriteApiClientImpl.kt`**

```kotlin
@Single(binds = [WriteApiClient::class])
class WriteApiClientImpl(
    private val rpcClient: KomodoRpcClient
) : WriteApiClient {

    // ═══════════════════════════════════════════════════════════════
    // SERVER OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    
    override suspend fun createServer(
        name: String, 
        config: ServerConfig?
    ): Result<Server> =
        rpcClient.call(
            endpoint = RpcEndpoint.WRITE,
            operation = "CreateServer",
            params = CreateServerParams(name, config),
            paramsSerializer = CreateServerParams.serializer(),
            responseSerializer = Server.serializer()
        )

    override suspend fun updateServer(
        id: String, 
        config: ServerConfig
    ): Result<Server> =
        rpcClient.call(
            endpoint = RpcEndpoint.WRITE,
            operation = "UpdateServer",
            params = UpdateServerParams(id, config),
            paramsSerializer = UpdateServerParams.serializer(),
            responseSerializer = Server.serializer()
        )

    override suspend fun deleteServer(id: String): Result<NoData> =
        rpcClient.call(
            endpoint = RpcEndpoint.WRITE,
            operation = "DeleteServer",
            params = DeleteServerParams(id),
            paramsSerializer = DeleteServerParams.serializer(),
            responseSerializer = NoData.serializer()
        )

    override suspend fun copyServer(id: String, newName: String): Result<Server> =
        rpcClient.call(
            endpoint = RpcEndpoint.WRITE,
            operation = "CopyServer",
            params = CopyServerParams(id, newName),
            paramsSerializer = CopyServerParams.serializer(),
            responseSerializer = Server.serializer()
        )

    override suspend fun renameServer(id: String, newName: String): Result<Update> =
        rpcClient.call(
            endpoint = RpcEndpoint.WRITE,
            operation = "RenameServer",
            params = RenameServerParams(id, newName),
            paramsSerializer = RenameServerParams.serializer(),
            responseSerializer = Update.serializer()
        )

    // ═══════════════════════════════════════════════════════════════
    // DEPLOYMENT OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    
    override suspend fun createDeployment(
        name: String, 
        config: DeploymentConfig?
    ): Result<Deployment> =
        rpcClient.call(
            endpoint = RpcEndpoint.WRITE,
            operation = "CreateDeployment",
            params = CreateDeploymentParams(name, config),
            paramsSerializer = CreateDeploymentParams.serializer(),
            responseSerializer = Deployment.serializer()
        )

    override suspend fun createDeploymentFromContainer(
        name: String, 
        server: String, 
        container: String
    ): Result<Deployment> =
        rpcClient.call(
            endpoint = RpcEndpoint.WRITE,
            operation = "CreateDeploymentFromContainer",
            params = CreateDeploymentFromContainerParams(name, server, container),
            paramsSerializer = CreateDeploymentFromContainerParams.serializer(),
            responseSerializer = Deployment.serializer()
        )

    // ... continue pattern for all operations
    
    // ═══════════════════════════════════════════════════════════════
    // USER GROUP OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    
    override suspend fun addUserToGroup(
        groupId: String, 
        userId: String
    ): Result<UserGroup> =
        rpcClient.call(
            endpoint = RpcEndpoint.WRITE,
            operation = "AddUserToUserGroup",
            params = UserGroupMemberParams(groupId, userId),
            paramsSerializer = UserGroupMemberParams.serializer(),
            responseSerializer = UserGroup.serializer()
        )

    override suspend fun removeUserFromGroup(
        groupId: String, 
        userId: String
    ): Result<UserGroup> =
        rpcClient.call(
            endpoint = RpcEndpoint.WRITE,
            operation = "RemoveUserFromUserGroup",
            params = UserGroupMemberParams(groupId, userId),
            paramsSerializer = UserGroupMemberParams.serializer(),
            responseSerializer = UserGroup.serializer()
        )

    // ═══════════════════════════════════════════════════════════════
    // PERMISSION OPERATIONS
    // ═══════════════════════════════════════════════════════════════
    
    override suspend fun updatePermission(
        target: UserTarget,
        resourceType: ResourceType,
        resourceId: String,
        permission: PermissionLevel
    ): Result<NoData> =
        rpcClient.call(
            endpoint = RpcEndpoint.WRITE,
            operation = "UpdatePermission",
            params = UpdatePermissionParams(target, resourceType, resourceId, permission),
            paramsSerializer = UpdatePermissionParams.serializer(),
            responseSerializer = NoData.serializer()
        )
}
```

### 3. Request Parameter DTOs

**Files in `core-domain/.../model/write/params/`**

```kotlin
// ServerWriteParams.kt
@Serializable
data class CreateServerParams(
    val name: String,
    val config: ServerConfig? = null,
    @SerialName("public_key") val publicKey: String? = null
)

@Serializable
data class UpdateServerParams(
    val id: String,
    val config: ServerConfig
)

@Serializable
data class DeleteServerParams(val id: String)

@Serializable
data class CopyServerParams(
    val id: String,
    val name: String
)

@Serializable
data class RenameServerParams(
    val id: String,
    val name: String
)

// DeploymentWriteParams.kt
@Serializable
data class CreateDeploymentParams(
    val name: String,
    val config: DeploymentConfig? = null
)

@Serializable
data class CreateDeploymentFromContainerParams(
    val name: String,
    val server: String,
    val container: String
)

@Serializable
data class UpdateDeploymentParams(
    val id: String,
    val config: DeploymentConfig
)

@Serializable
data class DeleteDeploymentParams(val id: String)

@Serializable
data class CopyDeploymentParams(
    val id: String,
    val name: String
)

@Serializable
data class RenameDeploymentParams(
    val id: String,
    val name: String
)

// StackWriteParams.kt, BuildWriteParams.kt, etc. - same pattern

// UserWriteParams.kt
@Serializable
data class CreateLocalUserParams(
    val username: String,
    val password: String
)

@Serializable
data class CreateServiceUserParams(
    val username: String,
    val description: String
)

@Serializable
data class DeleteUserParams(
    val user: String
)

@Serializable
data class UpdateUserEnabledParams(
    val user: String,
    val enabled: Boolean
)

@Serializable
data class UpdateUserAdminParams(
    val user: String,
    val admin: Boolean
)

@Serializable
data class UpdateUserPasswordParams(
    val user: String,
    val password: String
)

// UserGroupWriteParams.kt
@Serializable
data class CreateUserGroupParams(val name: String)

@Serializable
data class UpdateUserGroupParams(
    val id: String,
    val users: List<String>
)

@Serializable
data class DeleteUserGroupParams(val id: String)

@Serializable
data class RenameUserGroupParams(
    val id: String,
    val name: String
)

@Serializable
data class UserGroupMemberParams(
    @SerialName("user_group") val userGroup: String,
    val user: String
)

// VariableWriteParams.kt
@Serializable
data class CreateVariableParams(
    val name: String,
    val value: String = "",
    val description: String = "",
    @SerialName("is_secret") val isSecret: Boolean = false
)

@Serializable
data class UpdateVariableParams(
    val name: String,
    val value: String
)

@Serializable
data class DeleteVariableParams(val name: String)

// TagWriteParams.kt
@Serializable
data class CreateTagParams(
    val name: String,
    val color: String? = null
)

@Serializable
data class UpdateTagParams(
    val id: String,
    val color: String
)

@Serializable
data class DeleteTagParams(val id: String)

// TerminalWriteParams.kt
@Serializable
data class CreateTerminalParams(
    val name: String,
    val target: TerminalTarget,
    val command: String? = null,
    val mode: TerminalMode? = null,
    val recreate: Boolean? = null
)

@Serializable
data class DeleteTerminalParams(
    val target: TerminalTarget,
    val terminal: String
)

@Serializable
enum class TerminalMode {
    @SerialName("shell") SHELL,
    @SerialName("exec") EXEC
}

// AccountWriteParams.kt
@Serializable
data class DeleteDockerRegistryAccountParams(
    val domain: String,
    val username: String
)

@Serializable
data class DeleteGitProviderAccountParams(
    val domain: String,
    val username: String
)

// ServerResourceWriteParams.kt
@Serializable
data class CreateNetworkParams(
    val server: String,
    val name: String,
    val driver: String? = null
)

@Serializable
data class DeleteNetworkParams(
    val server: String,
    val name: String
)

@Serializable
data class DeleteImageParams(
    val server: String,
    val name: String
)

@Serializable
data class DeleteVolumeParams(
    val server: String,
    val name: String
)

// PermissionWriteParams.kt
@Serializable
data class UpdatePermissionParams(
    val target: UserTarget,
    @SerialName("resource_type") val resourceType: ResourceType,
    @SerialName("resource_id") val resourceId: String,
    val permission: PermissionLevel
)

// DescriptionWriteParams.kt
@Serializable
data class UpdateDescriptionParams(
    @SerialName("resource_type") val resourceType: ResourceType,
    @SerialName("resource_id") val resourceId: String,
    val description: String
)

@Serializable
data class UpdateResourceTagsParams(
    @SerialName("resource_type") val resourceType: ResourceType,
    @SerialName("resource_id") val resourceId: String,
    val tags: List<String>
)

// OnboardingKeyWriteParams.kt
@Serializable
data class CreateOnboardingKeyParams(
    val name: String,
    val expires: Long? = null,
    @SerialName("copy_server") val copyServer: String? = null,
    @SerialName("create_builder") val createBuilder: Boolean? = null,
    @SerialName("fix_existing_servers") val fixExistingServers: Boolean? = null,
    @SerialName("private_key") val privateKey: String? = null,
    val tags: List<String>? = null
)

@Serializable
data class DeleteOnboardingKeyParams(
    @SerialName("public_key") val publicKey: String
)
```

### 4. Response DTOs (additions)

```kotlin
// response/CreateServiceUserResponse.kt
@Serializable
data class CreateServiceUserResponse(
    val username: String,
    val secret: String
)

// response/OnboardingKey.kt
@Serializable
data class OnboardingKey(
    val name: String,
    @SerialName("public_key") val publicKey: String,
    @SerialName("private_key") val privateKey: String? = null,
    val expires: Long? = null,
    val tags: List<String> = emptyList()
)
```

## Write Operations Reference (from OpenAPI)

### CRUD Pattern (per resource type)
Each major resource type has these operations:
- `Create{Resource}` - Create new resource
- `Update{Resource}` - Update existing resource  
- `Delete{Resource}` - Delete resource
- `Copy{Resource}` - Clone resource with new name
- `Rename{Resource}` - Rename resource (not all types)

### Resource Types Supporting CRUD
| Resource | Create | Update | Delete | Copy | Rename |
|----------|--------|--------|--------|------|--------|
| Server | ✓ | ✓ | ✓ | ✓ | ✓ |
| Deployment | ✓ | ✓ | ✓ | ✓ | ✓ |
| Stack | ✓ | ✓ | ✓ | ✓ | ✓ |
| Build | ✓ | ✓ | ✓ | ✓ | ✓ |
| Builder | ✓ | ✓ | ✓ | ✓ | - |
| Repo | ✓ | ✓ | ✓ | ✓ | ✓ |
| Action | ✓ | ✓ | ✓ | ✓ | ✓ |
| Procedure | ✓ | ✓ | ✓ | ✓ | ✓ |
| Alerter | ✓ | ✓ | ✓ | ✓ | ✓ |
| ResourceSync | ✓ | ✓ | ✓ | ✓ | ✓ |
| Swarm | ✓ | ✓ | ✓ | ✓ | - |

### User & Group Operations
| Operation | Params |
|-----------|--------|
| CreateLocalUser | `{username, password}` |
| CreateServiceUser | `{username, description}` |
| DeleteUser | `{user}` |
| UpdateUserEnabled | `{user, enabled}` |
| UpdateUserAdmin | `{user, admin}` |
| UpdateUserPassword | `{user, password}` |
| CreateUserGroup | `{name}` |
| UpdateUserGroup | `{id, users}` |
| DeleteUserGroup | `{id}` |
| RenameUserGroup | `{id, name}` |
| AddUserToUserGroup | `{user_group, user}` |
| RemoveUserFromUserGroup | `{user_group, user}` |

### Variable & Tag Operations
| Operation | Params |
|-----------|--------|
| CreateVariable | `{name, value?, description?, is_secret?}` |
| UpdateVariable | `{name, value}` |
| DeleteVariable | `{name}` |
| CreateTag | `{name, color?}` |
| UpdateTag | `{id, color}` |
| DeleteTag | `{id}` |

### Server Resource Operations
| Operation | Params |
|-----------|--------|
| CreateNetwork | `{server, name, driver?}` |
| DeleteNetwork | `{server, name}` |
| DeleteImage | `{server, name}` |
| DeleteVolume | `{server, name}` |

## Tests

### Unit Tests
**File: `core-network/src/commonTest/kotlin/.../api/WriteApiClientTest.kt`**

```kotlin
class WriteApiClientTest {
    private lateinit var mockRpcClient: MockKomodoRpcClient
    private lateinit var writeClient: WriteApiClient

    @BeforeTest
    fun setup() {
        mockRpcClient = MockKomodoRpcClient()
        writeClient = WriteApiClientImpl(mockRpcClient)
    }

    // Server tests
    @Test
    fun `createServer returns created server`() = runTest {
        val config = ServerConfig(address = "192.168.1.1")
        val expected = Server(id = "srv_123", name = "new-server", config = config)
        mockRpcClient.mockResponse(expected)

        val result = writeClient.createServer("new-server", config)

        assertTrue(result.isSuccess)
        assertEquals("new-server", result.getOrThrow().name)
        mockRpcClient.verifyCall("CreateServer", CreateServerParams("new-server", config))
    }

    @Test
    fun `updateServer returns updated server`() = runTest {
        val config = ServerConfig(address = "192.168.1.2")
        val expected = Server(id = "srv_123", name = "server", config = config)
        mockRpcClient.mockResponse(expected)

        val result = writeClient.updateServer("srv_123", config)

        assertTrue(result.isSuccess)
        assertEquals("192.168.1.2", result.getOrThrow().config.address)
    }

    @Test
    fun `deleteServer returns NoData on success`() = runTest {
        mockRpcClient.mockResponse(NoData)

        val result = writeClient.deleteServer("srv_123")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `copyServer returns new server with new name`() = runTest {
        val expected = Server(id = "srv_456", name = "server-copy", config = ServerConfig(address = "1.1.1.1"))
        mockRpcClient.mockResponse(expected)

        val result = writeClient.copyServer("srv_123", "server-copy")

        assertTrue(result.isSuccess)
        assertEquals("server-copy", result.getOrThrow().name)
    }

    // Deployment tests
    @Test
    fun `createDeploymentFromContainer creates deployment from existing container`() = runTest {
        val expected = Deployment(id = "dep_123", name = "from-nginx", config = DeploymentConfig())
        mockRpcClient.mockResponse(expected)

        val result = writeClient.createDeploymentFromContainer(
            name = "from-nginx",
            server = "srv_1",
            container = "nginx-container"
        )

        assertTrue(result.isSuccess)
        mockRpcClient.verifyCall(
            "CreateDeploymentFromContainer",
            CreateDeploymentFromContainerParams("from-nginx", "srv_1", "nginx-container")
        )
    }

    // User group tests
    @Test
    fun `addUserToGroup returns updated group`() = runTest {
        val expected = UserGroup(id = "grp_1", name = "admins", users = listOf("user1", "user2"))
        mockRpcClient.mockResponse(expected)

        val result = writeClient.addUserToGroup("grp_1", "user2")

        assertTrue(result.isSuccess)
        assertTrue(result.getOrThrow().users.contains("user2"))
    }

    // Variable tests
    @Test
    fun `createVariable with secret flag`() = runTest {
        val expected = Variable(name = "API_KEY", value = "***", description = "Secret key", isSecret = true)
        mockRpcClient.mockResponse(expected)

        val result = writeClient.createVariable(
            name = "API_KEY",
            value = "abc123",
            description = "Secret key",
            isSecret = true
        )

        assertTrue(result.isSuccess)
        mockRpcClient.verifyCall(
            "CreateVariable",
            CreateVariableParams("API_KEY", "abc123", "Secret key", true)
        )
    }

    // Permission tests
    @Test
    fun `updatePermission sets resource permission`() = runTest {
        mockRpcClient.mockResponse(NoData)

        val result = writeClient.updatePermission(
            target = UserTarget.User(UserParams("user_123")),
            resourceType = ResourceType.DEPLOYMENT,
            resourceId = "dep_456",
            permission = PermissionLevel.EXECUTE
        )

        assertTrue(result.isSuccess)
    }

    // Error tests
    @Test
    fun `deleteServer returns NotFound for missing server`() = runTest {
        mockRpcClient.mockError(ApiError.NotFound("Server"))

        val result = writeClient.deleteServer("nonexistent")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is ApiError.NotFound)
    }

    @Test
    fun `createVariable returns error for duplicate name`() = runTest {
        mockRpcClient.mockError(ApiError.BadRequest(listOf("Variable already exists")))

        val result = writeClient.createVariable("existing_var", "value")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is ApiError.BadRequest)
    }
}
```

## File Structure (Final)
```
core-network/
└── src/
    ├── commonMain/kotlin/ca/glong/komodo/core/network/api/
    │   ├── WriteApiClient.kt
    │   └── WriteApiClientImpl.kt
    └── commonTest/kotlin/ca/glong/komodo/core/network/api/
        └── WriteApiClientTest.kt

core-domain/
└── src/commonMain/kotlin/ca/glong/komodo/core/domain/model/
    ├── write/
    │   └── params/
    │       ├── AccountWriteParams.kt
    │       ├── BuildWriteParams.kt
    │       ├── BuilderWriteParams.kt
    │       ├── DeploymentWriteParams.kt
    │       ├── DescriptionWriteParams.kt
    │       ├── OnboardingKeyWriteParams.kt
    │       ├── PermissionWriteParams.kt
    │       ├── ServerResourceWriteParams.kt
    │       ├── ServerWriteParams.kt
    │       ├── StackWriteParams.kt
    │       ├── TagWriteParams.kt
    │       ├── TerminalWriteParams.kt
    │       ├── UserGroupWriteParams.kt
    │       ├── UserWriteParams.kt
    │       └── VariableWriteParams.kt
    └── response/
        ├── CreateServiceUserResponse.kt
        └── OnboardingKey.kt
```

## Acceptance Criteria
- [ ] `./gradlew :core-network:test` passes
- [ ] WriteApiClient covers all ~100 write operations
- [ ] All CRUD operations work for each resource type
- [ ] Copy and Rename operations work where supported
- [ ] User and permission operations work correctly
- [ ] Server resource operations (network, volume, image deletion) work
- [ ] Error cases handled appropriately

## Estimated Effort
- Interface definition: 2 hours
- Implementation (~100 methods): 5 hours
- Param DTOs: 3 hours
- Tests: 4 hours
- **Total: 14-16 hours**
