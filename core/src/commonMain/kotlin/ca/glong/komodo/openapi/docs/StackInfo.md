
# StackInfo

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **deployedConfig** | **kotlin.String** | The output of &#x60;docker compose config&#x60;. This is updated whenever Komodo successfully deploys the stack. |  [optional] |
| **deployedContents** | [**kotlin.collections.List&lt;FileContents&gt;**](FileContents.md) | The deployed compose / additional file contents. This is updated whenever Komodo successfully deploys the stack. |  [optional] |
| **deployedHash** | **kotlin.String** | Deployed short commit hash, or null. Only for repo based stacks. |  [optional] |
| **deployedMessage** | **kotlin.String** | Deployed commit message, or null. Only for repo based stacks |  [optional] |
| **deployedProjectName** | **kotlin.String** | The deployed project name. This is updated whenever Komodo successfully deploys the stack. If it is present, Komodo will use it for actions over other options, to ensure control is maintained after changing the project name (there is no rename compose project api). |  [optional] |
| **deployedServices** | [**kotlin.collections.List&lt;StackServiceNames&gt;**](StackServiceNames.md) | The deployed service names. This is updated whenever it is empty, or deployed contents is updated. |  [optional] |
| **latestHash** | **kotlin.String** | Latest commit hash, or null |  [optional] |
| **latestMessage** | **kotlin.String** | Latest commit message, or null |  [optional] |
| **latestServices** | [**kotlin.collections.List&lt;StackServiceNames&gt;**](StackServiceNames.md) | The latest service names. This is updated whenever the stack cache refreshes, using the latest file contents (either db defined or remote). |  [optional] |
| **missingFiles** | **kotlin.collections.List&lt;kotlin.String&gt;** | If any of the expected compose / additional files are missing in the repo, they will be stored here. |  [optional] |
| **remoteContents** | [**kotlin.collections.List&lt;StackRemoteFileContents&gt;**](StackRemoteFileContents.md) | The remote compose / additional file contents, whether on host or in repo. This is updated whenever Komodo refreshes the stack cache. It will be empty if the file is defined directly in the stack config. |  [optional] |
| **remoteErrors** | [**kotlin.collections.List&lt;FileContents&gt;**](FileContents.md) | If there was an error in getting the remote contents, it will be here. |  [optional] |



