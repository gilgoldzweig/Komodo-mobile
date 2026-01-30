package ca.glong.komodo

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.MULTIPLATFORM)
                apply(Plugins.SERIALIZATION)
                apply(Plugins.MOKKERY)
            }

            extensions.configure<KotlinMultiplatformExtension> {
                applyDefaultHierarchyTemplate()

                iosArm64()
                iosSimulatorArm64()

                sourceSets.apply {
                    commonMain.dependencies {
                        implLib("kotlinx-serialization-json")
                        implLib("kotlin-logging")
                    }
                    commonTest.dependencies {
                        bundle("common-test")
                    }
                }
                
                compilerOptions {
                    freeCompilerArgs.add("-Xskip-prerelease-check")
                }
            }
        }
    }
}
