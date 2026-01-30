
# StackConfig

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **additionalEnvFiles** | [**kotlin.collections.List&lt;AdditionalEnvFile&gt;**](AdditionalEnvFile.md) | Add additional env files to attach with &#x60;--env-file&#x60;. Relative to the run directory root.  Note. It is already included as an &#x60;additional_file&#x60;. Don&#39;t add it again there. |  [optional] |
| **autoPull** | **kotlin.Boolean** | Whether to automatically &#x60;compose pull&#x60; before redeploying stack. Ensured latest images are deployed. Will fail if the compose file specifies a locally build image.  Note. Not used in Swarm mode. |  [optional] |
| **autoUpdate** | **kotlin.Boolean** | Whether to automatically redeploy when newer images are found. Will implicitly enable &#x60;poll_for_updates&#x60;, you don&#39;t need to enable both. |  [optional] |
| **autoUpdateAllServices** | **kotlin.Boolean** | If auto update is enabled, Komodo will by default only update the specific services with image updates. If this parameter is set to true, Komodo will redeploy the whole Stack (all services). |  [optional] |
| **branch** | **kotlin.String** | The branch of the repo. |  [optional] |
| **buildExtraArgs** | **kotlin.collections.List&lt;kotlin.String&gt;** | The extra arguments to pass after &#x60;docker compose build&#x60;. If empty, no extra build arguments will be passed. Only used if &#x60;run_build: true&#x60;  Note. Not used in Swarm mode. |  [optional] |
| **clonePath** | **kotlin.String** | Optionally set a specific clone path |  [optional] |
| **commit** | **kotlin.String** | Optionally set a specific commit hash. |  [optional] |
| **composeCmdWrapper** | **kotlin.String** | Optional command wrapper for secrets management tools. Wraps the docker compose up command with a prefix command. Use [[COMPOSE_COMMAND]] as placeholder for the full compose command.  Examples: - \&quot;op run -- [[COMPOSE_COMMAND]]\&quot; (1password CLI) - \&quot;sops exec-file --no-fifo /path/to/secret.env &#39;[[COMPOSE_COMMAND]]&#39;\&quot; (sops) |  [optional] |
| **configFiles** | [**kotlin.collections.List&lt;StackFileDependency&gt;**](StackFileDependency.md) | Add additional config files either in repo or on host to track. Can add any files associated with the stack to enable editing them in the UI. Doing so will also include diffing these when deciding to deploy in &#x60;DeployStackIfChanged&#x60;. Relative to the run directory.  Note. If the config file is .env and should be included in compose command using &#x60;--env-file&#x60;, add it to &#x60;additional_env_files&#x60; instead. |  [optional] |
| **destroyBeforeDeploy** | **kotlin.Boolean** | Whether to run &#x60;docker compose down&#x60; before &#x60;compose up&#x60;. |  [optional] |
| **envFilePath** | **kotlin.String** | The name of the written environment file before &#x60;docker compose up&#x60;. Relative to the run directory root. Default: .env  Note. Not used in Swarm mode. |  [optional] |
| **environment** | **kotlin.String** | The environment variables passed to the compose file. They will be written to path defined in env_file_path, which is given relative to the run directory.  If it is empty, no file will be written.  Note. Not used in Swarm mode. |  [optional] |
| **extraArgs** | **kotlin.collections.List&lt;kotlin.String&gt;** | The extra arguments to pass to the deploy command.  - For Compose stack, uses &#x60;docker compose up -d [EXTRA_ARGS]&#x60;. - For Swarm mode. &#x60;docker stack deploy [EXTRA_ARGS] STACK_NAME&#x60;  If empty, no extra arguments will be passed. |  [optional] |
| **fileContents** | **kotlin.String** | The contents of the file directly, for management in the UI. If this is empty, it will fall back to checking git config for repo based compose file. Supports variable / secret interpolation. |  [optional] |
| **filePaths** | **kotlin.collections.List&lt;kotlin.String&gt;** | Add paths to compose files, relative to the run path. If this is empty, will use file &#x60;compose.yaml&#x60;. |  [optional] |
| **filesOnHost** | **kotlin.Boolean** | If this is checked, the stack will source the files on the host. Use &#x60;run_directory&#x60; and &#x60;file_paths&#x60; to specify the path on the host. This is useful for those who wish to setup their files on the host, rather than defining the contents in UI or in a git repo. |  [optional] |
| **gitAccount** | **kotlin.String** | The git account used to access private repos. Passing empty string can only clone public repos.  Note. A token for the account must be available in the core config or the builder server&#39;s periphery config for the configured git provider. |  [optional] |
| **gitHttps** | **kotlin.Boolean** | Whether to use https to clone the repo (versus http). Default: true  Note. Komodo does not currently support cloning repos via ssh. |  [optional] |
| **gitProvider** | **kotlin.String** | The git provider domain. Default: github.com |  [optional] |
| **ignoreServices** | **kotlin.collections.List&lt;kotlin.String&gt;** | Ignore certain services declared in the compose file when checking the stack status. For example, an init service might be exited, but the stack should be healthy. This init service should be in &#x60;ignore_services&#x60; |  [optional] |
| **linkedRepo** | **kotlin.String** | Choose a Komodo Repo (Resource) to source the compose files. |  [optional] |
| **links** | **kotlin.collections.List&lt;kotlin.String&gt;** | Configure quick links that are displayed in the resource header |  [optional] |
| **pollForUpdates** | **kotlin.Boolean** | Whether to poll for any updates to the images. |  [optional] |
| **postDeploy** | [**SystemCommand**](SystemCommand.md) | The optional command to run after the Stack is deployed. |  [optional] |
| **preDeploy** | [**SystemCommand**](SystemCommand.md) | The optional command to run before the Stack is deployed. |  [optional] |
| **projectName** | **kotlin.String** | Optionally specify a custom project name for the stack. If this is empty string, it will default to the stack name. Used with &#x60;docker compose -p {project_name}&#x60; / &#x60;docker stack deploy {project_name}&#x60;.  Note. Can be used to import pre-existing stacks with names that do not match Stack name. |  [optional] |
| **reclone** | **kotlin.Boolean** | By default, the Stack will &#x60;git pull&#x60; the repo after it is first cloned. If this option is enabled, the repo folder will be deleted and recloned instead. |  [optional] |
| **registryAccount** | **kotlin.String** | Used with &#x60;registry_provider&#x60; to login to a registry before docker compose up. |  [optional] |
| **registryProvider** | **kotlin.String** | Used with &#x60;registry_account&#x60; to login to a registry before docker compose up. |  [optional] |
| **repo** | **kotlin.String** | The repo used as the source of the build. {namespace}/{repo_name} |  [optional] |
| **runBuild** | **kotlin.Boolean** | Whether to &#x60;docker compose build&#x60; before &#x60;compose down&#x60; / &#x60;compose up&#x60;. Combine with build_extra_args for custom behaviors.  Note. Not used in Swarm mode. |  [optional] |
| **runDirectory** | **kotlin.String** | Directory to change to (&#x60;cd&#x60;) before running &#x60;docker compose up -d&#x60;. |  [optional] |
| **sendAlerts** | **kotlin.Boolean** | Whether to send StackStateChange alerts for this stack. |  [optional] |
| **serverId** | **kotlin.String** | The Server to deploy the Stack on, setting the Stack into Compose mode.  Note. If both swarm_id and server_id are set, swarm_id overrides server_id and the Stack will be in Swarm mode. |  [optional] |
| **skipSecretInterp** | **kotlin.Boolean** | Whether to skip secret interpolation into the stack environment variables. |  [optional] |
| **swarmId** | **kotlin.String** | The Swarm to deploy the Stack on, setting the Stack into Swarm mode.  Note. If both swarm_id and server_id are set, swarm_id overrides server_id and the Stack will be in Swarm mode. |  [optional] |
| **webhookEnabled** | **kotlin.Boolean** | Whether incoming webhooks actually trigger action. |  [optional] |
| **webhookForceDeploy** | **kotlin.Boolean** | By default, the Stack will &#x60;DeployStackIfChanged&#x60;. If this option is enabled, will always run &#x60;DeployStack&#x60; without diffing. |  [optional] |
| **webhookSecret** | **kotlin.String** | Optionally provide an alternate webhook secret for this stack. If its an empty string, use the default secret from the config. |  [optional] |



