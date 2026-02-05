package ca.glong.komodo

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            with(pluginManager) {
                alias(libs.plugins.kotlinMultiplatform)
                alias(libs.plugins.kotlinSerialization)
                alias(libs.plugins.mokkery)
            }

            extensions.configure<KotlinMultiplatformExtension> {
                applyDefaultHierarchyTemplate()
                iosArm64()
                iosSimulatorArm64()

                jvmToolchain(21)

                sourceSets.apply {
                    commonMain.dependencies {
                        implementation(libs.kotlinx.serialization.json)
                        implementation(libs.kotlin.logging)
                    }
                    commonTest.dependencies {
                        implementation(libs.bundles.common.test)
                    }
                }

                compilerOptions {
                    freeCompilerArgs.addAll("-Xskip-prerelease-check", "-Xexpect-actual-classes")
                }
            }
        }
    }
}