
# EndpointSpec

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **mode** | [**EndpointSpecModeEnum**](EndpointSpecModeEnum.md) | The mode of resolution to use for internal load balancing between tasks. |  [optional] |
| **ports** | [**kotlin.collections.List&lt;EndpointPortConfig&gt;**](EndpointPortConfig.md) | List of exposed ports that this service is accessible on from the outside. Ports can only be provided if &#x60;vip&#x60; resolution mode is used. |  [optional] |



