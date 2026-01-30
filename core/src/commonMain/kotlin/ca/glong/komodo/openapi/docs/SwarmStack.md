
# SwarmStack

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **name** | **kotlin.String** | Swarm stack name. |  |
| **services** | [**kotlin.collections.List&lt;SwarmServiceListItem&gt;**](SwarmServiceListItem.md) | Services part of the stack |  |
| **state** | [**SwarmState**](SwarmState.md) | Swarm stack state. - Healthy if all associated tasks match their desired state (or report no desired state) - Unhealthy otherwise  Not included in docker cli return, computed by Komodo |  |
| **tasks** | [**kotlin.collections.List&lt;SwarmTaskListItem&gt;**](SwarmTaskListItem.md) | Tasks part of the stack |  |



