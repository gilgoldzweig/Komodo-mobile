package ca.glong.komodo

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                alias(libs2.plugins.koin.compiler)
            }

            if (extensions.findByType<KotlinMultiplatformExtension>() == null) return
            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets {
                    commonMain.dependencies {
                        implementation(project.dependencies.platform(libs2.koin.bom))
                        implementation(libs2.bundles.koin)
                    }
                    androidMain.dependencies {
                        implementation(libs2.koin.android)
                    }
                    commonTest.dependencies {
                        implementation(project.dependencies.platform(libs2.koin.bom))
                        implementation(libs2.koin.test)
                    }
                }
            }
        }
    }
}
