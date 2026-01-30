
# ResourceSyncInfo

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **lastSyncHash** | **kotlin.String** | Short commit hash of last applied sync |  [optional] |
| **lastSyncMessage** | **kotlin.String** | Commit message of last applied sync |  [optional] |
| **lastSyncTs** | **kotlin.Long** | Unix timestamp of last applied sync |  [optional] |
| **pendingDeploy** | [**SyncDeployUpdate**](SyncDeployUpdate.md) | The list of pending deploys to resources. |  [optional] |
| **pendingError** | **kotlin.String** | If there is an error, it will be stored here |  [optional] |
| **pendingHash** | **kotlin.String** | The commit hash which produced these pending updates. |  [optional] |
| **pendingMessage** | **kotlin.String** | The commit message which produced these pending updates. |  [optional] |
| **remoteContents** | [**kotlin.collections.List&lt;SyncFileContents&gt;**](SyncFileContents.md) | The current sync files |  [optional] |
| **remoteErrors** | [**kotlin.collections.List&lt;SyncFileContents&gt;**](SyncFileContents.md) | Any read errors in files by path |  [optional] |
| **resourceUpdates** | [**kotlin.collections.List&lt;ResourceDiff&gt;**](ResourceDiff.md) | The list of pending updates to resources |  [optional] |
| **userGroupUpdates** | [**kotlin.collections.List&lt;DiffData&gt;**](DiffData.md) | The list of pending updates to user groups |  [optional] |
| **variableUpdates** | [**kotlin.collections.List&lt;DiffData&gt;**](DiffData.md) | The list of pending updates to variables |  [optional] |



