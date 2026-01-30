
# GetStackLog

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **services** | **kotlin.collections.List&lt;kotlin.String&gt;** | Filter the logs to only ones from specific services. If empty, will include logs from all services. |  |
| **stack** | **kotlin.String** | Id or name |  |
| **tail** | **kotlin.Long** | The number of lines of the log tail to include. Default: 100. Max: 5000. |  [optional] |
| **timestamps** | **kotlin.Boolean** | Enable &#x60;--timestamps&#x60; |  [optional] |



