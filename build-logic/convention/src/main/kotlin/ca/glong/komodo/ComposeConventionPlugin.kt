package ca.glong.komodo

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.compose.ComposeExtension

class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            multiplatformDependencies {
                bom("androidx-compose-bom")
                bundle("compose")
            }
            extensions.configure<ComposeExtension> {
                // Any specific compose configuration if needed
            }
        }
    }
}
