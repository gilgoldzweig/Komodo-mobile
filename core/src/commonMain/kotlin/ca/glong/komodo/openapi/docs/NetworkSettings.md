
# NetworkSettings

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **networks** | [**kotlin.collections.Map&lt;kotlin.String, EndpointSettings&gt;**](EndpointSettings.md) | Information about all networks that the container is connected to. |  [optional] |
| **ports** | **kotlin.collections.Map&lt;kotlin.String, kotlin.collections.List&lt;PortBinding&gt;&gt;** |  |  [optional] |
| **sandboxID** | **kotlin.String** | SandboxID uniquely represents a container&#39;s network stack. |  [optional] |
| **sandboxKey** | **kotlin.String** | SandboxKey is the full path of the netns handle |  [optional] |



