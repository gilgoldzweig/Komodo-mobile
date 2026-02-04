package ca.glong.komodo

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val moduleName = project.name
            var koinEnabled = false
            with(pluginManager) {
                apply(Plugins.MULTIPLATFORM)
                apply(Plugins.MULTIPLATFORM_LIBRARY)
                apply(Plugins.SERIALIZATION)
                apply(Plugins.MOKKERY)
                if (hasPlugin("komodo.koin")) {
                    apply(Plugins.KOIN)
                    koinEnabled = true
                }
            }

            extensions.configure<KotlinMultiplatformExtension> {

//                androidTarget()
                iosArm64()
                iosSimulatorArm64()

                jvmToolchain(21)

                (this as ExtensionAware).extensions.configure<KotlinMultiplatformAndroidLibraryExtension> {
                    minSdk = versionInt(VersionNames.MIN_SDK)
                    compileSdk = versionInt(VersionNames.COMPILE_SDK)
                    namespace = "${Packages.KOMODO}.$moduleName"

                    withHostTest { }
                    withDeviceTest {
                        instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    }
                }

                sourceSets.apply {

                    commonMain.dependencies {
                        implLib("kotlinx-serialization-json")
                        implLib("kotlin-logging")

                        if (koinEnabled) {
                            bom("koin-bom")
                            bundle("koin")
                        }
                    }
                    commonTest.dependencies {
                        bundle("common-test")
                    }

                    maybeCreate("androidMain").apply {
                        dependencies {
                            if (koinEnabled) {
                                bundle("koin")
                                implLib("koin-android")
                                implementation("io.insert-koin:koin-annotations:2.3.2-Beta1")
                            }
                        }
                    }

                    val androidHostTest by getting
                    androidHostTest.dependencies {
                        bundle("common-test")
                    }
                }


                compilerOptions {
                    freeCompilerArgs.addAll("-Xskip-prerelease-check", "-Xexpect-actual-classes")
                }
            }
            dependencies {
                add("androidRuntimeClasspath", "org.jetbrains.compose.ui:ui-tooling:1.10.0")
            }
        }
    }
}
