
# ClusterVolumeSpec

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **accessMode** | [**ClusterVolumeSpecAccessMode**](ClusterVolumeSpecAccessMode.md) |  |  [optional] |
| **group** | **kotlin.String** | Group defines the volume group of this volume. Volumes belonging to the same group can be referred to by group name when creating Services.  Referring to a volume by group instructs Swarm to treat volumes in that group interchangeably for the purpose of scheduling. Volumes with an empty string for a group technically all belong to the same, emptystring group. |  [optional] |



