
# Volume

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **driver** | **kotlin.String** | Name of the volume driver used by the volume. |  |
| **mountpoint** | **kotlin.String** | Mount path of the volume on the host. |  |
| **name** | **kotlin.String** | Name of the volume. |  |
| **clusterVolume** | [**ClusterVolume**](ClusterVolume.md) |  |  [optional] |
| **createdAt** | **kotlin.String** | Date/Time the volume was created. |  [optional] |
| **labels** | **kotlin.collections.Map&lt;kotlin.String, kotlin.String&gt;** | User-defined key/value metadata. |  [optional] |
| **options** | **kotlin.collections.Map&lt;kotlin.String, kotlin.String&gt;** | The driver specific options used when creating the volume. |  [optional] |
| **scope** | [**VolumeScopeEnum**](VolumeScopeEnum.md) | The level at which the volume exists. Either &#x60;global&#x60; for cluster-wide, or &#x60;local&#x60; for machine level. |  [optional] |
| **status** | **kotlin.collections.List&lt;kotlin.String&gt;** | Low-level details about the volume, provided by the volume driver. Details are returned as a map with key/value pairs: &#x60;{\\\&quot;key\\\&quot;:\\\&quot;value\\\&quot;,\\\&quot;key2\\\&quot;:\\\&quot;value2\\\&quot;}&#x60;.  The &#x60;Status&#x60; field is optional, and is omitted if the volume driver does not support this feature. |  [optional] |
| **usageData** | [**VolumeUsageData**](VolumeUsageData.md) |  |  [optional] |



