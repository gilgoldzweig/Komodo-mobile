package ca.glong.komodo

import org.gradle.accessors.dm.LibrariesForLibs // This will be red in IDE until build, that's normal
import org.gradle.api.Project
import org.gradle.api.plugins.PluginManager
import org.gradle.api.provider.Provider
import org.gradle.plugin.use.PluginDependency

val Project.libs: LibrariesForLibs
    get() = (this as org.gradle.api.plugins.ExtensionAware)
        .extensions
        .getByName("libs") as LibrariesForLibs

internal fun PluginManager.alias(plugin: Provider<PluginDependency>) {
    apply(plugin.get().pluginId)
}