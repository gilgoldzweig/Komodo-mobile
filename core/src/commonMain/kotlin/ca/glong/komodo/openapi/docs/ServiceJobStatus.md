
# ServiceJobStatus

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **jobIteration** | [**ObjectVersion**](ObjectVersion.md) | JobIteration is a value increased each time a Job is executed, successfully or otherwise. \\\&quot;Executed\\\&quot;, in this case, means the job as a whole has been started, not that an individual Task has been launched. A job is \\\&quot;Executed\\\&quot; when its ServiceSpec is updated. JobIteration can be used to disambiguate Tasks belonging to different executions of a job.  Though JobIteration will increase with each subsequent execution, it may not necessarily increase by 1, and so JobIteration should not be used to |  [optional] |
| **lastExecution** | **kotlin.String** | The last time, as observed by the server, that this job was started. |  [optional] |



