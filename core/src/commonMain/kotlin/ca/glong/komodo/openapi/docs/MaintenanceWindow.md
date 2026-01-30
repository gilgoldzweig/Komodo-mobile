
# MaintenanceWindow

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **durationMinutes** | **kotlin.Int** | Duration of the maintenance window in minutes (required) |  |
| **name** | **kotlin.String** | Name for the maintenance window (required) |  |
| **date** | **kotlin.String** | For OneTime window: ISO 8601 date format (YYYY-MM-DD) |  [optional] |
| **dayOfWeek** | **kotlin.String** | For Weekly schedules: Specify the day of the week (Monday, Tuesday, etc.) |  [optional] |
| **description** | **kotlin.String** | Description of what maintenance is performed (optional) |  [optional] |
| **enabled** | **kotlin.Boolean** | Whether this maintenance window is currently enabled |  [optional] |
| **hour** | **kotlin.Int** | Start hour in 24-hour format (0-23) (optional, defaults to 0) |  [optional] |
| **minute** | **kotlin.Int** | Start minute (0-59) (optional, defaults to 0) |  [optional] |
| **scheduleType** | [**MaintenanceScheduleType**](MaintenanceScheduleType.md) | The type of maintenance schedule:   - Daily (default)   - Weekly   - OneTime |  [optional] |
| **timezone** | **kotlin.String** | Timezone for maintenance window specificiation. If empty, will use Core timezone. |  [optional] |



