
# StackServiceNames

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **containerName** | **kotlin.String** | Will either be the declared container_name in the compose file, or a pattern to match auto named containers.  Auto named containers are composed of three parts:  1. The name of the compose project (top level name field of compose file).    This defaults to the name of the parent folder of the compose file.    Komodo will always set it to be the name of the stack, but imported stacks    will have a different name. 2. The service name 3. The replica number  Example: stacko-mongo-1.  This stores only 1. and 2., ie stacko-mongo. Containers will be matched via regex like &#x60;^container_name-?[0-9]*$&#x60;&#x60;  Note. Setting container_name is not supported by Swarm, so will always be 1. and 2. in Swarm mode. |  |
| **serviceName** | **kotlin.String** | The name of the service |  |
| **image** | **kotlin.String** | The services image. |  [optional] |
| **imageDigest** | **kotlin.String** | Store the associated image digest. This includes both the image name / tag, and the specific digest hash. |  [optional] |



