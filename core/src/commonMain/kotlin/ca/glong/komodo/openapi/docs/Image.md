
# Image

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **architecture** | **kotlin.String** | Hardware CPU architecture that the image runs on. |  [optional] |
| **author** | **kotlin.String** | Name of the author that was specified when committing the image, or as specified through MAINTAINER (deprecated) in the Dockerfile. |  [optional] |
| **comment** | **kotlin.String** | Optional message that was set when committing or importing the image. |  [optional] |
| **config** | [**ImageConfig**](ImageConfig.md) |  |  [optional] |
| **created** | **kotlin.String** | Date and time at which the image was created, formatted in [RFC 3339](https://www.ietf.org/rfc/rfc3339.txt) format with nano-seconds.  This information is only available if present in the image, and omitted otherwise. |  [optional] |
| **descriptor** | [**OciDescriptor**](OciDescriptor.md) | Descriptor is an OCI descriptor of the image target. In case of a multi-platform image, this descriptor points to the OCI index or a manifest list.  This field is only present if the daemon provides a multi-platform image store.  WARNING: This is experimental and may change at any time without any backward compatibility. |  [optional] |
| **graphDriver** | [**GraphDriverData**](GraphDriverData.md) |  |  [optional] |
| **id** | **kotlin.String** | ID is the content-addressable ID of an image.  This identifier is a content-addressable digest calculated from the image&#39;s configuration (which includes the digests of layers used by the image).  Note that this digest differs from the &#x60;RepoDigests&#x60; below, which holds digests of image manifests that reference the image. |  [optional] |
| **manifests** | [**kotlin.collections.List&lt;ImageManifestSummary&gt;**](ImageManifestSummary.md) | Manifests is a list of image manifests available in this image. It provides a more detailed view of the platform-specific image manifests or other image-attached data like build attestations.  Only available if the daemon provides a multi-platform image store and the &#x60;manifests&#x60; option is set in the inspect request.  WARNING: This is experimental and may change at any time without any backward compatibility. |  [optional] |
| **metadata** | [**ImageInspectMetadata**](ImageInspectMetadata.md) |  |  [optional] |
| **os** | **kotlin.String** | Operating System the image is built to run on. |  [optional] |
| **osVersion** | **kotlin.String** | Operating System version the image is built to run on (especially for Windows). |  [optional] |
| **repoDigests** | **kotlin.collections.List&lt;kotlin.String&gt;** | List of content-addressable digests of locally available image manifests that the image is referenced from. Multiple manifests can refer to the same image.  These digests are usually only available if the image was either pulled from a registry, or if the image was pushed to a registry, which is when the manifest is generated and its digest calculated. |  [optional] |
| **repoTags** | **kotlin.collections.List&lt;kotlin.String&gt;** | List of image names/tags in the local image cache that reference this image.  Multiple image tags can refer to the same image, and this list may be empty if no tags reference the image, in which case the image is \\\&quot;untagged\\\&quot;, in which case it can still be referenced by its ID. |  [optional] |
| **rootFS** | [**ImageInspectRootFs**](ImageInspectRootFs.md) |  |  [optional] |
| **propertySize** | **kotlin.Long** | Total size of the image including all layers it is composed of. |  [optional] |
| **variant** | **kotlin.String** | CPU architecture variant (presently ARM-only). |  [optional] |



