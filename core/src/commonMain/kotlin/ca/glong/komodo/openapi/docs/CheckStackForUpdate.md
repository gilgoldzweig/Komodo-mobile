
# CheckStackForUpdate

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **stack** | **kotlin.String** | Name or id |  |
| **skipAutoUpdate** | **kotlin.Boolean** | Normally resources with &#39;auto_update&#39; will be redeployed immediately if updates are found. With this enabled, convert this into an UpdateAvailable alert. |  [optional] |
| **skipCacheRefresh** | **kotlin.Boolean** | Usually will refresh the stack cache before checking for updates. Skip with this option. |  [optional] |
| **waitForAutoUpdate** | **kotlin.Boolean** | If check triggers auto deploy, whether this call should wait on the auto deploy, or run it in the background. |  [optional] |



