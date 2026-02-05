package ca.glong.komodo

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import javax.inject.Inject

class KotlinMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val extension = project.extensions.create("komodo", KomodoPluginExtension::class.java)

            with(pluginManager) {
                apply(Plugins.MULTIPLATFORM)
                apply(Plugins.SERIALIZATION)
                apply(Plugins.MOKKERY)
            }

            extensions.configure<KotlinMultiplatformExtension> {
                applyDefaultHierarchyTemplate()
                iosArm64()
                iosSimulatorArm64()

                jvmToolchain(21)

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
                    freeCompilerArgs.addAll("-Xskip-prerelease-check", "-Xexpect-actual-classes")
                }
            }
        }
    }
}

abstract class KomodoPluginExtension @Inject constructor(objects: ObjectFactory) {
    val iosEnabled = objects.property(Boolean::class.java).convention(true)
    val androidEnabled = objects.property(Boolean::class.java).convention(true)

//    val enabledTargets = objects.listProperty(CompatibleExtensions::class.java)
//        .convention(
//            listOf(
//                CompatibleExtensions.ANDROID,
//                CompatibleExtensions.IOS,
//            )
//        )

    val koinEnabled = objects.property(Boolean::class.java).convention(true)
    val composeEnabled = objects.property(Boolean::class.java).convention(true)
    val navigationEnabled = objects.property(Boolean::class.java).convention(true)
}

// enum class CompatibleExtensions {
//    ANDROID,
//    IOS,
// }