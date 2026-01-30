package ca.glong.komodo

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
//                apply("com.google.devtools.ksp")
                apply("io.insert-koin.compiler.plugin")
            }

            multiplatformDependencies(commonMain = {
                bom("koin-bom")
                bundle("koin")
            })
        }
    }
}
