
# ImageManifestSummaryImageData

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **containers** | **kotlin.collections.List&lt;kotlin.String&gt;** | The IDs of the containers that are using this image. |  |
| **platform** | [**OciPlatform**](OciPlatform.md) | OCI platform of the image. This will be the platform specified in the manifest descriptor from the index/manifest list. If it&#39;s not available, it will be obtained from the image config. |  |
| **propertySize** | [**ImageManifestSummaryImageDataSize**](ImageManifestSummaryImageDataSize.md) |  |  |



