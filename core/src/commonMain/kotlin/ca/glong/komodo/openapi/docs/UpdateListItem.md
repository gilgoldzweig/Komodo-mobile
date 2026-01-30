
# UpdateListItem

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | **kotlin.String** | The id of the update |  |
| **operation** | [**Operation**](Operation.md) | Which operation was run |  |
| **&#x60;operator&#x60;** | **kotlin.String** | The user id that triggered the update.  Also can take these values for operations triggered automatically: - &#x60;Procedure&#x60;: The operation was triggered as part of a procedure run - &#x60;Github&#x60;: The operation was triggered by a github webhook - &#x60;Auto Redeploy&#x60;: The operation (always &#x60;Deploy&#x60;) was triggered by an attached build finishing. |  |
| **startTs** | **kotlin.Long** | The starting time of the operation |  |
| **status** | [**UpdateStatus**](UpdateStatus.md) | The status of the update - &#x60;Queued&#x60; - &#x60;InProgress&#x60; - &#x60;Complete&#x60; |  |
| **success** | **kotlin.Boolean** | Whether the operation was successful |  |
| **target** | [**ResourceTarget**](ResourceTarget.md) | The target resource to which this update refers |  |
| **username** | **kotlin.String** | The username of the user performing update |  |
| **otherData** | **kotlin.String** | Some unstructured, operation specific data. Not for general usage. |  [optional] |
| **version** | [**Version**](Version.md) | An optional version on the update, ie build version or deployed version. |  [optional] |



