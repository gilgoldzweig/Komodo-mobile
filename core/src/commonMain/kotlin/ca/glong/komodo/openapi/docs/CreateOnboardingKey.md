
# CreateOnboardingKey

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **name** | **kotlin.String** | The name for the creation key |  |
| **copyServer** | **kotlin.String** | Optional. New Servers copy this Server&#39;s config. |  [optional] |
| **createBuilder** | **kotlin.Boolean** | Optional. Whether to also create a Builder for the Server. |  [optional] |
| **expires** | **kotlin.Long** | A unix timestamp in millseconds specifying api key expire time. Default is 0, which means no expiry. |  [optional] |
| **fixExistingServers** | **kotlin.Boolean** | Allows the Onboarding Key to be used to:  1. Enable a disabled Server 2. Remove Server &#39;address&#39; configuration, allowing Periphery -&gt; Core connection. 3. Update existing Server&#39;s public keys. |  [optional] |
| **privateKey** | **kotlin.String** | Optionally specify an existing private key, otherwise generate fresh key. |  [optional] |
| **tags** | **kotlin.collections.List&lt;kotlin.String&gt;** | Default tags to apply to Servers created using this key. |  [optional] |



