
# UrlBuilderConfig

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **address** | **kotlin.String** | The address of the Periphery agent |  [optional] |
| **insecureTls** | **kotlin.Boolean** | Whether to validate the Periphery tls certificates. |  [optional] |
| **passkey** | **kotlin.String** | Deprecated. Use private / public keys instead. An optional override passkey to use to authenticate with periphery agent. If this is empty, will use passkey in core config. |  [optional] |
| **peripheryPublicKey** | **kotlin.String** | An expected public key associated with Periphery private key. If empty, doesn&#39;t validate Periphery public key. |  [optional] |



