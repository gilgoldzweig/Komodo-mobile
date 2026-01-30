
# ConfigSpec

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **&#x60;data&#x60;** | **kotlin.String** | Data is the data to store as a config, formatted as a Base64-url-safe-encoded ([RFC 4648](https://tools.ietf.org/html/rfc4648#section-5)) string. It must be empty if the Driver field is set, in which case the data is loaded from an external secret store. The maximum allowed size is 500KB, as defined in [MaxSecretSize](https://pkg.go.dev/github.com/moby/swarmkit/v2@v2.0.0-20250103191802-8c1959736554/api/validation#MaxSecretSize). |  [optional] |
| **labels** | **kotlin.collections.Map&lt;kotlin.String, kotlin.String&gt;** | User-defined key/value metadata. |  [optional] |
| **name** | **kotlin.String** | User-defined name of the config. |  [optional] |
| **templating** | [**Driver**](Driver.md) | Templating driver, if applicable  Templating controls whether and how to evaluate the config payload as a template. If no driver is set, no templating is used. |  [optional] |



