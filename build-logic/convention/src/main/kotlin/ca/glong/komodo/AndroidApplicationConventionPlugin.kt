package ca.glong.komodo

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.ANDROID_APPLICATION)
            }

            extensions.configure<ApplicationExtension> {
                compileSdk = versionInt(VersionNames.COMPILE_SDK)
                defaultConfig {
                    minSdk = versionInt(VersionNames.MIN_SDK)
                    targetSdk = versionInt("android-targetSdk")
                }
                
                compileOptions {
                    sourceCompatibility = org.gradle.api.JavaVersion.VERSION_21
                    targetCompatibility = org.gradle.api.JavaVersion.VERSION_21
                }
            }
        }
    }
}
