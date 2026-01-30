
# User

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **config** | [**UserConfig**](UserConfig.md) | The primary user login. |  |
| **username** | **kotlin.String** | The globally unique username for the user. |  |
| **id** | **kotlin.String** | The Mongo ID of the User. This field is de/serialized from/to JSON as &#x60;{ \&quot;_id\&quot;: { \&quot;$oid\&quot;: \&quot;...\&quot; }, ...(rest of User schema) }&#x60; |  [optional] |
| **admin** | **kotlin.Boolean** | Whether the user has global admin permissions. |  [optional] |
| **all** | [**kotlin.collections.Map&lt;kotlin.String, PermissionLevelAndSpecifics&gt;**](PermissionLevelAndSpecifics.md) | Give the user elevated permissions on all resources of a certain type |  [optional] |
| **createBuildPermissions** | **kotlin.Boolean** | Whether the user has permission to create builds |  [optional] |
| **createServerPermissions** | **kotlin.Boolean** | Whether the user has permission to create servers. |  [optional] |
| **enabled** | **kotlin.Boolean** | Whether user is enabled / able to access the api. |  [optional] |
| **externalSkip2fa** | **kotlin.Boolean** | Allow external / third party logins to skip 2fa. |  [optional] |
| **lastUpdateView** | **kotlin.Long** | When the user last opened updates dropdown. |  [optional] |
| **linkedLogins** | [**kotlin.collections.Map&lt;kotlin.String, UserConfig&gt;**](UserConfig.md) | Additional linked login methods. May not contain &#39;Service&#39; type config. |  [optional] |
| **passkey** | [**UserPasskeyConfig**](UserPasskeyConfig.md) | WebAuthn Passkey 2fa credentials |  [optional] |
| **recents** | **kotlin.collections.Map&lt;kotlin.String, kotlin.collections.List&lt;kotlin.String&gt;&gt;** | Recently viewed ids |  [optional] |
| **superAdmin** | **kotlin.Boolean** | Can give / take other users admin priviledges. |  [optional] |
| **totp** | [**UserTotpConfig**](UserTotpConfig.md) | TOTP 2fa credentials |  [optional] |
| **updatedAt** | **kotlin.Long** |  |  [optional] |



