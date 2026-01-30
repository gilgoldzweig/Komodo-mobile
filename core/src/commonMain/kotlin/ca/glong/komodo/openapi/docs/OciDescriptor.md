
# OciDescriptor

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **annotations** | **kotlin.collections.Map&lt;kotlin.String, kotlin.String&gt;** | Arbitrary metadata relating to the targeted content. |  [optional] |
| **artifactType** | **kotlin.String** | ArtifactType is the IANA media type of this artifact. |  [optional] |
| **&#x60;data&#x60;** | **kotlin.String** | Data is an embedding of the targeted content. This is encoded as a base64 string when marshalled to JSON (automatically, by encoding/json). If present, Data can be used directly to avoid fetching the targeted content. |  [optional] |
| **digest** | **kotlin.String** | The digest of the targeted content. |  [optional] |
| **mediaType** | **kotlin.String** | The media type of the object this schema refers to. |  [optional] |
| **platform** | [**OciPlatform**](OciPlatform.md) |  |  [optional] |
| **propertySize** | **kotlin.Long** | The size in bytes of the blob. |  [optional] |
| **urls** | **kotlin.collections.List&lt;kotlin.String&gt;** | List of URLs from which this object MAY be downloaded. |  [optional] |



