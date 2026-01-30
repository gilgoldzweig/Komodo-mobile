package ca.glong.komodo

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MetroConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.METRO)
            }

            if (extensions.findByType<KotlinMultiplatformExtension>() != null) {
                multiplatformDependencies(commonMain = {
                    implLib("metro-viewmodel")
                    implLib("metro-viewmodel-compose")
                })
            } else {
                dependencies {
                    implLib("metro-viewmodel")
                    implLib("metro-viewmodel-compose")
                }
            }
        }
    }
}
