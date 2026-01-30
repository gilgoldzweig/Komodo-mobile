
# AwsBuilderConfig

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **amiId** | **kotlin.String** | The EC2 ami id to create. The ami should have the periphery client configured to start on startup, and should have the necessary github / dockerhub accounts configured. |  [optional] |
| **assignPublicIp** | **kotlin.Boolean** | Whether to assign the instance a public IP address. Likely needed for the instance to be able to reach the open internet. |  [optional] |
| **dockerRegistries** | [**kotlin.collections.List&lt;DockerRegistry&gt;**](DockerRegistry.md) | Which docker registries are available on the AMI. |  [optional] |
| **gitProviders** | [**kotlin.collections.List&lt;GitProvider&gt;**](GitProvider.md) | Which git providers are available on the AMI |  [optional] |
| **insecureTls** | **kotlin.Boolean** | Whether to validate the Periphery tls certificates. |  [optional] |
| **instanceType** | **kotlin.String** | The instance type to create for the build |  [optional] |
| **keyPairName** | **kotlin.String** | The key pair name to attach to the instance |  [optional] |
| **peripheryPublicKey** | **kotlin.String** | An expected public key associated with Periphery private key. If empty, doesn&#39;t validate Periphery public key. |  [optional] |
| **port** | **kotlin.Int** | The port periphery will be running on. Default: &#x60;8120&#x60; |  [optional] |
| **region** | **kotlin.String** | The AWS region to create the instance in |  [optional] |
| **secrets** | **kotlin.collections.List&lt;kotlin.String&gt;** | Which secrets are available on the AMI. |  [optional] |
| **securityGroupIds** | **kotlin.collections.List&lt;kotlin.String&gt;** | The security group ids to attach to the instance. This should include a security group to allow core inbound access to the periphery port. |  [optional] |
| **subnetId** | **kotlin.String** | The subnet id to create the instance in. |  [optional] |
| **useHttps** | **kotlin.Boolean** |  |  [optional] |
| **usePublicIp** | **kotlin.Boolean** | Whether core should use the public IP address to communicate with periphery on the builder. If false, core will communicate with the instance using the private IP. |  [optional] |
| **userData** | **kotlin.String** | The user data to deploy the instance with. |  [optional] |
| **volumeGb** | **kotlin.Int** | The size of the builder volume in gb |  [optional] |



