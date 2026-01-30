# ExecuteApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**backupCoreDatabase**](ExecuteApi.md#backupCoreDatabase) | **POST** /execute/BackupCoreDatabase |  |
| [**batchBuildRepo**](ExecuteApi.md#batchBuildRepo) | **POST** /execute/BatchBuildRepo |  |
| [**batchCloneRepo**](ExecuteApi.md#batchCloneRepo) | **POST** /execute/BatchCloneRepo |  |
| [**batchDeploy**](ExecuteApi.md#batchDeploy) | **POST** /execute/BatchDeploy |  |
| [**batchDeployStack**](ExecuteApi.md#batchDeployStack) | **POST** /execute/BatchDeployStack |  |
| [**batchDeployStackIfChanged**](ExecuteApi.md#batchDeployStackIfChanged) | **POST** /execute/BatchDeployStackIfChanged |  |
| [**batchDestroyDeployment**](ExecuteApi.md#batchDestroyDeployment) | **POST** /execute/BatchDestroyDeployment |  |
| [**batchDestroyStack**](ExecuteApi.md#batchDestroyStack) | **POST** /execute/BatchDestroyStack |  |
| [**batchPullRepo**](ExecuteApi.md#batchPullRepo) | **POST** /execute/BatchPullRepo |  |
| [**batchPullStack**](ExecuteApi.md#batchPullStack) | **POST** /execute/BatchPullStack |  |
| [**batchRunAction**](ExecuteApi.md#batchRunAction) | **POST** /execute/BatchRunAction |  |
| [**batchRunBuild**](ExecuteApi.md#batchRunBuild) | **POST** /execute/BatchRunBuild |  |
| [**batchRunProcedure**](ExecuteApi.md#batchRunProcedure) | **POST** /execute/BatchRunProcedure |  |
| [**buildRepo**](ExecuteApi.md#buildRepo) | **POST** /execute/BuildRepo |  |
| [**cancelBuild**](ExecuteApi.md#cancelBuild) | **POST** /execute/CancelBuild |  |
| [**cancelRepoBuild**](ExecuteApi.md#cancelRepoBuild) | **POST** /execute/CancelRepoBuild |  |
| [**clearRepoCache**](ExecuteApi.md#clearRepoCache) | **POST** /execute/ClearRepoCache |  |
| [**cloneRepo**](ExecuteApi.md#cloneRepo) | **POST** /execute/CloneRepo |  |
| [**createSwarmConfig**](ExecuteApi.md#createSwarmConfig) | **POST** /execute/CreateSwarmConfig |  |
| [**createSwarmSecret**](ExecuteApi.md#createSwarmSecret) | **POST** /execute/CreateSwarmSecret |  |
| [**deleteImage**](ExecuteApi.md#deleteImage) | **POST** /execute/DeleteImage |  |
| [**deleteNetwork**](ExecuteApi.md#deleteNetwork) | **POST** /execute/DeleteNetwork |  |
| [**deleteVolume**](ExecuteApi.md#deleteVolume) | **POST** /execute/DeleteVolume |  |
| [**deploy**](ExecuteApi.md#deploy) | **POST** /execute/Deploy |  |
| [**deployStack**](ExecuteApi.md#deployStack) | **POST** /execute/DeployStack |  |
| [**deployStackIfChanged**](ExecuteApi.md#deployStackIfChanged) | **POST** /execute/DeployStackIfChanged |  |
| [**destroyContainer**](ExecuteApi.md#destroyContainer) | **POST** /execute/DestroyContainer |  |
| [**destroyDeployment**](ExecuteApi.md#destroyDeployment) | **POST** /execute/DestroyDeployment |  |
| [**destroyStack**](ExecuteApi.md#destroyStack) | **POST** /execute/DestroyStack |  |
| [**globalAutoUpdate**](ExecuteApi.md#globalAutoUpdate) | **POST** /execute/GlobalAutoUpdate |  |
| [**pauseAllContainers**](ExecuteApi.md#pauseAllContainers) | **POST** /execute/PauseAllContainers |  |
| [**pauseContainer**](ExecuteApi.md#pauseContainer) | **POST** /execute/PauseContainer |  |
| [**pauseDeployment**](ExecuteApi.md#pauseDeployment) | **POST** /execute/PauseDeployment |  |
| [**pauseStack**](ExecuteApi.md#pauseStack) | **POST** /execute/PauseStack |  |
| [**pruneBuildx**](ExecuteApi.md#pruneBuildx) | **POST** /execute/PruneBuildx |  |
| [**pruneContainers**](ExecuteApi.md#pruneContainers) | **POST** /execute/PruneContainers |  |
| [**pruneDockerBuilders**](ExecuteApi.md#pruneDockerBuilders) | **POST** /execute/PruneDockerBuilders |  |
| [**pruneImages**](ExecuteApi.md#pruneImages) | **POST** /execute/PruneImages |  |
| [**pruneNetworks**](ExecuteApi.md#pruneNetworks) | **POST** /execute/PruneNetworks |  |
| [**pruneSystem**](ExecuteApi.md#pruneSystem) | **POST** /execute/PruneSystem |  |
| [**pruneVolumes**](ExecuteApi.md#pruneVolumes) | **POST** /execute/PruneVolumes |  |
| [**pullDeployment**](ExecuteApi.md#pullDeployment) | **POST** /execute/PullDeployment |  |
| [**pullRepo**](ExecuteApi.md#pullRepo) | **POST** /execute/PullRepo |  |
| [**pullStack**](ExecuteApi.md#pullStack) | **POST** /execute/PullStack |  |
| [**removeSwarmConfigs**](ExecuteApi.md#removeSwarmConfigs) | **POST** /execute/RemoveSwarmConfigs |  |
| [**removeSwarmNodes**](ExecuteApi.md#removeSwarmNodes) | **POST** /execute/RemoveSwarmNodes |  |
| [**removeSwarmSecrets**](ExecuteApi.md#removeSwarmSecrets) | **POST** /execute/RemoveSwarmSecrets |  |
| [**removeSwarmServices**](ExecuteApi.md#removeSwarmServices) | **POST** /execute/RemoveSwarmServices |  |
| [**removeSwarmStacks**](ExecuteApi.md#removeSwarmStacks) | **POST** /execute/RemoveSwarmStacks |  |
| [**restartAllContainers**](ExecuteApi.md#restartAllContainers) | **POST** /execute/RestartAllContainers |  |
| [**restartContainer**](ExecuteApi.md#restartContainer) | **POST** /execute/RestartContainer |  |
| [**restartDeployment**](ExecuteApi.md#restartDeployment) | **POST** /execute/RestartDeployment |  |
| [**restartStack**](ExecuteApi.md#restartStack) | **POST** /execute/RestartStack |  |
| [**rotateAllServerKeys**](ExecuteApi.md#rotateAllServerKeys) | **POST** /execute/RotateAllServerKeys |  |
| [**rotateCoreKeys**](ExecuteApi.md#rotateCoreKeys) | **POST** /execute/RotateCoreKeys |  |
| [**rotateSwarmConfig**](ExecuteApi.md#rotateSwarmConfig) | **POST** /execute/RotateSwarmConfig |  |
| [**rotateSwarmSecret**](ExecuteApi.md#rotateSwarmSecret) | **POST** /execute/RotateSwarmSecret |  |
| [**runAction**](ExecuteApi.md#runAction) | **POST** /execute/RunAction |  |
| [**runBuild**](ExecuteApi.md#runBuild) | **POST** /execute/RunBuild |  |
| [**runProcedure**](ExecuteApi.md#runProcedure) | **POST** /execute/RunProcedure |  |
| [**runStackService**](ExecuteApi.md#runStackService) | **POST** /execute/RunStackService |  |
| [**runSync**](ExecuteApi.md#runSync) | **POST** /execute/RunSync |  |
| [**sendAlert**](ExecuteApi.md#sendAlert) | **POST** /execute/SendAlert |  |
| [**startAllContainers**](ExecuteApi.md#startAllContainers) | **POST** /execute/StartAllContainers |  |
| [**startContainer**](ExecuteApi.md#startContainer) | **POST** /execute/StartContainer |  |
| [**startDeployment**](ExecuteApi.md#startDeployment) | **POST** /execute/StartDeployment |  |
| [**startStack**](ExecuteApi.md#startStack) | **POST** /execute/StartStack |  |
| [**stopAllContainers**](ExecuteApi.md#stopAllContainers) | **POST** /execute/StopAllContainers |  |
| [**stopContainer**](ExecuteApi.md#stopContainer) | **POST** /execute/StopContainer |  |
| [**stopDeployment**](ExecuteApi.md#stopDeployment) | **POST** /execute/StopDeployment |  |
| [**stopStack**](ExecuteApi.md#stopStack) | **POST** /execute/StopStack |  |
| [**testAlerter**](ExecuteApi.md#testAlerter) | **POST** /execute/TestAlerter |  |
| [**unpauseAllContainers**](ExecuteApi.md#unpauseAllContainers) | **POST** /execute/UnpauseAllContainers |  |
| [**unpauseContainer**](ExecuteApi.md#unpauseContainer) | **POST** /execute/UnpauseContainer |  |
| [**unpauseDeployment**](ExecuteApi.md#unpauseDeployment) | **POST** /execute/UnpauseDeployment |  |
| [**unpauseStack**](ExecuteApi.md#unpauseStack) | **POST** /execute/UnpauseStack |  |


<a id="backupCoreDatabase"></a>
# **backupCoreDatabase**
> Update backupCoreDatabase(body)



Backs up the Komodo Core database to compressed jsonl files.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val body : kotlin.String = Object // kotlin.String | 
try {
    val result : Update = apiInstance.backupCoreDatabase(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#backupCoreDatabase")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#backupCoreDatabase")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | **kotlin.String**|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="batchBuildRepo"></a>
# **batchBuildRepo**
> kotlin.collections.List&lt;VecInner&gt; batchBuildRepo(batchBuildRepo)



Builds multiple Repos in parallel that match pattern.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val batchBuildRepo : BatchBuildRepo =  // BatchBuildRepo | 
try {
    val result : kotlin.collections.List<VecInner> = apiInstance.batchBuildRepo(batchBuildRepo)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#batchBuildRepo")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#batchBuildRepo")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **batchBuildRepo** | [**BatchBuildRepo**](BatchBuildRepo.md)|  | |

### Return type

[**kotlin.collections.List&lt;VecInner&gt;**](VecInner.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="batchCloneRepo"></a>
# **batchCloneRepo**
> kotlin.collections.List&lt;VecInner&gt; batchCloneRepo(batchCloneRepo)



Clones multiple Repos in parallel that match pattern.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val batchCloneRepo : BatchCloneRepo =  // BatchCloneRepo | 
try {
    val result : kotlin.collections.List<VecInner> = apiInstance.batchCloneRepo(batchCloneRepo)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#batchCloneRepo")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#batchCloneRepo")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **batchCloneRepo** | [**BatchCloneRepo**](BatchCloneRepo.md)|  | |

### Return type

[**kotlin.collections.List&lt;VecInner&gt;**](VecInner.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="batchDeploy"></a>
# **batchDeploy**
> kotlin.collections.List&lt;VecInner&gt; batchDeploy(batchDeploy)



Deploys multiple Deployments in parallel that match pattern.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val batchDeploy : BatchDeploy =  // BatchDeploy | 
try {
    val result : kotlin.collections.List<VecInner> = apiInstance.batchDeploy(batchDeploy)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#batchDeploy")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#batchDeploy")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **batchDeploy** | [**BatchDeploy**](BatchDeploy.md)|  | |

### Return type

[**kotlin.collections.List&lt;VecInner&gt;**](VecInner.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="batchDeployStack"></a>
# **batchDeployStack**
> kotlin.collections.List&lt;VecInner&gt; batchDeployStack(batchDeployStack)



Deploys multiple Stacks in parallel that match pattern.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val batchDeployStack : BatchDeployStack =  // BatchDeployStack | 
try {
    val result : kotlin.collections.List<VecInner> = apiInstance.batchDeployStack(batchDeployStack)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#batchDeployStack")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#batchDeployStack")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **batchDeployStack** | [**BatchDeployStack**](BatchDeployStack.md)|  | |

### Return type

[**kotlin.collections.List&lt;VecInner&gt;**](VecInner.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="batchDeployStackIfChanged"></a>
# **batchDeployStackIfChanged**
> kotlin.collections.List&lt;VecInner&gt; batchDeployStackIfChanged(batchDeployStackIfChanged)



Deploys multiple Stacks if changed in parallel that match pattern.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val batchDeployStackIfChanged : BatchDeployStackIfChanged =  // BatchDeployStackIfChanged | 
try {
    val result : kotlin.collections.List<VecInner> = apiInstance.batchDeployStackIfChanged(batchDeployStackIfChanged)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#batchDeployStackIfChanged")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#batchDeployStackIfChanged")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **batchDeployStackIfChanged** | [**BatchDeployStackIfChanged**](BatchDeployStackIfChanged.md)|  | |

### Return type

[**kotlin.collections.List&lt;VecInner&gt;**](VecInner.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="batchDestroyDeployment"></a>
# **batchDestroyDeployment**
> kotlin.collections.List&lt;VecInner&gt; batchDestroyDeployment(batchDestroyDeployment)



Destroys multiple Deployments in parallel that match pattern.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val batchDestroyDeployment : BatchDestroyDeployment =  // BatchDestroyDeployment | 
try {
    val result : kotlin.collections.List<VecInner> = apiInstance.batchDestroyDeployment(batchDestroyDeployment)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#batchDestroyDeployment")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#batchDestroyDeployment")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **batchDestroyDeployment** | [**BatchDestroyDeployment**](BatchDestroyDeployment.md)|  | |

### Return type

[**kotlin.collections.List&lt;VecInner&gt;**](VecInner.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="batchDestroyStack"></a>
# **batchDestroyStack**
> kotlin.collections.List&lt;VecInner&gt; batchDestroyStack(batchDestroyStack)



Destroys multiple Stacks in parallel that match pattern.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val batchDestroyStack : BatchDestroyStack =  // BatchDestroyStack | 
try {
    val result : kotlin.collections.List<VecInner> = apiInstance.batchDestroyStack(batchDestroyStack)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#batchDestroyStack")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#batchDestroyStack")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **batchDestroyStack** | [**BatchDestroyStack**](BatchDestroyStack.md)|  | |

### Return type

[**kotlin.collections.List&lt;VecInner&gt;**](VecInner.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="batchPullRepo"></a>
# **batchPullRepo**
> kotlin.collections.List&lt;VecInner&gt; batchPullRepo(batchPullRepo)



Pulls multiple Repos in parallel that match pattern.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val batchPullRepo : BatchPullRepo =  // BatchPullRepo | 
try {
    val result : kotlin.collections.List<VecInner> = apiInstance.batchPullRepo(batchPullRepo)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#batchPullRepo")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#batchPullRepo")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **batchPullRepo** | [**BatchPullRepo**](BatchPullRepo.md)|  | |

### Return type

[**kotlin.collections.List&lt;VecInner&gt;**](VecInner.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="batchPullStack"></a>
# **batchPullStack**
> kotlin.collections.List&lt;VecInner&gt; batchPullStack(batchPullStack)



Pulls multiple Stacks in parallel that match pattern.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val batchPullStack : BatchPullStack =  // BatchPullStack | 
try {
    val result : kotlin.collections.List<VecInner> = apiInstance.batchPullStack(batchPullStack)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#batchPullStack")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#batchPullStack")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **batchPullStack** | [**BatchPullStack**](BatchPullStack.md)|  | |

### Return type

[**kotlin.collections.List&lt;VecInner&gt;**](VecInner.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="batchRunAction"></a>
# **batchRunAction**
> kotlin.collections.List&lt;VecInner&gt; batchRunAction(batchRunAction)



Runs multiple Actions in parallel that match pattern.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val batchRunAction : BatchRunAction =  // BatchRunAction | 
try {
    val result : kotlin.collections.List<VecInner> = apiInstance.batchRunAction(batchRunAction)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#batchRunAction")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#batchRunAction")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **batchRunAction** | [**BatchRunAction**](BatchRunAction.md)|  | |

### Return type

[**kotlin.collections.List&lt;VecInner&gt;**](VecInner.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="batchRunBuild"></a>
# **batchRunBuild**
> kotlin.collections.List&lt;VecInner&gt; batchRunBuild(batchRunBuild)



Runs multiple builds in parallel that match pattern.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val batchRunBuild : BatchRunBuild =  // BatchRunBuild | 
try {
    val result : kotlin.collections.List<VecInner> = apiInstance.batchRunBuild(batchRunBuild)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#batchRunBuild")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#batchRunBuild")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **batchRunBuild** | [**BatchRunBuild**](BatchRunBuild.md)|  | |

### Return type

[**kotlin.collections.List&lt;VecInner&gt;**](VecInner.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="batchRunProcedure"></a>
# **batchRunProcedure**
> kotlin.collections.List&lt;VecInner&gt; batchRunProcedure(batchRunProcedure)



Runs multiple Procedures in parallel that match pattern.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val batchRunProcedure : BatchRunProcedure =  // BatchRunProcedure | 
try {
    val result : kotlin.collections.List<VecInner> = apiInstance.batchRunProcedure(batchRunProcedure)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#batchRunProcedure")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#batchRunProcedure")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **batchRunProcedure** | [**BatchRunProcedure**](BatchRunProcedure.md)|  | |

### Return type

[**kotlin.collections.List&lt;VecInner&gt;**](VecInner.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="buildRepo"></a>
# **buildRepo**
> Update buildRepo(buildRepo)



Builds the target repo, using the attached builder.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val buildRepo : BuildRepo =  // BuildRepo | 
try {
    val result : Update = apiInstance.buildRepo(buildRepo)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#buildRepo")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#buildRepo")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **buildRepo** | [**BuildRepo**](BuildRepo.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="cancelBuild"></a>
# **cancelBuild**
> Update cancelBuild(cancelBuild)



Cancels the target build.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val cancelBuild : CancelBuild =  // CancelBuild | 
try {
    val result : Update = apiInstance.cancelBuild(cancelBuild)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#cancelBuild")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#cancelBuild")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cancelBuild** | [**CancelBuild**](CancelBuild.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="cancelRepoBuild"></a>
# **cancelRepoBuild**
> Update cancelRepoBuild(cancelRepoBuild)



Cancels the target repo build.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val cancelRepoBuild : CancelRepoBuild =  // CancelRepoBuild | 
try {
    val result : Update = apiInstance.cancelRepoBuild(cancelRepoBuild)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#cancelRepoBuild")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#cancelRepoBuild")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cancelRepoBuild** | [**CancelRepoBuild**](CancelRepoBuild.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="clearRepoCache"></a>
# **clearRepoCache**
> Update clearRepoCache(body)



Clears all repos from the Core repo cache.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val body : kotlin.String = Object // kotlin.String | 
try {
    val result : Update = apiInstance.clearRepoCache(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#clearRepoCache")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#clearRepoCache")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | **kotlin.String**|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="cloneRepo"></a>
# **cloneRepo**
> Update cloneRepo(cloneRepo)



Clones the target repo.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val cloneRepo : CloneRepo =  // CloneRepo | 
try {
    val result : Update = apiInstance.cloneRepo(cloneRepo)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#cloneRepo")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#cloneRepo")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **cloneRepo** | [**CloneRepo**](CloneRepo.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="createSwarmConfig"></a>
# **createSwarmConfig**
> Update createSwarmConfig(createSwarmConfig)



Create a swarm config.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val createSwarmConfig : CreateSwarmConfig =  // CreateSwarmConfig | 
try {
    val result : Update = apiInstance.createSwarmConfig(createSwarmConfig)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#createSwarmConfig")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#createSwarmConfig")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **createSwarmConfig** | [**CreateSwarmConfig**](CreateSwarmConfig.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="createSwarmSecret"></a>
# **createSwarmSecret**
> Update createSwarmSecret(createSwarmSecret)



Create a swarm secret.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val createSwarmSecret : CreateSwarmSecret =  // CreateSwarmSecret | 
try {
    val result : Update = apiInstance.createSwarmSecret(createSwarmSecret)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#createSwarmSecret")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#createSwarmSecret")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **createSwarmSecret** | [**CreateSwarmSecret**](CreateSwarmSecret.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="deleteImage"></a>
# **deleteImage**
> Update deleteImage(deleteImage)



Delete a docker image.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val deleteImage : DeleteImage =  // DeleteImage | 
try {
    val result : Update = apiInstance.deleteImage(deleteImage)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#deleteImage")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#deleteImage")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **deleteImage** | [**DeleteImage**](DeleteImage.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="deleteNetwork"></a>
# **deleteNetwork**
> Update deleteNetwork(deleteNetwork)



Delete a docker network.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val deleteNetwork : DeleteNetwork =  // DeleteNetwork | 
try {
    val result : Update = apiInstance.deleteNetwork(deleteNetwork)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#deleteNetwork")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#deleteNetwork")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **deleteNetwork** | [**DeleteNetwork**](DeleteNetwork.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="deleteVolume"></a>
# **deleteVolume**
> Update deleteVolume(deleteVolume)



Delete a docker volume.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val deleteVolume : DeleteVolume =  // DeleteVolume | 
try {
    val result : Update = apiInstance.deleteVolume(deleteVolume)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#deleteVolume")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#deleteVolume")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **deleteVolume** | [**DeleteVolume**](DeleteVolume.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="deploy"></a>
# **deploy**
> Update deploy(deploy)



Deploys the container / swarm service for the target Deployment.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val deploy : Deploy =  // Deploy | 
try {
    val result : Update = apiInstance.deploy(deploy)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#deploy")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#deploy")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **deploy** | [**Deploy**](Deploy.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="deployStack"></a>
# **deployStack**
> Update deployStack(deployStack)



Deploys the target stack.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val deployStack : DeployStack =  // DeployStack | 
try {
    val result : Update = apiInstance.deployStack(deployStack)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#deployStack")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#deployStack")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **deployStack** | [**DeployStack**](DeployStack.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="deployStackIfChanged"></a>
# **deployStackIfChanged**
> Update deployStackIfChanged(deployStackIfChanged)



Checks deployed contents vs latest contents and deploys if changed.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val deployStackIfChanged : DeployStackIfChanged =  // DeployStackIfChanged | 
try {
    val result : Update = apiInstance.deployStackIfChanged(deployStackIfChanged)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#deployStackIfChanged")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#deployStackIfChanged")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **deployStackIfChanged** | [**DeployStackIfChanged**](DeployStackIfChanged.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="destroyContainer"></a>
# **destroyContainer**
> Update destroyContainer(destroyContainer)



Stops and destroys the container on the target server.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val destroyContainer : DestroyContainer =  // DestroyContainer | 
try {
    val result : Update = apiInstance.destroyContainer(destroyContainer)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#destroyContainer")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#destroyContainer")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **destroyContainer** | [**DestroyContainer**](DestroyContainer.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="destroyDeployment"></a>
# **destroyDeployment**
> Update destroyDeployment(destroyDeployment)



Destroys the container for the target deployment.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val destroyDeployment : DestroyDeployment =  // DestroyDeployment | 
try {
    val result : Update = apiInstance.destroyDeployment(destroyDeployment)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#destroyDeployment")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#destroyDeployment")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **destroyDeployment** | [**DestroyDeployment**](DestroyDeployment.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="destroyStack"></a>
# **destroyStack**
> Update destroyStack(destroyStack)



Destroys the target stack.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val destroyStack : DestroyStack =  // DestroyStack | 
try {
    val result : Update = apiInstance.destroyStack(destroyStack)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#destroyStack")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#destroyStack")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **destroyStack** | [**DestroyStack**](DestroyStack.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="globalAutoUpdate"></a>
# **globalAutoUpdate**
> Update globalAutoUpdate(globalAutoUpdate)



Trigger a global poll for image updates on Stacks and Deployments.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val globalAutoUpdate : GlobalAutoUpdate =  // GlobalAutoUpdate | 
try {
    val result : Update = apiInstance.globalAutoUpdate(globalAutoUpdate)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#globalAutoUpdate")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#globalAutoUpdate")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **globalAutoUpdate** | [**GlobalAutoUpdate**](GlobalAutoUpdate.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="pauseAllContainers"></a>
# **pauseAllContainers**
> Update pauseAllContainers(pauseAllContainers)



Pauses all containers on the target server.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val pauseAllContainers : PauseAllContainers =  // PauseAllContainers | 
try {
    val result : Update = apiInstance.pauseAllContainers(pauseAllContainers)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#pauseAllContainers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#pauseAllContainers")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **pauseAllContainers** | [**PauseAllContainers**](PauseAllContainers.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="pauseContainer"></a>
# **pauseContainer**
> Update pauseContainer(pauseContainer)



Pauses the container on the target server.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val pauseContainer : PauseContainer =  // PauseContainer | 
try {
    val result : Update = apiInstance.pauseContainer(pauseContainer)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#pauseContainer")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#pauseContainer")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **pauseContainer** | [**PauseContainer**](PauseContainer.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="pauseDeployment"></a>
# **pauseDeployment**
> Update pauseDeployment(pauseDeployment)



Pauses the container for the target deployment.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val pauseDeployment : PauseDeployment =  // PauseDeployment | 
try {
    val result : Update = apiInstance.pauseDeployment(pauseDeployment)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#pauseDeployment")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#pauseDeployment")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **pauseDeployment** | [**PauseDeployment**](PauseDeployment.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="pauseStack"></a>
# **pauseStack**
> Update pauseStack(pauseStack)



Pauses the target stack.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val pauseStack : PauseStack =  // PauseStack | 
try {
    val result : Update = apiInstance.pauseStack(pauseStack)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#pauseStack")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#pauseStack")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **pauseStack** | [**PauseStack**](PauseStack.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="pruneBuildx"></a>
# **pruneBuildx**
> Update pruneBuildx(pruneBuildx)



Prunes the docker buildx cache on the target server.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val pruneBuildx : PruneBuildx =  // PruneBuildx | 
try {
    val result : Update = apiInstance.pruneBuildx(pruneBuildx)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#pruneBuildx")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#pruneBuildx")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **pruneBuildx** | [**PruneBuildx**](PruneBuildx.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="pruneContainers"></a>
# **pruneContainers**
> Update pruneContainers(pruneContainers)



Prunes the docker containers on the target server.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val pruneContainers : PruneContainers =  // PruneContainers | 
try {
    val result : Update = apiInstance.pruneContainers(pruneContainers)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#pruneContainers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#pruneContainers")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **pruneContainers** | [**PruneContainers**](PruneContainers.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="pruneDockerBuilders"></a>
# **pruneDockerBuilders**
> Update pruneDockerBuilders(pruneDockerBuilders)



Prunes the docker builders on the target server.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val pruneDockerBuilders : PruneDockerBuilders =  // PruneDockerBuilders | 
try {
    val result : Update = apiInstance.pruneDockerBuilders(pruneDockerBuilders)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#pruneDockerBuilders")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#pruneDockerBuilders")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **pruneDockerBuilders** | [**PruneDockerBuilders**](PruneDockerBuilders.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="pruneImages"></a>
# **pruneImages**
> Update pruneImages(pruneImages)



Prunes the docker images on the target server.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val pruneImages : PruneImages =  // PruneImages | 
try {
    val result : Update = apiInstance.pruneImages(pruneImages)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#pruneImages")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#pruneImages")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **pruneImages** | [**PruneImages**](PruneImages.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="pruneNetworks"></a>
# **pruneNetworks**
> Update pruneNetworks(pruneNetworks)



Prunes the docker networks on the target server.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val pruneNetworks : PruneNetworks =  // PruneNetworks | 
try {
    val result : Update = apiInstance.pruneNetworks(pruneNetworks)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#pruneNetworks")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#pruneNetworks")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **pruneNetworks** | [**PruneNetworks**](PruneNetworks.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="pruneSystem"></a>
# **pruneSystem**
> Update pruneSystem(pruneSystem)



Prunes the docker system on the target server, including volumes.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val pruneSystem : PruneSystem =  // PruneSystem | 
try {
    val result : Update = apiInstance.pruneSystem(pruneSystem)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#pruneSystem")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#pruneSystem")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **pruneSystem** | [**PruneSystem**](PruneSystem.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="pruneVolumes"></a>
# **pruneVolumes**
> Update pruneVolumes(pruneVolumes)



Prunes the docker volumes on the target server.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val pruneVolumes : PruneVolumes =  // PruneVolumes | 
try {
    val result : Update = apiInstance.pruneVolumes(pruneVolumes)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#pruneVolumes")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#pruneVolumes")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **pruneVolumes** | [**PruneVolumes**](PruneVolumes.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="pullDeployment"></a>
# **pullDeployment**
> Update pullDeployment(pullDeployment)



Pulls the image for the target deployment.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val pullDeployment : PullDeployment =  // PullDeployment | 
try {
    val result : Update = apiInstance.pullDeployment(pullDeployment)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#pullDeployment")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#pullDeployment")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **pullDeployment** | [**PullDeployment**](PullDeployment.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="pullRepo"></a>
# **pullRepo**
> Update pullRepo(pullRepo)



Pulls the target repo.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val pullRepo : PullRepo =  // PullRepo | 
try {
    val result : Update = apiInstance.pullRepo(pullRepo)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#pullRepo")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#pullRepo")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **pullRepo** | [**PullRepo**](PullRepo.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="pullStack"></a>
# **pullStack**
> Update pullStack(pullStack)



Pulls images for the target stack.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val pullStack : PullStack =  // PullStack | 
try {
    val result : Update = apiInstance.pullStack(pullStack)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#pullStack")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#pullStack")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **pullStack** | [**PullStack**](PullStack.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="removeSwarmConfigs"></a>
# **removeSwarmConfigs**
> Update removeSwarmConfigs(removeSwarmConfigs)



Remove swarm configs.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val removeSwarmConfigs : RemoveSwarmConfigs =  // RemoveSwarmConfigs | 
try {
    val result : Update = apiInstance.removeSwarmConfigs(removeSwarmConfigs)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#removeSwarmConfigs")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#removeSwarmConfigs")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **removeSwarmConfigs** | [**RemoveSwarmConfigs**](RemoveSwarmConfigs.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="removeSwarmNodes"></a>
# **removeSwarmNodes**
> Update removeSwarmNodes(removeSwarmNodes)



Remove swarm nodes.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val removeSwarmNodes : RemoveSwarmNodes =  // RemoveSwarmNodes | 
try {
    val result : Update = apiInstance.removeSwarmNodes(removeSwarmNodes)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#removeSwarmNodes")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#removeSwarmNodes")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **removeSwarmNodes** | [**RemoveSwarmNodes**](RemoveSwarmNodes.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="removeSwarmSecrets"></a>
# **removeSwarmSecrets**
> Update removeSwarmSecrets(removeSwarmSecrets)



Remove swarm secrets.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val removeSwarmSecrets : RemoveSwarmSecrets =  // RemoveSwarmSecrets | 
try {
    val result : Update = apiInstance.removeSwarmSecrets(removeSwarmSecrets)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#removeSwarmSecrets")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#removeSwarmSecrets")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **removeSwarmSecrets** | [**RemoveSwarmSecrets**](RemoveSwarmSecrets.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="removeSwarmServices"></a>
# **removeSwarmServices**
> Update removeSwarmServices(removeSwarmServices)



Remove swarm services.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val removeSwarmServices : RemoveSwarmServices =  // RemoveSwarmServices | 
try {
    val result : Update = apiInstance.removeSwarmServices(removeSwarmServices)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#removeSwarmServices")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#removeSwarmServices")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **removeSwarmServices** | [**RemoveSwarmServices**](RemoveSwarmServices.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="removeSwarmStacks"></a>
# **removeSwarmStacks**
> Update removeSwarmStacks(removeSwarmStacks)



Remove swarm stacks.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val removeSwarmStacks : RemoveSwarmStacks =  // RemoveSwarmStacks | 
try {
    val result : Update = apiInstance.removeSwarmStacks(removeSwarmStacks)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#removeSwarmStacks")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#removeSwarmStacks")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **removeSwarmStacks** | [**RemoveSwarmStacks**](RemoveSwarmStacks.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="restartAllContainers"></a>
# **restartAllContainers**
> Update restartAllContainers(restartAllContainers)



Restarts all containers on the target server.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val restartAllContainers : RestartAllContainers =  // RestartAllContainers | 
try {
    val result : Update = apiInstance.restartAllContainers(restartAllContainers)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#restartAllContainers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#restartAllContainers")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **restartAllContainers** | [**RestartAllContainers**](RestartAllContainers.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="restartContainer"></a>
# **restartContainer**
> Update restartContainer(restartContainer)



Restarts the container on the target server.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val restartContainer : RestartContainer =  // RestartContainer | 
try {
    val result : Update = apiInstance.restartContainer(restartContainer)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#restartContainer")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#restartContainer")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **restartContainer** | [**RestartContainer**](RestartContainer.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="restartDeployment"></a>
# **restartDeployment**
> Update restartDeployment(restartDeployment)



Restarts the container for the target deployment.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val restartDeployment : RestartDeployment =  // RestartDeployment | 
try {
    val result : Update = apiInstance.restartDeployment(restartDeployment)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#restartDeployment")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#restartDeployment")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **restartDeployment** | [**RestartDeployment**](RestartDeployment.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="restartStack"></a>
# **restartStack**
> Update restartStack(restartStack)



Restarts the target stack.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val restartStack : RestartStack =  // RestartStack | 
try {
    val result : Update = apiInstance.restartStack(restartStack)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#restartStack")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#restartStack")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **restartStack** | [**RestartStack**](RestartStack.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="rotateAllServerKeys"></a>
# **rotateAllServerKeys**
> Update rotateAllServerKeys(body)



Rotates all connected Server keys.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val body : kotlin.String = Object // kotlin.String | 
try {
    val result : Update = apiInstance.rotateAllServerKeys(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#rotateAllServerKeys")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#rotateAllServerKeys")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **body** | **kotlin.String**|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="rotateCoreKeys"></a>
# **rotateCoreKeys**
> Update rotateCoreKeys(rotateCoreKeys)



Rotates the Core private key and all Server public keys.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val rotateCoreKeys : RotateCoreKeys =  // RotateCoreKeys | 
try {
    val result : Update = apiInstance.rotateCoreKeys(rotateCoreKeys)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#rotateCoreKeys")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#rotateCoreKeys")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **rotateCoreKeys** | [**RotateCoreKeys**](RotateCoreKeys.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="rotateSwarmConfig"></a>
# **rotateSwarmConfig**
> Update rotateSwarmConfig(rotateSwarmConfig)



Rotate a swarm config.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val rotateSwarmConfig : RotateSwarmConfig =  // RotateSwarmConfig | 
try {
    val result : Update = apiInstance.rotateSwarmConfig(rotateSwarmConfig)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#rotateSwarmConfig")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#rotateSwarmConfig")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **rotateSwarmConfig** | [**RotateSwarmConfig**](RotateSwarmConfig.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="rotateSwarmSecret"></a>
# **rotateSwarmSecret**
> Update rotateSwarmSecret(rotateSwarmSecret)



Rotate a swarm secret.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val rotateSwarmSecret : RotateSwarmSecret =  // RotateSwarmSecret | 
try {
    val result : Update = apiInstance.rotateSwarmSecret(rotateSwarmSecret)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#rotateSwarmSecret")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#rotateSwarmSecret")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **rotateSwarmSecret** | [**RotateSwarmSecret**](RotateSwarmSecret.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="runAction"></a>
# **runAction**
> Update runAction(runAction)



Run an action.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val runAction : RunAction =  // RunAction | 
try {
    val result : Update = apiInstance.runAction(runAction)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#runAction")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#runAction")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **runAction** | [**RunAction**](RunAction.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="runBuild"></a>
# **runBuild**
> Update runBuild(runBuild)



Runs the target build.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val runBuild : RunBuild =  // RunBuild | 
try {
    val result : Update = apiInstance.runBuild(runBuild)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#runBuild")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#runBuild")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **runBuild** | [**RunBuild**](RunBuild.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="runProcedure"></a>
# **runProcedure**
> Update runProcedure(runProcedure)



Runs the target Procedure.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val runProcedure : RunProcedure =  // RunProcedure | 
try {
    val result : Update = apiInstance.runProcedure(runProcedure)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#runProcedure")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#runProcedure")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **runProcedure** | [**RunProcedure**](RunProcedure.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="runStackService"></a>
# **runStackService**
> Update runStackService(runStackService)



Runs a one-time command against a service using docker compose run.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val runStackService : RunStackService =  // RunStackService | 
try {
    val result : Update = apiInstance.runStackService(runStackService)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#runStackService")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#runStackService")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **runStackService** | [**RunStackService**](RunStackService.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="runSync"></a>
# **runSync**
> Update runSync(runSync)



Runs the target resource sync.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val runSync : RunSync =  // RunSync | 
try {
    val result : Update = apiInstance.runSync(runSync)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#runSync")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#runSync")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **runSync** | [**RunSync**](RunSync.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="sendAlert"></a>
# **sendAlert**
> Update sendAlert(sendAlert)



Send a custom alert message to configured Alerters.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val sendAlert : SendAlert =  // SendAlert | 
try {
    val result : Update = apiInstance.sendAlert(sendAlert)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#sendAlert")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#sendAlert")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **sendAlert** | [**SendAlert**](SendAlert.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="startAllContainers"></a>
# **startAllContainers**
> Update startAllContainers(startAllContainers)



Starts all containers on the target server.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val startAllContainers : StartAllContainers =  // StartAllContainers | 
try {
    val result : Update = apiInstance.startAllContainers(startAllContainers)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#startAllContainers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#startAllContainers")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **startAllContainers** | [**StartAllContainers**](StartAllContainers.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="startContainer"></a>
# **startContainer**
> Update startContainer(startContainer)



Starts the container on the target server.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val startContainer : StartContainer =  // StartContainer | 
try {
    val result : Update = apiInstance.startContainer(startContainer)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#startContainer")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#startContainer")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **startContainer** | [**StartContainer**](StartContainer.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="startDeployment"></a>
# **startDeployment**
> Update startDeployment(startDeployment)



Starts the container for the target deployment.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val startDeployment : StartDeployment =  // StartDeployment | 
try {
    val result : Update = apiInstance.startDeployment(startDeployment)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#startDeployment")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#startDeployment")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **startDeployment** | [**StartDeployment**](StartDeployment.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="startStack"></a>
# **startStack**
> Update startStack(startStack)



Starts the target stack.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val startStack : StartStack =  // StartStack | 
try {
    val result : Update = apiInstance.startStack(startStack)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#startStack")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#startStack")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **startStack** | [**StartStack**](StartStack.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="stopAllContainers"></a>
# **stopAllContainers**
> Update stopAllContainers(stopAllContainers)



Stops all containers on the target server.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val stopAllContainers : StopAllContainers =  // StopAllContainers | 
try {
    val result : Update = apiInstance.stopAllContainers(stopAllContainers)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#stopAllContainers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#stopAllContainers")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **stopAllContainers** | [**StopAllContainers**](StopAllContainers.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="stopContainer"></a>
# **stopContainer**
> Update stopContainer(stopContainer)



Stops the container on the target server.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val stopContainer : StopContainer =  // StopContainer | 
try {
    val result : Update = apiInstance.stopContainer(stopContainer)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#stopContainer")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#stopContainer")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **stopContainer** | [**StopContainer**](StopContainer.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="stopDeployment"></a>
# **stopDeployment**
> Update stopDeployment(stopDeployment)



Stops the container for the target deployment.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val stopDeployment : StopDeployment =  // StopDeployment | 
try {
    val result : Update = apiInstance.stopDeployment(stopDeployment)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#stopDeployment")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#stopDeployment")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **stopDeployment** | [**StopDeployment**](StopDeployment.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="stopStack"></a>
# **stopStack**
> Update stopStack(stopStack)



Stops the target stack.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val stopStack : StopStack =  // StopStack | 
try {
    val result : Update = apiInstance.stopStack(stopStack)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#stopStack")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#stopStack")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **stopStack** | [**StopStack**](StopStack.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="testAlerter"></a>
# **testAlerter**
> Update testAlerter(testAlerter)



Tests an Alerter&#39;s ability to reach the configured endpoint.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val testAlerter : TestAlerter =  // TestAlerter | 
try {
    val result : Update = apiInstance.testAlerter(testAlerter)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#testAlerter")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#testAlerter")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **testAlerter** | [**TestAlerter**](TestAlerter.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="unpauseAllContainers"></a>
# **unpauseAllContainers**
> Update unpauseAllContainers(unpauseAllContainers)



Unpauses all containers on the target server.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val unpauseAllContainers : UnpauseAllContainers =  // UnpauseAllContainers | 
try {
    val result : Update = apiInstance.unpauseAllContainers(unpauseAllContainers)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#unpauseAllContainers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#unpauseAllContainers")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **unpauseAllContainers** | [**UnpauseAllContainers**](UnpauseAllContainers.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="unpauseContainer"></a>
# **unpauseContainer**
> Update unpauseContainer(unpauseContainer)



Unpauses the container on the target server.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val unpauseContainer : UnpauseContainer =  // UnpauseContainer | 
try {
    val result : Update = apiInstance.unpauseContainer(unpauseContainer)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#unpauseContainer")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#unpauseContainer")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **unpauseContainer** | [**UnpauseContainer**](UnpauseContainer.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="unpauseDeployment"></a>
# **unpauseDeployment**
> Update unpauseDeployment(unpauseDeployment)



Unpauses the container for the target deployment.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val unpauseDeployment : UnpauseDeployment =  // UnpauseDeployment | 
try {
    val result : Update = apiInstance.unpauseDeployment(unpauseDeployment)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#unpauseDeployment")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#unpauseDeployment")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **unpauseDeployment** | [**UnpauseDeployment**](UnpauseDeployment.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="unpauseStack"></a>
# **unpauseStack**
> Update unpauseStack(unpauseStack)



Unpauses the target stack.

### Example
```kotlin
// Import classes:
//import ca.glong.komodo.core.openapi.infrastructure.*
//import ca.glong.komodo.core.openapi.models.*

val apiInstance = ExecuteApi()
val unpauseStack : UnpauseStack =  // UnpauseStack | 
try {
    val result : Update = apiInstance.unpauseStack(unpauseStack)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ExecuteApi#unpauseStack")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ExecuteApi#unpauseStack")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **unpauseStack** | [**UnpauseStack**](UnpauseStack.md)|  | |

### Return type

[**Update**](Update.md)

### Authorization


Configure api-secret:
    ApiClient.apiKey["X-Api-Secret"] = ""
    ApiClient.apiKeyPrefix["X-Api-Secret"] = ""
Configure api-key:
    ApiClient.apiKey["X-Api-Key"] = ""
    ApiClient.apiKeyPrefix["X-Api-Key"] = ""
Configure jwt:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

