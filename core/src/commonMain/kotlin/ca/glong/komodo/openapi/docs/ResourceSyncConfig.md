
# ResourceSyncConfig

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **branch** | **kotlin.String** | The branch of the repo. |  [optional] |
| **commit** | **kotlin.String** | Optionally set a specific commit hash. |  [optional] |
| **delete** | **kotlin.Boolean** | Whether sync should delete resources not declared in the resource files |  [optional] |
| **fileContents** | **kotlin.String** | Manage the file contents in the UI. |  [optional] |
| **filesOnHost** | **kotlin.Boolean** | Files are available on the Komodo Core host. Specify the file / folder with [ResourceSyncConfig::resource_path]. |  [optional] |
| **gitAccount** | **kotlin.String** | The git account used to access private repos. Passing empty string can only clone public repos.  Note. A token for the account must be available in the core config or the builder server&#39;s periphery config for the configured git provider. |  [optional] |
| **gitHttps** | **kotlin.Boolean** | Whether to use https to clone the repo (versus http). Default: true  Note. Komodo does not currently support cloning repos via ssh. |  [optional] |
| **gitProvider** | **kotlin.String** | The git provider domain. Default: github.com |  [optional] |
| **includeResources** | **kotlin.Boolean** | Whether sync should include resources. Default: true |  [optional] |
| **includeUserGroups** | **kotlin.Boolean** | Whether sync should include user groups. |  [optional] |
| **includeVariables** | **kotlin.Boolean** | Whether sync should include variables. |  [optional] |
| **linkedRepo** | **kotlin.String** | Choose a Komodo Repo (Resource) to source the sync files. |  [optional] |
| **managed** | **kotlin.Boolean** | Enable \&quot;pushes\&quot; to the file, which exports resources matching tags to single file.  - If using &#x60;files_on_host&#x60;, it is stored in the file_contents, which must point to a .toml file path (it will be created if it doesn&#39;t exist).  - If using &#x60;file_contents&#x60;, it is stored in the database. When using this, \&quot;delete\&quot; mode is always enabled. |  [optional] |
| **matchTags** | **kotlin.collections.List&lt;kotlin.String&gt;** | When using &#x60;managed&#x60; resource sync, will only export resources matching all of the given tags. If none, will match all resources. |  [optional] |
| **pendingAlert** | **kotlin.Boolean** | Whether sync should send alert when it enters Pending state. Default: true |  [optional] |
| **repo** | **kotlin.String** | The Github repo used as the source of the build. |  [optional] |
| **resourcePath** | **kotlin.collections.List&lt;kotlin.String&gt;** | The path of the resource file(s) to sync.  - If Files on Host, this is relative to the configured &#x60;sync_directory&#x60; in core config.  - If Git Repo based, this is relative to the root of the repo. Can be a specific file, or a directory containing multiple files / folders. See [https://komo.do/docs/sync-resources](https://komo.do/docs/sync-resources) for more information. |  [optional] |
| **webhookEnabled** | **kotlin.Boolean** | Whether incoming webhooks actually trigger action. |  [optional] |
| **webhookSecret** | **kotlin.String** | Optionally provide an alternate webhook secret for this sync. If its an empty string, use the default secret from the config. |  [optional] |



