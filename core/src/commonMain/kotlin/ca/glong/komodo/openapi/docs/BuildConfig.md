
# BuildConfig

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **autoIncrementVersion** | **kotlin.Boolean** | Whether to automatically increment the patch on every build. Default is &#x60;true&#x60; |  [optional] |
| **branch** | **kotlin.String** | The branch of the repo. |  [optional] |
| **buildArgs** | **kotlin.String** | Docker build arguments.  These values are visible in the final image by running &#x60;docker inspect&#x60;. |  [optional] |
| **buildPath** | **kotlin.String** | The path of the docker build context relative to the root of the repo. Default: \&quot;.\&quot; (the root of the repo). |  [optional] |
| **builderId** | **kotlin.String** | Which builder is used to build the image. |  [optional] |
| **commit** | **kotlin.String** | Optionally set a specific commit hash. |  [optional] |
| **dockerfile** | **kotlin.String** | UI defined dockerfile contents. Supports variable / secret interpolation. |  [optional] |
| **dockerfilePath** | **kotlin.String** | The path of the dockerfile relative to the build path. |  [optional] |
| **extraArgs** | **kotlin.collections.List&lt;kotlin.String&gt;** | Any extra docker cli arguments to be included in the build command |  [optional] |
| **filesOnHost** | **kotlin.Boolean** | If this is checked, the build will source the files on the host. Use &#x60;build_path&#x60; and &#x60;dockerfile_path&#x60; to specify the path on the host. This is useful for those who wish to setup their files on the host, rather than defining the contents in UI or in a git repo. |  [optional] |
| **gitAccount** | **kotlin.String** | The git account used to access private repos. Passing empty string can only clone public repos.  Note. A token for the account must be available in the core config or the builder server&#39;s periphery config for the configured git provider. |  [optional] |
| **gitHttps** | **kotlin.Boolean** | Whether to use https to clone the repo (versus http). Default: true  Note. Komodo does not currently support cloning repos via ssh. |  [optional] |
| **gitProvider** | **kotlin.String** | The git provider domain. Default: github.com |  [optional] |
| **imageName** | **kotlin.String** | An alternate name for the image pushed to the repository. If this is empty, it will use the build name.  Can be used in conjunction with &#x60;image_tag&#x60; to direct multiple builds with different configs to push to the same image registry, under different, independantly versioned tags. |  [optional] |
| **imageRegistry** | [**kotlin.collections.List&lt;ImageRegistryConfig&gt;**](ImageRegistryConfig.md) | Configuration for the registry/s to push the built image to. The first registry in this list will be used with attached Deployments. |  [optional] |
| **imageTag** | **kotlin.String** | An extra tag put after the build version, for the image pushed to the repository. Eg. in image tag of &#x60;aarch64&#x60; would push to moghtech/komodo-core:1.13.2-aarch64. If this is empty, the image tag will just be the build version.  Can be used in conjunction with &#x60;image_name&#x60; to direct multiple builds with different configs to push to the same image registry, under different, independantly versioned tags. |  [optional] |
| **includeCommitTag** | **kotlin.Boolean** | Push commit hash &#x60;:a6v8h83&#x60; / &#x60;:a6v8h83-image_tag&#x60; tags. |  [optional] |
| **includeLatestTag** | **kotlin.Boolean** | Push &#x60;:latest&#x60; / &#x60;:latest-image_tag&#x60; tags. |  [optional] |
| **includeVersionTags** | **kotlin.Boolean** | Push build version semver &#x60;:1.19.5&#x60; + &#x60;1.19&#x60; / &#x60;:1.19.5-image_tag&#x60; tags. |  [optional] |
| **labels** | **kotlin.String** | Docker labels |  [optional] |
| **linkedRepo** | **kotlin.String** | Choose a Komodo Repo (Resource) to source the build files. |  [optional] |
| **links** | **kotlin.collections.List&lt;kotlin.String&gt;** | Configure quick links that are displayed in the resource header |  [optional] |
| **preBuild** | [**SystemCommand**](SystemCommand.md) | The optional command run after repo clone and before docker build. |  [optional] |
| **repo** | **kotlin.String** | The repo used as the source of the build. |  [optional] |
| **secretArgs** | **kotlin.String** | Secret arguments.  These values remain hidden in the final image by using docker secret mounts. See &lt;https://docs.docker.com/build/building/secrets&gt;.  The values can be used in RUN commands: &#x60;&#x60;&#x60;sh RUN --mount&#x3D;type&#x3D;secret,id&#x3D;SECRET_KEY \\   SECRET_KEY&#x3D;$(cat /run/secrets/SECRET_KEY) ... &#x60;&#x60;&#x60; |  [optional] |
| **skipSecretInterp** | **kotlin.Boolean** | Whether to skip secret interpolation in the build_args. |  [optional] |
| **useBuildx** | **kotlin.Boolean** | Whether to use buildx to build (eg &#x60;docker buildx build ...&#x60;) |  [optional] |
| **version** | [**Version**](Version.md) | The current version of the build. |  [optional] |
| **webhookEnabled** | **kotlin.Boolean** | Whether incoming webhooks actually trigger action. |  [optional] |
| **webhookSecret** | **kotlin.String** | Optionally provide an alternate webhook secret for this build. If its an empty string, use the default secret from the config. |  [optional] |



