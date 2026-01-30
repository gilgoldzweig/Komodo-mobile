
# TaskSpec

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **containerSpec** | [**TaskSpecContainerSpec**](TaskSpecContainerSpec.md) |  |  [optional] |
| **forceUpdate** | **kotlin.Long** | A counter that triggers an update even if no relevant parameters have been changed. |  [optional] |
| **logDriver** | [**TaskSpecLogDriver**](TaskSpecLogDriver.md) |  |  [optional] |
| **networkAttachmentSpec** | [**TaskSpecNetworkAttachmentSpec**](TaskSpecNetworkAttachmentSpec.md) |  |  [optional] |
| **networks** | [**kotlin.collections.List&lt;NetworkAttachmentConfig&gt;**](NetworkAttachmentConfig.md) | Specifies which networks the service should attach to. |  [optional] |
| **placement** | [**TaskSpecPlacement**](TaskSpecPlacement.md) |  |  [optional] |
| **pluginSpec** | [**TaskSpecPluginSpec**](TaskSpecPluginSpec.md) |  |  [optional] |
| **resources** | [**TaskSpecResources**](TaskSpecResources.md) |  |  [optional] |
| **restartPolicy** | [**TaskSpecRestartPolicy**](TaskSpecRestartPolicy.md) |  |  [optional] |
| **runtime** | **kotlin.String** | Runtime is the type of runtime specified for the task executor. |  [optional] |



