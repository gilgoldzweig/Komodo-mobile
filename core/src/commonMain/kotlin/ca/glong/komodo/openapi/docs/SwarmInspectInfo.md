
# SwarmInspectInfo

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **createdAt** | **kotlin.String** | Date and time at which the swarm was initialised in [RFC 3339](https://www.ietf.org/rfc/rfc3339.txt) format with nano-seconds. |  [optional] |
| **dataPathPort** | **kotlin.Int** | DataPathPort specifies the data path port number for data traffic. Acceptable port range is 1024 to 49151. If no port is set or is set to 0, the default port (4789) is used. |  [optional] |
| **defaultAddrPool** | **kotlin.collections.List&lt;kotlin.String&gt;** | Default Address Pool specifies default subnet pools for global scope networks. |  [optional] |
| **ID** | **kotlin.String** | The (Docker) ID of the swarm. |  [optional] |
| **joinTokens** | [**JoinTokens**](JoinTokens.md) |  |  [optional] |
| **rootRotationInProgress** | **kotlin.Boolean** | Whether there is currently a root CA rotation in progress for the swarm |  [optional] |
| **spec** | [**SwarmSpec**](SwarmSpec.md) |  |  [optional] |
| **subnetSize** | **kotlin.Int** | SubnetSize specifies the subnet size of the networks created from the default subnet pool. |  [optional] |
| **tlSInfo** | [**TlsInfo**](TlsInfo.md) |  |  [optional] |
| **updatedAt** | **kotlin.String** | Date and time at which the swarm was last updated in [RFC 3339](https://www.ietf.org/rfc/rfc3339.txt) format with nano-seconds. |  [optional] |
| **version** | [**ObjectVersion**](ObjectVersion.md) |  |  [optional] |



