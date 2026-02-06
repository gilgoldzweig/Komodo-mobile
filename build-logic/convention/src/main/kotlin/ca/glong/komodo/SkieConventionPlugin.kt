package ca.glong.komodo

import co.touchlab.skie.configuration.FlowInterop
import co.touchlab.skie.configuration.SealedInterop
import co.touchlab.skie.configuration.SuspendInterop
import co.touchlab.skie.plugin.configuration.SkieExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class SkieConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("co.touchlab.skie")
            configure<SkieExtension> {
                isEnabled.set(true)
                features {
                    group {
                        SealedInterop.Enabled(true)
                        SuspendInterop.Enabled(true)
                        FlowInterop.Enabled(true)
                    }
                }
            }
        }
    }
}