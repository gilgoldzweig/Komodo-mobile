
# ExecutionOneOf74

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **params** | **kotlin.String** | **Admin only.** Backs up the Komodo Core database to compressed jsonl files. Response: [Update]. Aliases: &#x60;backup-database&#x60;, &#x60;backup-db&#x60;, &#x60;backup&#x60;.  Mount a folder to &#x60;/backups&#x60;, and Core will use it to create timestamped database dumps, which can be restored using the Komodo CLI.  https://komo.do/docs/setup/backup |  |
| **type** | [**inline**](#Type) |  |  |


<a id="Type"></a>
## Enum: type
| Name | Value |
| ---- | ----- |
| type | BackupCoreDatabase |



