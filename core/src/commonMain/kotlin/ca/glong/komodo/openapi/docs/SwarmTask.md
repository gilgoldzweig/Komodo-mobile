
# SwarmTask

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **assignedGenericResources** | [**kotlin.collections.List&lt;VecInner&gt;**](VecInner.md) |  |  [optional] |
| **createdAt** | **kotlin.String** |  |  [optional] |
| **desiredState** | [**TaskState**](TaskState.md) |  |  [optional] |
| **ID** | **kotlin.String** | The ID of the task. |  [optional] |
| **jobIteration** | [**ObjectVersion**](ObjectVersion.md) | If the Service this Task belongs to is a job-mode service, contains the JobIteration of the Service this Task was created for. Absent if the Task was created for a Replicated or Global Service. |  [optional] |
| **labels** | **kotlin.collections.Map&lt;kotlin.String, kotlin.String&gt;** | User-defined key/value metadata. |  [optional] |
| **name** | **kotlin.String** | Name of the task. |  [optional] |
| **nodeID** | **kotlin.String** | The ID of the node that this task is on. |  [optional] |
| **serviceID** | **kotlin.String** | The ID of the service this task is part of. |  [optional] |
| **slot** | **kotlin.Long** |  |  [optional] |
| **spec** | [**TaskSpec**](TaskSpec.md) |  |  [optional] |
| **status** | [**TaskStatus**](TaskStatus.md) |  |  [optional] |
| **updatedAt** | **kotlin.String** |  |  [optional] |
| **version** | [**ObjectVersion**](ObjectVersion.md) |  |  [optional] |



