
# DeleteApiKeyV2

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **name** | **kotlin.String** | The name for the api key. |  |
| **expires** | **kotlin.Long** | A unix timestamp in millseconds specifying api key expire time. Default is 0, which means no expiry. |  [optional] |
| **publicKey** | **kotlin.String** | Optionally provide a pre-existing public key. Otherwise, a private key will be generated and returned in the response |  [optional] |



