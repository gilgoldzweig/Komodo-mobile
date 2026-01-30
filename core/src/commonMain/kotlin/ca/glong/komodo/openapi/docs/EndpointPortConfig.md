
# EndpointPortConfig

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **name** | **kotlin.String** |  |  [optional] |
| **protocol** | [**EndpointPortConfigProtocolEnum**](EndpointPortConfigProtocolEnum.md) |  |  [optional] |
| **publishMode** | [**EndpointPortConfigPublishModeEnum**](EndpointPortConfigPublishModeEnum.md) | The mode in which port is published.  &lt;p&gt;&lt;br /&gt;&lt;/p&gt;  - \\\&quot;ingress\\\&quot; makes the target port accessible on every node,   regardless of whether there is a task for the service running on   that node or not. - \\\&quot;host\\\&quot; bypasses the routing mesh and publish the port directly on   the swarm node where that service is running. |  [optional] |
| **publishedPort** | **kotlin.Long** | The port on the swarm hosts. |  [optional] |
| **targetPort** | **kotlin.Long** | The port inside the container. |  [optional] |



