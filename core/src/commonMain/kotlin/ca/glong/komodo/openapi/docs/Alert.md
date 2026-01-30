
# Alert

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **&#x60;data&#x60;** | [**AlertData**](AlertData.md) | The data attached to the alert |  |
| **level** | [**SeverityLevel**](SeverityLevel.md) | The severity of the alert |  |
| **resolved** | **kotlin.Boolean** | Whether the alert is already resolved |  |
| **target** | [**ResourceTarget**](ResourceTarget.md) | The target of the alert |  |
| **ts** | **kotlin.Long** | Unix timestamp in milliseconds the alert was opened |  |
| **id** | **kotlin.String** | The Mongo ID of the alert. This field is de/serialized from/to JSON as &#x60;{ \&quot;_id\&quot;: { \&quot;$oid\&quot;: \&quot;...\&quot; }, ...(rest of serialized Alert) }&#x60; |  [optional] |
| **resolvedTs** | **kotlin.Long** | The timestamp of alert resolution |  [optional] |



