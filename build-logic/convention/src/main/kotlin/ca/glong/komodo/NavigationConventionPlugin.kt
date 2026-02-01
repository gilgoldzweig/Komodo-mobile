package ca.glong.komodo

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import kotlin.jvm.optionals.getOrNull

class NavigationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            if (extensions.findByType<KotlinMultiplatformExtension>() != null) {
                multiplatformDependencies(commonMain = {
                    bundle("nav3")
                })
            } else {
                dependencies {
                    bundle("nav3")
                }
            }
        }
    }
}
