
# DeployStack

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **stack** | **kotlin.String** | Id or name |  |
| **services** | **kotlin.collections.List&lt;kotlin.String&gt;** | Filter to only deploy specific services. If empty, will deploy all services.  Note. For Swarm mode Stacks, this field is not supported and will be ignored. |  [optional] |
| **stopTime** | **kotlin.Int** | Override the default termination max time. Only used if the stack needs to be taken down first. |  [optional] |



