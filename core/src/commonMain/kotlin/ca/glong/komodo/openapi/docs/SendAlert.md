
# SendAlert

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **message** | **kotlin.String** | The alert message. Required. |  |
| **alerters** | **kotlin.collections.List&lt;kotlin.String&gt;** | Specific alerter names or ids. If empty / not passed, sends to all configured alerters with the &#x60;Custom&#x60; alert type whitelisted / not blacklisted. |  [optional] |
| **details** | **kotlin.String** | The alert details. Optional. |  [optional] |
| **level** | [**SeverityLevel**](SeverityLevel.md) | The alert level. |  [optional] |



