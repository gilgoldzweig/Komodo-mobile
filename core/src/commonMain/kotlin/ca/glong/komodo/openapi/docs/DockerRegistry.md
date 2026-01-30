
# DockerRegistry

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **accounts** | [**kotlin.collections.List&lt;ProviderAccount&gt;**](ProviderAccount.md) | The accounts on the registry. Required. |  |
| **domain** | **kotlin.String** | The docker provider domain. Default: &#x60;docker.io&#x60;. |  [optional] |
| **organizations** | **kotlin.collections.List&lt;kotlin.String&gt;** | Available organizations on the registry provider. Used to push an image under an organization&#39;s repo rather than an account&#39;s repo. |  [optional] |



