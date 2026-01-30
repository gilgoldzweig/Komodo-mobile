
# SwarmService

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **state** | [**SwarmState**](SwarmState.md) | Swarm service state. - Healthy if all associated tasks match their desired state (or report no desired state) - Unhealthy otherwise  Not included in docker cli return, computed by Komodo |  |
| **createdAt** | **kotlin.String** |  |  [optional] |
| **endpoint** | [**ServiceEndpoint**](ServiceEndpoint.md) |  |  [optional] |
| **ID** | **kotlin.String** |  |  [optional] |
| **jobStatus** | [**ServiceJobStatus**](ServiceJobStatus.md) |  |  [optional] |
| **maxConcurrent** | **kotlin.Long** | Max concurrent tasks (in a replicated job mode) |  [optional] |
| **mode** | [**SwarmServiceMode**](SwarmServiceMode.md) | The service mode. |  [optional] |
| **replicas** | **kotlin.Long** | Number of replicas (in a replicated mode) |  [optional] |
| **serviceStatus** | [**ServiceServiceStatus**](ServiceServiceStatus.md) |  |  [optional] |
| **spec** | [**ServiceSpec**](ServiceSpec.md) |  |  [optional] |
| **updateStatus** | [**ServiceUpdateStatus**](ServiceUpdateStatus.md) |  |  [optional] |
| **updatedAt** | **kotlin.String** |  |  [optional] |
| **version** | [**ObjectVersion**](ObjectVersion.md) |  |  [optional] |



