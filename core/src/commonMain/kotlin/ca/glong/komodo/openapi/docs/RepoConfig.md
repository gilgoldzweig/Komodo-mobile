
# RepoConfig

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **branch** | **kotlin.String** | The repo branch. |  [optional] |
| **builderId** | **kotlin.String** | Attach a builder to &#39;build&#39; the repo. |  [optional] |
| **commit** | **kotlin.String** | Optionally set a specific commit hash. |  [optional] |
| **envFilePath** | **kotlin.String** | The name of the written environment file before &#x60;docker compose up&#x60;. Relative to the repo root. Default: .env |  [optional] |
| **environment** | **kotlin.String** | The environment variables passed to the compose file. They will be written to path defined in env_file_path, which is given relative to the run directory.  If it is empty, no file will be written. |  [optional] |
| **gitAccount** | **kotlin.String** | The git account used to access private repos. Passing empty string can only clone public repos.  Note. A token for the account must be available in the core config or the builder server&#39;s periphery config for the configured git provider. |  [optional] |
| **gitHttps** | **kotlin.Boolean** | Whether to use https to clone the repo (versus http). Default: true  Note. Komodo does not currently support cloning repos via ssh. |  [optional] |
| **gitProvider** | **kotlin.String** | The git provider domain. Default: github.com |  [optional] |
| **links** | **kotlin.collections.List&lt;kotlin.String&gt;** | Configure quick links that are displayed in the resource header |  [optional] |
| **onClone** | [**SystemCommand**](SystemCommand.md) | Command to be run after the repo is cloned. The path is relative to the root of the repo. |  [optional] |
| **onPull** | [**SystemCommand**](SystemCommand.md) | Command to be run after the repo is pulled. The path is relative to the root of the repo. |  [optional] |
| **path** | **kotlin.String** | Explicitly specify the folder to clone the repo in. - If absolute (has leading &#39;/&#39;)   - Used directly as the path - If relative   - Taken relative to Periphery &#x60;repo_dir&#x60; (ie &#x60;${root_directory}/repos&#x60;) |  [optional] |
| **repo** | **kotlin.String** | The github repo to clone. |  [optional] |
| **serverId** | **kotlin.String** | The server to clone the repo on. |  [optional] |
| **skipSecretInterp** | **kotlin.Boolean** | Whether to skip secret interpolation into the repo environment variable file. |  [optional] |
| **webhookEnabled** | **kotlin.Boolean** | Whether incoming webhooks actually trigger action. |  [optional] |
| **webhookSecret** | **kotlin.String** | Optionally provide an alternate webhook secret for this repo. If its an empty string, use the default secret from the config. |  [optional] |



