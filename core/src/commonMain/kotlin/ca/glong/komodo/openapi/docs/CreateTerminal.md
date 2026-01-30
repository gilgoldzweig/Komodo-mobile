
# CreateTerminal

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **name** | **kotlin.String** | A name for the Terminal session. |  |
| **target** | [**TerminalTarget**](TerminalTarget.md) | The target to create terminal for |  |
| **command** | **kotlin.String** | The shell command (eg &#x60;bash&#x60;) to init the shell.  Default:  - Server: Configured on each Periphery  - ContainerExec: &#x60;sh&#x60;  - Attach: unused |  [optional] |
| **mode** | [**ContainerTerminalMode**](ContainerTerminalMode.md) | For container terminals, choose &#39;exec&#39; or &#39;attach&#39;.  Default  - Server: ignored  - Container / Stack / Deployment: &#x60;exec&#x60; |  [optional] |
| **recreate** | [**TerminalRecreateMode**](TerminalRecreateMode.md) | Default: &#x60;Never&#x60; |  [optional] |



