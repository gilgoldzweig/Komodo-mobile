
# Variable

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **name** | **kotlin.String** | Unique name associated with the variable. Instances of &#39;[[variable.name]]&#39; in value will be replaced with &#39;variable.value&#39;. |  |
| **description** | **kotlin.String** | A description for the variable. |  [optional] |
| **isSecret** | **kotlin.Boolean** | If marked as secret, the variable value will be hidden in updates / logs. Additionally the value will not be served in read requests by non admin users.  Note that the value is NOT encrypted in the database, and will likely show up in database logs. The security of these variables comes down to the security of the database (system level encryption, network isolation, etc.) |  [optional] |
| **&#x60;value&#x60;** | **kotlin.String** | The value associated with the variable. |  [optional] |



