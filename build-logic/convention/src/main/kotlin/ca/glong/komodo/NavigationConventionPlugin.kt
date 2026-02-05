package ca.glong.komodo

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class NavigationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            if (extensions.findByType<KotlinMultiplatformExtension>() == null) return
            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets {
                    commonMain.dependencies {
                        implementation(libs.bundles.nav3)
                    }
                }
            }
        }
    }
}
