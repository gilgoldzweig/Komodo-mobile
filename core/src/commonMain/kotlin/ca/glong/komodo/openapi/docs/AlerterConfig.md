
# AlerterConfig

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **alertTypes** | [**kotlin.collections.List&lt;AlertDataVariant&gt;**](AlertDataVariant.md) | Only send specific alert types. If empty, will send all alert types. |  [optional] |
| **enabled** | **kotlin.Boolean** | Whether the alerter is enabled |  [optional] |
| **endpoint** | [**AlerterEndpoint**](AlerterEndpoint.md) | Where to route the alert messages.  Default: Custom endpoint &#x60;http://localhost:7000&#x60; |  [optional] |
| **exceptResources** | [**kotlin.collections.List&lt;ResourceTarget&gt;**](ResourceTarget.md) | DON&#39;T send alerts on these resources. |  [optional] |
| **maintenanceWindows** | [**kotlin.collections.List&lt;MaintenanceWindow&gt;**](MaintenanceWindow.md) | Scheduled maintenance windows during which alerts will be suppressed. |  [optional] |
| **resources** | [**kotlin.collections.List&lt;ResourceTarget&gt;**](ResourceTarget.md) | Only send alerts on specific resources. If empty, will send alerts for all resources. |  [optional] |



