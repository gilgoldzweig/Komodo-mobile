
# ActionConfig

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **arguments** | **kotlin.String** | Default arguments to give to the Action for use in the script at &#x60;ARGS&#x60;. |  [optional] |
| **argumentsFormat** | [**FileFormat**](FileFormat.md) | Specify the format in which the arguments are defined. Default: &#x60;key_value&#x60; (like environment) |  [optional] |
| **failureAlert** | **kotlin.Boolean** | Whether to send alerts when this action fails. |  [optional] |
| **fileContents** | **kotlin.String** | Typescript file contents using pre-initialized &#x60;komodo&#x60; client. Supports variable / secret interpolation. |  [optional] |
| **reloadDenoDeps** | **kotlin.Boolean** | Whether deno will be instructed to reload all dependencies, this can usually be kept false outside of development. |  [optional] |
| **runAtStartup** | **kotlin.Boolean** | Whether this action should run at startup. |  [optional] |
| **schedule** | **kotlin.String** | Optionally provide a schedule for the procedure to run on.  There are 2 ways to specify a schedule:  1. Regular CRON expression:  (second, minute, hour, day, month, day-of-week) &#x60;&#x60;&#x60;text 0 0 0 1,15 * ? &#x60;&#x60;&#x60;  2. \&quot;English\&quot; expression via [english-to-cron](https://crates.io/crates/english-to-cron):  &#x60;&#x60;&#x60;text at midnight on the 1st and 15th of the month &#x60;&#x60;&#x60; |  [optional] |
| **scheduleAlert** | **kotlin.Boolean** | Whether to send alerts when the schedule was run. |  [optional] |
| **scheduleEnabled** | **kotlin.Boolean** | Whether schedule is enabled if one is provided. Can be used to temporarily disable the schedule. |  [optional] |
| **scheduleFormat** | [**ScheduleFormat**](ScheduleFormat.md) | Choose whether to specify schedule as regular CRON, or using the english to CRON parser. |  [optional] |
| **scheduleTimezone** | **kotlin.String** | Optional. A TZ Identifier. If not provided, will use Core local timezone. https://en.wikipedia.org/wiki/List_of_tz_database_time_zones. |  [optional] |
| **webhookEnabled** | **kotlin.Boolean** | Whether incoming webhooks actually trigger action. |  [optional] |
| **webhookSecret** | **kotlin.String** | Optionally provide an alternate webhook secret for this procedure. If its an empty string, use the default secret from the config. |  [optional] |



