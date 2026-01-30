
# SystemStatsRecord

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **cpuPerc** | **kotlin.Float** | Cpu usage percentage |  |
| **diskTotalGb** | **kotlin.Double** | Total disk size in GB |  |
| **diskUsedGb** | **kotlin.Double** | Disk used in GB |  |
| **disks** | [**kotlin.collections.List&lt;SingleDiskUsage&gt;**](SingleDiskUsage.md) | Breakdown of individual disks, including their usage, total size, and mount point |  |
| **memTotalGb** | **kotlin.Double** | Total memory in GB |  |
| **memUsedGb** | **kotlin.Double** | Memory used in GB |  |
| **sid** | **kotlin.String** | Server id |  |
| **ts** | **kotlin.Long** | Unix timestamp in milliseconds |  |
| **loadAverage** | [**SystemLoadAverage**](SystemLoadAverage.md) | Load average (1m, 5m, 15m) |  [optional] |
| **networkEgressBytes** | **kotlin.Double** | Total network egress in bytes |  [optional] |
| **networkIngressBytes** | **kotlin.Double** | Total network ingress in bytes |  [optional] |



