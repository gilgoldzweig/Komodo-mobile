
# ImageManifestSummary

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **available** | **kotlin.Boolean** | Indicates whether all the child content (image config, layers) is fully available locally. |  |
| **descriptor** | [**OciDescriptor**](OciDescriptor.md) |  |  |
| **ID** | **kotlin.String** | ID is the content-addressable ID of an image and is the same as the digest of the image manifest. |  |
| **propertySize** | [**ImageManifestSummarySize**](ImageManifestSummarySize.md) |  |  |
| **attestationData** | [**ImageManifestSummaryAttestationData**](ImageManifestSummaryAttestationData.md) |  |  [optional] |
| **imageData** | [**ImageManifestSummaryImageData**](ImageManifestSummaryImageData.md) |  |  [optional] |
| **kind** | [**ImageManifestSummaryKindEnum**](ImageManifestSummaryKindEnum.md) | The kind of the manifest.  kind         | description -------------|----------------------------------------------------------- image        | Image manifest that can be used to start a container. attestation  | Attestation manifest produced by the Buildkit builder for a specific image manifest. |  [optional] |



