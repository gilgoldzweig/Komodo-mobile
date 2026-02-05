package ca.glong.komodo

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val moduleName = project.displayName
            with(pluginManager) {
                apply(Plugins.MULTIPLATFORM_LIBRARY)
            }
            extensions.configure<KotlinMultiplatformExtension> {
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
                    val androidHostTest by getting
                    androidHostTest.dependencies {
                        bundle("common-test")
                    }
                }
            }

            dependencies {
                add("androidRuntimeClasspath", "org.jetbrains.compose.ui:ui-tooling:1.10.0")
            }
        }
    }
}
