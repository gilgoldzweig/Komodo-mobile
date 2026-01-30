
# Deploy

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **deployment** | **kotlin.String** | Name or id |  |
| **stopSignal** | [**TerminationSignal**](TerminationSignal.md) | Override the default termination signal specified in the deployment. Only used when deployment needs to be taken down before redeploy. |  [optional] |
| **stopTime** | **kotlin.Int** | Override the default termination max time. Only used when deployment needs to be taken down before redeploy. |  [optional] |



