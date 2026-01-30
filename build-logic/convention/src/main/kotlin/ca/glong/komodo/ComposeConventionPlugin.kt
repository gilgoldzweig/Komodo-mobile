package ca.glong.komodo

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.COMPOSE)
                apply(Plugins.COMPOSE_COMPILER)
                apply(Plugins.COMPOSE_HOT_RELOAD)
            }

            if (extensions.findByType<KotlinMultiplatformExtension>() != null) {
                multiplatformDependencies {
                    bundle("compose")
                }
            } else {
                dependencies {
                    bundle("compose")
                }
            }
        }
    }
}
