
# DockerRegistryAccount

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | **kotlin.String** | The Mongo ID of the docker registry account. This field is de/serialized from/to JSON as &#x60;{ \&quot;_id\&quot;: { \&quot;$oid\&quot;: \&quot;...\&quot; }, ...(rest of DockerRegistryAccount) }&#x60; |  [optional] |
| **domain** | **kotlin.String** | The domain of the provider.  For docker registry, this can include &#39;http://...&#39;, however this is not recommended and won&#39;t work unless \&quot;insecure registries\&quot; are enabled on your hosts. See &lt;https://docs.docker.com/reference/cli/dockerd/#insecure-registries&gt;. |  [optional] |
| **token** | **kotlin.String** | The token in plain text on the db. If the database / host can be accessed this is insecure. |  [optional] |
| **username** | **kotlin.String** | The account username |  [optional] |



