
# Operation

## Enum


    * `NONE` (value: `"None"`)

    * `CREATE_SWARM` (value: `"CreateSwarm"`)

    * `UPDATE_SWARM` (value: `"UpdateSwarm"`)

    * `RENAME_SWARM` (value: `"RenameSwarm"`)

    * `DELETE_SWARM` (value: `"DeleteSwarm"`)

    * `REMOVE_SWARM_NODES` (value: `"RemoveSwarmNodes"`)

    * `REMOVE_SWARM_STACKS` (value: `"RemoveSwarmStacks"`)

    * `REMOVE_SWARM_SERVICES` (value: `"RemoveSwarmServices"`)

    * `CREATE_SWARM_CONFIG` (value: `"CreateSwarmConfig"`)

    * `ROTATE_SWARM_CONFIG` (value: `"RotateSwarmConfig"`)

    * `REMOVE_SWARM_CONFIGS` (value: `"RemoveSwarmConfigs"`)

    * `CREATE_SWARM_SECRET` (value: `"CreateSwarmSecret"`)

    * `ROTATE_SWARM_SECRET` (value: `"RotateSwarmSecret"`)

    * `REMOVE_SWARM_SECRETS` (value: `"RemoveSwarmSecrets"`)

    * `CREATE_SERVER` (value: `"CreateServer"`)

    * `UPDATE_SERVER` (value: `"UpdateServer"`)

    * `UPDATE_SERVER_KEY` (value: `"UpdateServerKey"`)

    * `DELETE_SERVER` (value: `"DeleteServer"`)

    * `RENAME_SERVER` (value: `"RenameServer"`)

    * `START_CONTAINER` (value: `"StartContainer"`)

    * `RESTART_CONTAINER` (value: `"RestartContainer"`)

    * `PAUSE_CONTAINER` (value: `"PauseContainer"`)

    * `UNPAUSE_CONTAINER` (value: `"UnpauseContainer"`)

    * `STOP_CONTAINER` (value: `"StopContainer"`)

    * `DESTROY_CONTAINER` (value: `"DestroyContainer"`)

    * `START_ALL_CONTAINERS` (value: `"StartAllContainers"`)

    * `RESTART_ALL_CONTAINERS` (value: `"RestartAllContainers"`)

    * `PAUSE_ALL_CONTAINERS` (value: `"PauseAllContainers"`)

    * `UNPAUSE_ALL_CONTAINERS` (value: `"UnpauseAllContainers"`)

    * `STOP_ALL_CONTAINERS` (value: `"StopAllContainers"`)

    * `PRUNE_CONTAINERS` (value: `"PruneContainers"`)

    * `CREATE_NETWORK` (value: `"CreateNetwork"`)

    * `DELETE_NETWORK` (value: `"DeleteNetwork"`)

    * `PRUNE_NETWORKS` (value: `"PruneNetworks"`)

    * `DELETE_IMAGE` (value: `"DeleteImage"`)

    * `PRUNE_IMAGES` (value: `"PruneImages"`)

    * `DELETE_VOLUME` (value: `"DeleteVolume"`)

    * `PRUNE_VOLUMES` (value: `"PruneVolumes"`)

    * `PRUNE_DOCKER_BUILDERS` (value: `"PruneDockerBuilders"`)

    * `PRUNE_BUILDX` (value: `"PruneBuildx"`)

    * `PRUNE_SYSTEM` (value: `"PruneSystem"`)

    * `CREATE_STACK` (value: `"CreateStack"`)

    * `UPDATE_STACK` (value: `"UpdateStack"`)

    * `RENAME_STACK` (value: `"RenameStack"`)

    * `DELETE_STACK` (value: `"DeleteStack"`)

    * `WRITE_STACK_CONTENTS` (value: `"WriteStackContents"`)

    * `REFRESH_STACK_CACHE` (value: `"RefreshStackCache"`)

    * `PULL_STACK` (value: `"PullStack"`)

    * `DEPLOY_STACK` (value: `"DeployStack"`)

    * `START_STACK` (value: `"StartStack"`)

    * `RESTART_STACK` (value: `"RestartStack"`)

    * `PAUSE_STACK` (value: `"PauseStack"`)

    * `UNPAUSE_STACK` (value: `"UnpauseStack"`)

    * `STOP_STACK` (value: `"StopStack"`)

    * `DESTROY_STACK` (value: `"DestroyStack"`)

    * `RUN_STACK_SERVICE` (value: `"RunStackService"`)

    * `CHECK_STACK_FOR_UPDATE` (value: `"CheckStackForUpdate"`)

    * `DEPLOY_STACK_SERVICE` (value: `"DeployStackService"`)

    * `PULL_STACK_SERVICE` (value: `"PullStackService"`)

    * `START_STACK_SERVICE` (value: `"StartStackService"`)

    * `RESTART_STACK_SERVICE` (value: `"RestartStackService"`)

    * `PAUSE_STACK_SERVICE` (value: `"PauseStackService"`)

    * `UNPAUSE_STACK_SERVICE` (value: `"UnpauseStackService"`)

    * `STOP_STACK_SERVICE` (value: `"StopStackService"`)

    * `DESTROY_STACK_SERVICE` (value: `"DestroyStackService"`)

    * `CREATE_DEPLOYMENT` (value: `"CreateDeployment"`)

    * `UPDATE_DEPLOYMENT` (value: `"UpdateDeployment"`)

    * `RENAME_DEPLOYMENT` (value: `"RenameDeployment"`)

    * `DELETE_DEPLOYMENT` (value: `"DeleteDeployment"`)

    * `DEPLOY` (value: `"Deploy"`)

    * `PULL_DEPLOYMENT` (value: `"PullDeployment"`)

    * `START_DEPLOYMENT` (value: `"StartDeployment"`)

    * `RESTART_DEPLOYMENT` (value: `"RestartDeployment"`)

    * `PAUSE_DEPLOYMENT` (value: `"PauseDeployment"`)

    * `UNPAUSE_DEPLOYMENT` (value: `"UnpauseDeployment"`)

    * `STOP_DEPLOYMENT` (value: `"StopDeployment"`)

    * `DESTROY_DEPLOYMENT` (value: `"DestroyDeployment"`)

    * `CHECK_DEPLOYMENT_FOR_UPDATE` (value: `"CheckDeploymentForUpdate"`)

    * `CREATE_BUILD` (value: `"CreateBuild"`)

    * `UPDATE_BUILD` (value: `"UpdateBuild"`)

    * `RENAME_BUILD` (value: `"RenameBuild"`)

    * `DELETE_BUILD` (value: `"DeleteBuild"`)

    * `RUN_BUILD` (value: `"RunBuild"`)

    * `CANCEL_BUILD` (value: `"CancelBuild"`)

    * `WRITE_DOCKERFILE` (value: `"WriteDockerfile"`)

    * `CREATE_REPO` (value: `"CreateRepo"`)

    * `UPDATE_REPO` (value: `"UpdateRepo"`)

    * `RENAME_REPO` (value: `"RenameRepo"`)

    * `DELETE_REPO` (value: `"DeleteRepo"`)

    * `CLONE_REPO` (value: `"CloneRepo"`)

    * `PULL_REPO` (value: `"PullRepo"`)

    * `BUILD_REPO` (value: `"BuildRepo"`)

    * `CANCEL_REPO_BUILD` (value: `"CancelRepoBuild"`)

    * `CREATE_PROCEDURE` (value: `"CreateProcedure"`)

    * `UPDATE_PROCEDURE` (value: `"UpdateProcedure"`)

    * `RENAME_PROCEDURE` (value: `"RenameProcedure"`)

    * `DELETE_PROCEDURE` (value: `"DeleteProcedure"`)

    * `RUN_PROCEDURE` (value: `"RunProcedure"`)

    * `CREATE_ACTION` (value: `"CreateAction"`)

    * `UPDATE_ACTION` (value: `"UpdateAction"`)

    * `RENAME_ACTION` (value: `"RenameAction"`)

    * `DELETE_ACTION` (value: `"DeleteAction"`)

    * `RUN_ACTION` (value: `"RunAction"`)

    * `CREATE_RESOURCE_SYNC` (value: `"CreateResourceSync"`)

    * `UPDATE_RESOURCE_SYNC` (value: `"UpdateResourceSync"`)

    * `RENAME_RESOURCE_SYNC` (value: `"RenameResourceSync"`)

    * `DELETE_RESOURCE_SYNC` (value: `"DeleteResourceSync"`)

    * `WRITE_SYNC_CONTENTS` (value: `"WriteSyncContents"`)

    * `COMMIT_SYNC` (value: `"CommitSync"`)

    * `RUN_SYNC` (value: `"RunSync"`)

    * `CREATE_BUILDER` (value: `"CreateBuilder"`)

    * `UPDATE_BUILDER` (value: `"UpdateBuilder"`)

    * `RENAME_BUILDER` (value: `"RenameBuilder"`)

    * `DELETE_BUILDER` (value: `"DeleteBuilder"`)

    * `CREATE_ALERTER` (value: `"CreateAlerter"`)

    * `UPDATE_ALERTER` (value: `"UpdateAlerter"`)

    * `RENAME_ALERTER` (value: `"RenameAlerter"`)

    * `DELETE_ALERTER` (value: `"DeleteAlerter"`)

    * `TEST_ALERTER` (value: `"TestAlerter"`)

    * `SEND_ALERT` (value: `"SendAlert"`)

    * `CLEAR_REPO_CACHE` (value: `"ClearRepoCache"`)

    * `BACKUP_CORE_DATABASE` (value: `"BackupCoreDatabase"`)

    * `GLOBAL_AUTO_UPDATE` (value: `"GlobalAutoUpdate"`)

    * `ROTATE_ALL_SERVER_KEYS` (value: `"RotateAllServerKeys"`)

    * `ROTATE_CORE_KEYS` (value: `"RotateCoreKeys"`)

    * `CREATE_VARIABLE` (value: `"CreateVariable"`)

    * `UPDATE_VARIABLE_VALUE` (value: `"UpdateVariableValue"`)

    * `DELETE_VARIABLE` (value: `"DeleteVariable"`)

    * `CREATE_GIT_PROVIDER_ACCOUNT` (value: `"CreateGitProviderAccount"`)

    * `UPDATE_GIT_PROVIDER_ACCOUNT` (value: `"UpdateGitProviderAccount"`)

    * `DELETE_GIT_PROVIDER_ACCOUNT` (value: `"DeleteGitProviderAccount"`)

    * `CREATE_DOCKER_REGISTRY_ACCOUNT` (value: `"CreateDockerRegistryAccount"`)

    * `UPDATE_DOCKER_REGISTRY_ACCOUNT` (value: `"UpdateDockerRegistryAccount"`)

    * `DELETE_DOCKER_REGISTRY_ACCOUNT` (value: `"DeleteDockerRegistryAccount"`)



