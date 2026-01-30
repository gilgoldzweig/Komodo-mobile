
# TaskSpecContainerSpec

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **args** | **kotlin.collections.List&lt;kotlin.String&gt;** | Arguments to the command. |  [optional] |
| **capabilityAdd** | **kotlin.collections.List&lt;kotlin.String&gt;** | A list of kernel capabilities to add to the default set for the container. |  [optional] |
| **capabilityDrop** | **kotlin.collections.List&lt;kotlin.String&gt;** | A list of kernel capabilities to drop from the default set for the container. |  [optional] |
| **command** | **kotlin.collections.List&lt;kotlin.String&gt;** | The command to be run in the image. |  [optional] |
| **configs** | [**kotlin.collections.List&lt;TaskSpecContainerSpecConfigs&gt;**](TaskSpecContainerSpecConfigs.md) | Configs contains references to zero or more configs that will be exposed to the service. |  [optional] |
| **dnSConfig** | [**TaskSpecContainerSpecDnsConfig**](TaskSpecContainerSpecDnsConfig.md) |  |  [optional] |
| **dir** | **kotlin.String** | The working directory for commands to run in. |  [optional] |
| **env** | **kotlin.collections.List&lt;kotlin.String&gt;** | A list of environment variables in the form &#x60;VAR&#x3D;value&#x60;. |  [optional] |
| **groups** | **kotlin.collections.List&lt;kotlin.String&gt;** | A list of additional groups that the container process will run as. |  [optional] |
| **healthCheck** | [**HealthConfig**](HealthConfig.md) |  |  [optional] |
| **hostname** | **kotlin.String** | The hostname to use for the container, as a valid [RFC 1123](https://tools.ietf.org/html/rfc1123) hostname. |  [optional] |
| **hosts** | **kotlin.collections.List&lt;kotlin.String&gt;** | A list of hostname/IP mappings to add to the container&#39;s &#x60;hosts&#x60; file. The format of extra hosts is specified in the [hosts(5)](http://man7.org/linux/man-pages/man5/hosts.5.html) man page:      IP_address canonical_hostname [aliases...] |  [optional] |
| **image** | **kotlin.String** | The image name to use for the container |  [optional] |
| **&#x60;init&#x60;** | **kotlin.Boolean** | Run an init inside the container that forwards signals and reaps processes. This field is omitted if empty, and the default (as configured on the daemon) is used. |  [optional] |
| **isolation** | [**TaskSpecContainerSpecIsolationEnum**](TaskSpecContainerSpecIsolationEnum.md) | Isolation technology of the containers running the service. (Windows only) |  [optional] |
| **labels** | **kotlin.collections.Map&lt;kotlin.String, kotlin.String&gt;** | User-defined key/value data. |  [optional] |
| **mounts** | [**kotlin.collections.List&lt;Mount&gt;**](Mount.md) | Specification for mounts to be added to containers created as part of the service. |  [optional] |
| **oomScoreAdj** | **kotlin.Long** | An integer value containing the score given to the container in order to tune OOM killer preferences. |  [optional] |
| **openStdin** | **kotlin.Boolean** | Open &#x60;stdin&#x60; |  [optional] |
| **privileges** | [**TaskSpecContainerSpecPrivileges**](TaskSpecContainerSpecPrivileges.md) |  |  [optional] |
| **readOnly** | **kotlin.Boolean** | Mount the container&#39;s root filesystem as read only. |  [optional] |
| **secrets** | [**kotlin.collections.List&lt;TaskSpecContainerSpecSecrets&gt;**](TaskSpecContainerSpecSecrets.md) | Secrets contains references to zero or more secrets that will be exposed to the service. |  [optional] |
| **stopGracePeriod** | **kotlin.Long** | Amount of time to wait for the container to terminate before forcefully killing it. |  [optional] |
| **stopSignal** | **kotlin.String** | Signal to stop the container. |  [optional] |
| **sysctls** | **kotlin.collections.Map&lt;kotlin.String, kotlin.String&gt;** | Set kernel namedspaced parameters (sysctls) in the container. The Sysctls option on services accepts the same sysctls as the are supported on containers. Note that while the same sysctls are supported, no guarantees or checks are made about their suitability for a clustered environment, and it&#39;s up to the user to determine whether a given sysctl will work properly in a Service. |  [optional] |
| **TTY** | **kotlin.Boolean** | Whether a pseudo-TTY should be allocated. |  [optional] |
| **ulimits** | [**kotlin.collections.List&lt;ResourcesUlimits&gt;**](ResourcesUlimits.md) | A list of resource limits to set in the container. For example: &#x60;{\\\&quot;Name\\\&quot;: \\\&quot;nofile\\\&quot;, \\\&quot;Soft\\\&quot;: 1024, \\\&quot;Hard\\\&quot;: 2048}&#x60;\\\&quot; |  [optional] |
| **user** | **kotlin.String** | The user inside the container. |  [optional] |



