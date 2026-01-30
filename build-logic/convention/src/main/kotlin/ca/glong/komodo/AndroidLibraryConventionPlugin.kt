package ca.glong.komodo

import com.android.build.api.variant.KotlinMultiplatformAndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.MULTIPLATFORM_LIBRARY)
            }

            extensions.configure<KotlinMultiplatformAndroidComponentsExtension> {
                finalizeDsl {
                    it.minSdk = versionInt(VersionNames.MIN_SDK)
                    it.compileSdk = versionInt(VersionNames.COMPILE_SDK)

                    val moduleName = path.split(":").drop(2).joinToString(".")
                    val name = moduleName.ifEmpty { "app" }
                    it.namespace = "${Packages.KOMODO}.$name"

                }
            }
        }
    }
}
