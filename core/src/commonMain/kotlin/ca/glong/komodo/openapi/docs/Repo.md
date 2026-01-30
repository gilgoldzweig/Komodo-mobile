
# Repo

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **name** | **kotlin.String** | The resource name. This is guaranteed unique among others of the same resource type. |  |
| **id** | [**MongoIdObj**](MongoIdObj.md) | The Mongo ID of the resource. This field is de/serialized from/to JSON as &#x60;{ \&quot;_id\&quot;: { \&quot;$oid\&quot;: \&quot;...\&quot; }, ...(rest of serialized Resource&lt;T&gt;) }&#x60; |  [optional] |
| **basePermission** | [**PermissionLevelAndSpecifics**](PermissionLevelAndSpecifics.md) | Set a base permission level that all users will have on the resource. |  [optional] |
| **config** | [**RepoConfig**](RepoConfig.md) |  |  [optional] |
| **description** | **kotlin.String** | A description for the resource |  [optional] |
| **info** | [**RepoInfo**](RepoInfo.md) |  |  [optional] |
| **tags** | **kotlin.collections.List&lt;kotlin.String&gt;** | Tag Ids |  [optional] |
| **template** | **kotlin.Boolean** | Mark resource as a template |  [optional] |
| **updatedAt** | **kotlin.Long** | When description last updated |  [optional] |



