package ca.glong.komodo

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.ANDROID_APPLICATION)
            }

            extensions.configure<ApplicationExtension> {
                namespace = "${Packages.KOMODO}.android.app"

                compileSdk = versionInt(VersionNames.COMPILE_SDK)
                defaultConfig {
                    minSdk = versionInt(VersionNames.MIN_SDK)
                    targetSdk = versionInt(VersionNames.TARGET_SDK)
                }
                
                compileOptions {
                    sourceCompatibility = org.gradle.api.JavaVersion.VERSION_21
                    targetCompatibility = org.gradle.api.JavaVersion.VERSION_21
                }
            }
            tasks.withType<KotlinCompile> {
                compilerOptions {
                    freeCompilerArgs.add("-Xskip-prerelease-check")
                }
            }
        }
    }
}
