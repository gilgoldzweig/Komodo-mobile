
# ContainerState

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **dead** | **kotlin.Boolean** |  |  [optional] |
| **error** | **kotlin.String** |  |  [optional] |
| **exitCode** | **kotlin.Long** | The last exit code of this container |  [optional] |
| **finishedAt** | **kotlin.String** | The time when this container last exited. |  [optional] |
| **health** | [**ContainerHealth**](ContainerHealth.md) |  |  [optional] |
| **ooMKilled** | **kotlin.Boolean** | Whether a process within this container has been killed because it ran out of memory since the container was last started. |  [optional] |
| **paused** | **kotlin.Boolean** | Whether this container is paused. |  [optional] |
| **pid** | **kotlin.Long** | The process ID of this container |  [optional] |
| **restarting** | **kotlin.Boolean** | Whether this container is restarting. |  [optional] |
| **running** | **kotlin.Boolean** | Whether this container is running.  Note that a running container can be _paused_. The &#x60;Running&#x60; and &#x60;Paused&#x60; booleans are not mutually exclusive:  When pausing a container (on Linux), the freezer cgroup is used to suspend all processes in the container. Freezing the process requires the process to be running. As a result, paused containers are both &#x60;Running&#x60; _and_ &#x60;Paused&#x60;.  Use the &#x60;Status&#x60; field instead to determine if a container&#39;s state is \\\&quot;running\\\&quot;. |  [optional] |
| **startedAt** | **kotlin.String** | The time when this container was last started. |  [optional] |
| **status** | [**ContainerStateStatusEnum**](ContainerStateStatusEnum.md) | String representation of the container state. Can be one of \\\&quot;created\\\&quot;, \\\&quot;running\\\&quot;, \\\&quot;paused\\\&quot;, \\\&quot;restarting\\\&quot;, \\\&quot;removing\\\&quot;, \\\&quot;exited\\\&quot;, or \\\&quot;dead\\\&quot;. |  [optional] |



