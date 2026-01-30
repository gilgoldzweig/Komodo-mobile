
# DeploymentConfig

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **autoUpdate** | **kotlin.Boolean** | Whether to automatically redeploy when newer a image is found. Will implicitly enable &#x60;poll_for_updates&#x60;, you don&#39;t need to enable both. |  [optional] |
| **command** | **kotlin.String** | This is interpolated at the end of the &#x60;docker run&#x60; command, which means they are either passed to the containers inner process, or replaces the container command, depending on use of ENTRYPOINT or CMD in dockerfile. Empty is no command. |  [optional] |
| **environment** | **kotlin.String** | The environment variables passed to the container / service. |  [optional] |
| **extraArgs** | **kotlin.collections.List&lt;kotlin.String&gt;** | Extra args which are interpolated into the &#x60;docker run&#x60; / &#x60;docker service create&#x60; command, and affect the container configuration.  - Container ref: https://docs.docker.com/reference/cli/docker/container/run/#options - Swarm Service ref: https://docs.docker.com/reference/cli/docker/service/create/#options |  [optional] |
| **image** | [**DeploymentImage**](DeploymentImage.md) | The image which the deployment deploys. Can either be a user inputted image, or a Komodo Build. |  [optional] |
| **imageRegistryAccount** | **kotlin.String** | Configure the account used to pull the image from the registry. Used with &#x60;docker login&#x60;.   - If the field is empty string, will use the same account config as the build, or none at all if using image.  - If the field contains an account, a token for the account must be available.  - Will get the registry domain from the build / image |  [optional] |
| **labels** | **kotlin.String** | The docker labels given to the container. |  [optional] |
| **links** | **kotlin.collections.List&lt;kotlin.String&gt;** | Configure quick links that are displayed in the resource header |  [optional] |
| **network** | **kotlin.String** | The network attached to the container. Default is &#x60;host&#x60;. |  [optional] |
| **pollForUpdates** | **kotlin.Boolean** | Whether to poll for any updates to the image. |  [optional] |
| **ports** | **kotlin.String** | The container port mapping. Irrelevant if container network is &#x60;host&#x60;. Maps ports on host to ports on container. |  [optional] |
| **redeployOnBuild** | **kotlin.Boolean** | Whether to redeploy the deployment whenever the attached build finishes. |  [optional] |
| **replicas** | **kotlin.Int** | The number of replicas for the Service.  Note. Only used in Swarm mode. |  [optional] |
| **restart** | [**RestartMode**](RestartMode.md) | The restart mode given to the container. |  [optional] |
| **sendAlerts** | **kotlin.Boolean** | Whether to send ContainerStateChange alerts for this deployment. |  [optional] |
| **serverId** | **kotlin.String** | The Server to deploy the Deployment on, setting the Deployment into Container mode.  Note. If both swarm_id and server_id are set, swarm_id overrides server_id and the Deployment will be in Swarm mode. |  [optional] |
| **skipSecretInterp** | **kotlin.Boolean** | Whether to skip secret interpolation into the deployment environment variables. |  [optional] |
| **swarmId** | **kotlin.String** | The Swarm to deploy the Deployment on (as a Swarm Service), setting the Deployment into Swarm mode.  Note. If both swarm_id and server_id are set, swarm_id overrides server_id and the Deployment will be in Swarm mode. |  [optional] |
| **termSignalLabels** | **kotlin.String** | Labels attached to various termination signal options. Used to specify different shutdown functionality depending on the termination signal. |  [optional] |
| **terminationSignal** | [**TerminationSignal**](TerminationSignal.md) | The default termination signal to use to stop the deployment. Defaults to SigTerm (default docker signal). |  [optional] |
| **terminationTimeout** | **kotlin.Int** | The termination timeout. |  [optional] |
| **volumes** | **kotlin.String** | The container volume mapping. Maps files / folders on host to files / folders in container. |  [optional] |



