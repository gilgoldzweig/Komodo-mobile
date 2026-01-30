
# SwarmConfig

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **links** | **kotlin.collections.List&lt;kotlin.String&gt;** | Configure quick links that are displayed in the resource header |  [optional] |
| **maintenanceWindows** | [**kotlin.collections.List&lt;MaintenanceWindow&gt;**](MaintenanceWindow.md) | Scheduled maintenance windows during which alerts will be suppressed. |  [optional] |
| **sendUnhealthyAlerts** | **kotlin.Boolean** | Whether to send alerts about the swarm health. |  [optional] |
| **serverIds** | **kotlin.collections.List&lt;kotlin.String&gt;** | The Servers which are swarm manager nodes. If a Server is not reachable or gives error, tries the next Server. |  [optional] |



