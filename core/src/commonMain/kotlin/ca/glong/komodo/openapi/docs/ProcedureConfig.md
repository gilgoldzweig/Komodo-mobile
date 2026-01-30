
# ProcedureConfig

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **failureAlert** | **kotlin.Boolean** | Whether to send alerts when this procedure fails. |  [optional] |
| **schedule** | **kotlin.String** | Optionally provide a schedule for the procedure to run on.  There are 2 ways to specify a schedule:  1. Regular CRON expression:  (second, minute, hour, day, month, day-of-week) &#x60;&#x60;&#x60;text 0 0 0 1,15 * ? &#x60;&#x60;&#x60;  2. \&quot;English\&quot; expression via [english-to-cron](https://crates.io/crates/english-to-cron):  &#x60;&#x60;&#x60;text at midnight on the 1st and 15th of the month &#x60;&#x60;&#x60; |  [optional] |
| **scheduleAlert** | **kotlin.Boolean** | Whether to send alerts when the schedule was run. |  [optional] |
| **scheduleEnabled** | **kotlin.Boolean** | Whether schedule is enabled if one is provided. Can be used to temporarily disable the schedule. |  [optional] |
| **scheduleFormat** | [**ScheduleFormat**](ScheduleFormat.md) | Choose whether to specify schedule as regular CRON, or using the english to CRON parser. |  [optional] |
| **scheduleTimezone** | **kotlin.String** | Optional. A TZ Identifier. If not provided, will use Core local timezone. https://en.wikipedia.org/wiki/List_of_tz_database_time_zones. |  [optional] |
| **stages** | [**kotlin.collections.List&lt;ProcedureStage&gt;**](ProcedureStage.md) | The stages to be run by the procedure. |  [optional] |
| **webhookEnabled** | **kotlin.Boolean** | Whether incoming webhooks actually trigger action. |  [optional] |
| **webhookSecret** | **kotlin.String** | Optionally provide an alternate webhook secret for this procedure. If its an empty string, use the default secret from the config. |  [optional] |



