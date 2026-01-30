
# VecInner

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **name** | **kotlin.String** | A name for the user group |  |
| **id** | **kotlin.String** | The Mongo ID of the UserGroup. This field is de/serialized from/to JSON as &#x60;{ \&quot;_id\&quot;: { \&quot;$oid\&quot;: \&quot;...\&quot; }, ...(rest of serialized User) }&#x60; |  [optional] |
| **all** | [**kotlin.collections.Map&lt;kotlin.String, PermissionLevelAndSpecifics&gt;**](PermissionLevelAndSpecifics.md) | Give the user group elevated permissions on all resources of a certain type |  [optional] |
| **everyone** | **kotlin.Boolean** | Whether all users will implicitly have the permissions in this group. |  [optional] |
| **updatedAt** | **kotlin.Long** | Unix time (ms) when user group last updated |  [optional] |
| **users** | **kotlin.collections.List&lt;kotlin.String&gt;** | User ids of group members |  [optional] |



