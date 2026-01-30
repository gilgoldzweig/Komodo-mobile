package ca.glong.komodo

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.KOIN)
            }

            if (extensions.findByType<KotlinMultiplatformExtension>() != null) {
                multiplatformDependencies(commonMain = {
                    bom("koin-bom")
                    bundle("koin")
                })
            } else {
                dependencies {
                    bom("koin-bom")
                    bundle("koin")
                }
            }
        }
    }
}
