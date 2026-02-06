package ca.glong.komodo

import co.touchlab.skie.plugin.configuration.SkieExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType

class SkieConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                alias(libs.plugins.skie)
            }
            if (extensions.findByType<SkieExtension>() != null) {
                extensions.configure<SkieExtension> {
                    isEnabled.set(false)
                    features {
                        coroutinesInterop.set(true)
                    }
                }
            }
        }
    }
}