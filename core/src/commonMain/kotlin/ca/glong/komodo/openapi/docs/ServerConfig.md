
# ServerConfig

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **address** | **kotlin.String** | The ws/s address of the periphery client. If unset, Server expects Periphery -&gt; Core connection. |  [optional] |
| **autoPrune** | **kotlin.Boolean** | Whether to trigger &#39;docker image prune -a -f&#39; every 24 hours. default: true |  [optional] |
| **autoRotateKeys** | **kotlin.Boolean** | Whether to automatically rotate Server keys when RotateAllServerKeys is called. Default: true |  [optional] |
| **cpuCritical** | **kotlin.Float** | The percentage threshhold which triggers CRITICAL state for CPU. |  [optional] |
| **cpuWarning** | **kotlin.Float** | The percentage threshhold which triggers WARNING state for CPU. |  [optional] |
| **diskCritical** | **kotlin.Double** | The percentage threshhold which triggers CRITICAL state for DISK. |  [optional] |
| **diskWarning** | **kotlin.Double** | The percentage threshhold which triggers WARNING state for DISK. |  [optional] |
| **enabled** | **kotlin.Boolean** | Whether a server is enabled. If a server is disabled, you won&#39;t be able to perform any actions on it or see deployment&#39;s status. Default: false |  [optional] |
| **externalAddress** | **kotlin.String** | The address to use with links for containers on the server. If empty, will use the &#39;address&#39; for links. |  [optional] |
| **ignoreMounts** | **kotlin.collections.List&lt;kotlin.String&gt;** | Sometimes the system stats reports a mount path that is not desired. Use this field to filter it out from the report. |  [optional] |
| **insecureTls** | **kotlin.Boolean** | Only relevant for Core -&gt; Periphery connections. Whether to skip Periphery tls certificate validation. This defaults to true because Periphery generates self-signed certificates by default, but if you use valid certs you can switch this to false. |  [optional] |
| **links** | **kotlin.collections.List&lt;kotlin.String&gt;** | Configure quick links that are displayed in the resource header |  [optional] |
| **maintenanceWindows** | [**kotlin.collections.List&lt;MaintenanceWindow&gt;**](MaintenanceWindow.md) | Scheduled maintenance windows during which alerts will be suppressed. |  [optional] |
| **memCritical** | **kotlin.Double** | The percentage threshhold which triggers CRITICAL state for MEM. |  [optional] |
| **memWarning** | **kotlin.Double** | The percentage threshhold which triggers WARNING state for MEM. |  [optional] |
| **passkey** | **kotlin.String** | Deprecated. Use private / public keys instead. An optional override passkey to use to authenticate with periphery agent. If this is empty, will use passkey in core config. |  [optional] |
| **region** | **kotlin.String** | An optional region label |  [optional] |
| **sendCpuAlerts** | **kotlin.Boolean** | Whether to send alerts about the servers CPU status |  [optional] |
| **sendDiskAlerts** | **kotlin.Boolean** | Whether to send alerts about the servers DISK status |  [optional] |
| **sendMemAlerts** | **kotlin.Boolean** | Whether to send alerts about the servers MEM status |  [optional] |
| **sendUnreachableAlerts** | **kotlin.Boolean** | Whether to send alerts about the servers reachability |  [optional] |
| **sendVersionMismatchAlerts** | **kotlin.Boolean** | Whether to send alerts about the servers version mismatch with core |  [optional] |
| **statsMonitoring** | **kotlin.Boolean** | Whether to monitor any server stats beyond passing health check. default: true |  [optional] |



