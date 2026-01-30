
# StackRemoteFileContents

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **contents** | **kotlin.String** | The contents of the file |  |
| **path** | **kotlin.String** | The path to the file |  |
| **requires** | [**StackFileRequires**](StackFileRequires.md) | Whether diff requires Redeploy / Restart / None |  [optional] |
| **services** | **kotlin.collections.List&lt;kotlin.String&gt;** | The services depending on this file, or empty for global requirement (eg all compose files and env files). |  [optional] |



