
# ContainerListItem

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **name** | **kotlin.String** | The first name in Names, not including the initial &#39;/&#39; |  |
| **state** | [**ContainerStateStatusEnum**](ContainerStateStatusEnum.md) | The state of this container (e.g. &#x60;exited&#x60;) |  |
| **created** | **kotlin.Long** | When the container was created |  [optional] |
| **id** | **kotlin.String** | The ID of this container |  [optional] |
| **image** | **kotlin.String** | The name of the image used when creating this container |  [optional] |
| **imageId** | **kotlin.String** | The ID of the image that this container was created from |  [optional] |
| **networkMode** | **kotlin.String** | The network mode |  [optional] |
| **networks** | **kotlin.collections.List&lt;kotlin.String&gt;** | The network names attached to container |  [optional] |
| **ports** | [**kotlin.collections.List&lt;Port&gt;**](Port.md) | Port mappings for the container |  [optional] |
| **serverId** | **kotlin.String** | The Server which hosts the container. |  [optional] |
| **sizeRootFs** | **kotlin.Long** | The total size of all the files in this container |  [optional] |
| **sizeRw** | **kotlin.Long** | The size of files that have been created or changed by this container |  [optional] |
| **stats** | [**ContainerStats**](ContainerStats.md) | The container stats, if they can be retreived. |  [optional] |
| **status** | **kotlin.String** | Additional human-readable status of this container (e.g. &#x60;Exit 0&#x60;) |  [optional] |
| **volumes** | **kotlin.collections.List&lt;kotlin.String&gt;** | The volume names attached to container |  [optional] |



