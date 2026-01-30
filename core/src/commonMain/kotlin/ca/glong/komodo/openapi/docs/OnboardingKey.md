
# OnboardingKey

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **publicKey** | **kotlin.String** | Unique public key associated the creation private key. |  |
| **copyServer** | **kotlin.String** | Optional. If specified, copy this Server config when initializing the Server. |  [optional] |
| **createBuilder** | **kotlin.Boolean** | Also create a Builder for the Server. |  [optional] |
| **createdAt** | **kotlin.Long** | Timestamp of key creation |  [optional] |
| **enabled** | **kotlin.Boolean** | Disable the onboarding key when not in use. |  [optional] |
| **expires** | **kotlin.Long** | Expiry of key, or 0 if never expires |  [optional] |
| **fixExistingServers** | **kotlin.Boolean** | Allows the Onboarding Key to be used to:  1. Enable a disabled Server 2. Remove Server &#39;address&#39; configuration, allowing Periphery -&gt; Core connection. 3. Update existing Server&#39;s public keys. |  [optional] |
| **name** | **kotlin.String** | Name associated with the api key for management |  [optional] |
| **onboarded** | **kotlin.collections.List&lt;kotlin.String&gt;** | The [Server](crate::entities::server::Server) ids onboarded by this Creation Key |  [optional] |
| **tags** | **kotlin.collections.List&lt;kotlin.String&gt;** | Default tags to give to Servers created with this key. |  [optional] |



