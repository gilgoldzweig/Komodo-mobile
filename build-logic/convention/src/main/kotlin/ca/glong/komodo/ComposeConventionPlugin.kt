package ca.glong.komodo

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                alias(libs2.plugins.composeCompiler)
                alias(libs2.plugins.composeCompiler)
                alias(libs2.plugins.composeHotReload)
            }

            if (extensions.findByType<KotlinMultiplatformExtension>() == null) return
            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets {
                    commonMain.dependencies {
                        implementation(libs2.bundles.compose)
//                        compileOnly(libs2.compose.uiTooling)
                        implementation(libs2.compose.uiToolingPreview)
                    }
                }
            }
        }
    }
}
