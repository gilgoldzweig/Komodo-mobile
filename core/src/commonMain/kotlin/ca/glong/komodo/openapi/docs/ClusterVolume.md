
# ClusterVolume

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **createdAt** | **kotlin.String** |  |  [optional] |
| **ID** | **kotlin.String** | The Swarm ID of this volume. Because cluster volumes are Swarm objects, they have an ID, unlike non-cluster volumes. This ID can be used to refer to the Volume instead of the name. |  [optional] |
| **info** | [**ClusterVolumeInfo**](ClusterVolumeInfo.md) |  |  [optional] |
| **publishStatus** | [**kotlin.collections.List&lt;ClusterVolumePublishStatus&gt;**](ClusterVolumePublishStatus.md) | The status of the volume as it pertains to its publishing and use on specific nodes |  [optional] |
| **spec** | [**ClusterVolumeSpec**](ClusterVolumeSpec.md) |  |  [optional] |
| **updatedAt** | **kotlin.String** |  |  [optional] |
| **version** | [**ObjectVersion**](ObjectVersion.md) |  |  [optional] |



