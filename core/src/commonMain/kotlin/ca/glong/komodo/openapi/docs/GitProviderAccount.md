
# GitProviderAccount

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | **kotlin.String** | The Mongo ID of the git provider account. This field is de/serialized from/to JSON as &#x60;{ \&quot;_id\&quot;: { \&quot;$oid\&quot;: \&quot;...\&quot; }, ...(rest of serialized User) }&#x60; |  [optional] |
| **domain** | **kotlin.String** | The domain of the provider.  For git, this cannot include the protocol eg &#39;http://&#39;, which is controlled with &#39;https&#39; field. |  [optional] |
| **https** | **kotlin.Boolean** | Whether git provider is accessed over http or https. |  [optional] |
| **token** | **kotlin.String** | The token in plain text on the db. If the database / host can be accessed this is insecure. |  [optional] |
| **username** | **kotlin.String** | The account username |  [optional] |



