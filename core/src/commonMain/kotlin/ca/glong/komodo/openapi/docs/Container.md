
# Container

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **appArmorProfile** | **kotlin.String** |  |  [optional] |
| **args** | **kotlin.collections.List&lt;kotlin.String&gt;** | The arguments to the command being run |  [optional] |
| **config** | [**ContainerConfig**](ContainerConfig.md) |  |  [optional] |
| **created** | **kotlin.String** | The time the container was created |  [optional] |
| **driver** | **kotlin.String** |  |  [optional] |
| **execIDs** | **kotlin.collections.List&lt;kotlin.String&gt;** | IDs of exec instances that are running in the container. |  [optional] |
| **graphDriver** | [**GraphDriverData**](GraphDriverData.md) |  |  [optional] |
| **hostConfig** | [**HostConfig**](HostConfig.md) |  |  [optional] |
| **hostnamePath** | **kotlin.String** |  |  [optional] |
| **hostsPath** | **kotlin.String** |  |  [optional] |
| **id** | **kotlin.String** | The ID of the container |  [optional] |
| **image** | **kotlin.String** | The container&#39;s image ID |  [optional] |
| **logPath** | **kotlin.String** |  |  [optional] |
| **mountLabel** | **kotlin.String** |  |  [optional] |
| **mounts** | [**kotlin.collections.List&lt;MountPoint&gt;**](MountPoint.md) |  |  [optional] |
| **name** | **kotlin.String** |  |  [optional] |
| **networkSettings** | [**NetworkSettings**](NetworkSettings.md) |  |  [optional] |
| **path** | **kotlin.String** | The path to the command being run |  [optional] |
| **platform** | **kotlin.String** |  |  [optional] |
| **processLabel** | **kotlin.String** |  |  [optional] |
| **resolvConfPath** | **kotlin.String** |  |  [optional] |
| **restartCount** | **kotlin.Long** |  |  [optional] |
| **sizeRootFs** | **kotlin.Long** | The total size of all the files in this container. |  [optional] |
| **sizeRw** | **kotlin.Long** | The size of files that have been created or changed by this container. |  [optional] |
| **state** | [**ContainerState**](ContainerState.md) |  |  [optional] |



