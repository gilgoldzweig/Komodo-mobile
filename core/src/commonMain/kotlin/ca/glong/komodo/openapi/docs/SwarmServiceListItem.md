
# SwarmServiceListItem

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **configs** | **kotlin.collections.List&lt;kotlin.String&gt;** | Attached config names |  |
| **secrets** | **kotlin.collections.List&lt;kotlin.String&gt;** | Attached secret names |  |
| **state** | [**SwarmState**](SwarmState.md) | Swarm service state. - Healthy if all associated tasks match their desired state (or report no desired state) - Unhealthy otherwise  Not included in docker cli return, computed by Komodo |  |
| **completedTasks** | **kotlin.Long** | The number of tasks for a job that are in the Completed state. This field must be cross-referenced with the service type, as the value of 0 may mean the service is not in a job mode, or it may mean the job-mode service has no tasks yet Completed. |  [optional] |
| **createdAt** | **kotlin.String** |  |  [optional] |
| **desiredTasks** | **kotlin.Long** | The number of tasks for the service desired to be running. - For replicated services, this is the replica count from the service spec. - For global services, this is computed by taking count of all tasks for the   service with a Desired State other than Shutdown. |  [optional] |
| **ID** | **kotlin.String** |  |  [optional] |
| **image** | **kotlin.String** | The image associated with service |  [optional] |
| **maxConcurrent** | **kotlin.Long** | Max concurrent tasks (in a replicated job mode) |  [optional] |
| **mode** | [**SwarmServiceMode**](SwarmServiceMode.md) | The service mode. |  [optional] |
| **name** | **kotlin.String** | Name of the service. |  [optional] |
| **replicas** | **kotlin.Long** | Number of replicas (in a replicated mode) |  [optional] |
| **restart** | [**TaskSpecRestartPolicyConditionEnum**](TaskSpecRestartPolicyConditionEnum.md) | Condition for restart. |  [optional] |
| **runningTasks** | **kotlin.Long** | The number of tasks for the service currently in the Running state. |  [optional] |
| **runtime** | **kotlin.String** | Runtime is the type of runtime specified for the task executor. |  [optional] |
| **updatedAt** | **kotlin.String** |  |  [optional] |



