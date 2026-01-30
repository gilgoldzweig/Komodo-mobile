
# Update

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **logs** | [**kotlin.collections.List&lt;Log&gt;**](Log.md) | Logs produced as the operation is performed |  |
| **operation** | [**Operation**](Operation.md) | The operation performed |  |
| **&#x60;operator&#x60;** | **kotlin.String** | The user id that triggered the update.  Also can take these values for operations triggered automatically: - &#x60;Procedure&#x60;: The operation was triggered as part of a procedure run - &#x60;Github&#x60;: The operation was triggered by a github webhook - &#x60;Auto Redeploy&#x60;: The operation (always &#x60;Deploy&#x60;) was triggered by an attached build finishing. |  |
| **startTs** | **kotlin.Long** | The time the operation started |  |
| **status** | [**UpdateStatus**](UpdateStatus.md) | The status of the update - &#x60;Queued&#x60; - &#x60;InProgress&#x60; - &#x60;Complete&#x60; |  |
| **success** | **kotlin.Boolean** | Whether the operation was successful |  |
| **target** | [**ResourceTarget**](ResourceTarget.md) | The target resource to which this update refers |  |
| **id** | **kotlin.String** | The Mongo ID of the update. This field is de/serialized from/to JSON as &#x60;{ \&quot;_id\&quot;: { \&quot;$oid\&quot;: \&quot;...\&quot; }, ...(rest of serialized Update) }&#x60; |  [optional] |
| **commitHash** | **kotlin.String** | An optional commit hash associated with the update, ie cloned hash or deployed hash. |  [optional] |
| **currentToml** | **kotlin.String** | If the update is for resource config update, give the current (at time of Update) toml contents |  [optional] |
| **endTs** | **kotlin.Long** | The time the operation completed. |  [optional] |
| **otherData** | **kotlin.String** | Some unstructured, operation specific data. Not for general usage. |  [optional] |
| **prevToml** | **kotlin.String** | If the update is for resource config update, give the previous toml contents |  [optional] |
| **version** | [**Version**](Version.md) | An optional version on the update, ie build version or deployed version. |  [optional] |



