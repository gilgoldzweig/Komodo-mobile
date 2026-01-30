
# SystemStats

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **cpuPerc** | **kotlin.Float** | Cpu usage percentage |  |
| **disks** | [**kotlin.collections.List&lt;SingleDiskUsage&gt;**](SingleDiskUsage.md) | Breakdown of individual disks, ie their usages, sizes, and mount points |  |
| **memTotalGb** | **kotlin.Double** | Total memory in GB |  |
| **memUsedGb** | **kotlin.Double** | Used memory in GB. &#39;Total&#39; - &#39;Available&#39; (not free) memory. |  |
| **pollingRate** | [**Timelength**](Timelength.md) | The rate the system stats are being polled from the system |  |
| **refreshListTs** | **kotlin.Long** | Unix timestamp in milliseconds when disk list was last refreshed |  |
| **refreshTs** | **kotlin.Long** | Unix timestamp in milliseconds when stats were last polled |  |
| **loadAverage** | [**SystemLoadAverage**](SystemLoadAverage.md) | Load average (1m, 5m, 15m) |  [optional] |
| **memFreeGb** | **kotlin.Double** | [1.15.9+] Free memory in GB. This is really the &#39;Free&#39; memory, not the &#39;Available&#39; memory. It may be different than mem_total_gb - mem_used_gb. |  [optional] |
| **networkEgressBytes** | **kotlin.Double** | Network egress usage in MB |  [optional] |
| **networkIngressBytes** | **kotlin.Double** | Network ingress usage in MB |  [optional] |



