
# SearchSwarmServiceLog

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **service** | **kotlin.String** | Select the swarm service to get logs for. |  |
| **swarm** | **kotlin.String** | Id or name |  |
| **terms** | **kotlin.collections.List&lt;kotlin.String&gt;** | The terms to search for. |  |
| **combinator** | [**SearchCombinator**](SearchCombinator.md) | When searching for multiple terms, can use &#x60;AND&#x60; or &#x60;OR&#x60; combinator.  - &#x60;AND&#x60;: Only include lines with **all** terms present in that line. - &#x60;OR&#x60;: Include lines that have one or more matches in the terms. |  [optional] |
| **details** | **kotlin.Boolean** | Enable &#x60;--details&#x60; |  [optional] |
| **invert** | **kotlin.Boolean** | Invert the results, ie return all lines that DON&#39;T match the terms / combinator. |  [optional] |
| **noResolve** | **kotlin.Boolean** | Enable &#x60;--no-resolve&#x60; |  [optional] |
| **noTaskIds** | **kotlin.Boolean** | Enable &#x60;--no-task-ids&#x60; |  [optional] |
| **timestamps** | **kotlin.Boolean** | Enable &#x60;--timestamps&#x60; |  [optional] |



