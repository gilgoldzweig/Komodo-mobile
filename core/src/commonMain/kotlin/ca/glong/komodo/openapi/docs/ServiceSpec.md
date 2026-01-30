
# ServiceSpec

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **endpointSpec** | [**EndpointSpec**](EndpointSpec.md) |  |  [optional] |
| **labels** | **kotlin.collections.Map&lt;kotlin.String, kotlin.String&gt;** | User-defined key/value metadata. |  [optional] |
| **mode** | [**ServiceSpecMode**](ServiceSpecMode.md) |  |  [optional] |
| **name** | **kotlin.String** | Name of the service. |  [optional] |
| **networks** | [**kotlin.collections.List&lt;NetworkAttachmentConfig&gt;**](NetworkAttachmentConfig.md) | Specifies which networks the service should attach to.  Deprecated: This field is deprecated since v1.44. The Networks field in TaskSpec should be used instead. |  [optional] |
| **rollbackConfig** | [**ServiceSpecRollbackConfig**](ServiceSpecRollbackConfig.md) |  |  [optional] |
| **taskTemplate** | [**TaskSpec**](TaskSpec.md) |  |  [optional] |
| **updateConfig** | [**ServiceSpecUpdateConfig**](ServiceSpecUpdateConfig.md) |  |  [optional] |



