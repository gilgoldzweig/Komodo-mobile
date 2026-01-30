
# BuildInfo

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **lastBuiltAt** | **kotlin.Long** | The timestamp build was last built. |  |
| **builtContents** | **kotlin.String** | The last built dockerfile contents. This is updated whenever Komodo successfully runs the build. |  [optional] |
| **builtHash** | **kotlin.String** | Latest built short commit hash, or null. |  [optional] |
| **builtMessage** | **kotlin.String** | Latest built commit message, or null. Only for repo based stacks |  [optional] |
| **latestHash** | **kotlin.String** | Latest remote short commit hash, or null. |  [optional] |
| **latestMessage** | **kotlin.String** | Latest remote commit message, or null |  [optional] |
| **remoteContents** | **kotlin.String** | The remote dockerfile contents, whether on host or in repo. This is updated whenever Komodo refreshes the build cache. It will be empty if the dockerfile is defined directly in the build config. |  [optional] |
| **remoteError** | **kotlin.String** | If there was an error in getting the remote contents, it will be here. |  [optional] |
| **remotePath** | **kotlin.String** | The absolute path to the file |  [optional] |



