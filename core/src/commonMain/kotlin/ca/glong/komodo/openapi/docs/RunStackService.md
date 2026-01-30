
# RunStackService

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **service** | **kotlin.String** | Service to run |  |
| **stack** | **kotlin.String** | Id or name |  |
| **command** | **kotlin.collections.List&lt;kotlin.String&gt;** | Command and args to pass to the service container |  [optional] |
| **detach** | **kotlin.Boolean** | Detach container on run |  [optional] |
| **entrypoint** | **kotlin.String** | Override the default entrypoint |  [optional] |
| **env** | **kotlin.collections.Map&lt;kotlin.String, kotlin.String&gt;** | Extra environment variables for the run |  [optional] |
| **noDeps** | **kotlin.Boolean** | Do not start linked services |  [optional] |
| **noTty** | **kotlin.Boolean** | Do not allocate TTY |  [optional] |
| **pull** | **kotlin.Boolean** | Pull the image before running |  [optional] |
| **servicePorts** | **kotlin.Boolean** | Map service ports to the host |  [optional] |
| **user** | **kotlin.String** | User to run as inside the container |  [optional] |
| **workdir** | **kotlin.String** | Working directory inside the container |  [optional] |



