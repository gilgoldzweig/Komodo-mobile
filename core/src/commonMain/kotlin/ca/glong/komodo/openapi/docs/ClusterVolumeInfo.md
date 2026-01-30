
# ClusterVolumeInfo

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **accessibleTopology** | **kotlin.collections.List&lt;kotlin.collections.Map&lt;kotlin.String, kotlin.collections.List&lt;HashMapValueInner&gt;&gt;&gt;** | The topology this volume is actually accessible from. |  [optional] |
| **capacityBytes** | **kotlin.Long** | The capacity of the volume in bytes. A value of 0 indicates that the capacity is unknown. |  [optional] |
| **volumeContext** | **kotlin.collections.Map&lt;kotlin.String, kotlin.String&gt;** | A map of strings to strings returned from the storage plugin when the volume is created. |  [optional] |
| **volumeID** | **kotlin.String** | The ID of the volume as returned by the CSI storage plugin. This is distinct from the volume&#39;s ID as provided by Docker. This ID is never used by the user when communicating with Docker to refer to this volume. If the ID is blank, then the Volume has not been successfully created in the plugin yet. |  [optional] |



