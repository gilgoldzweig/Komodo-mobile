package ca.glong.komodo

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.KOIN)
//                apply(Plugins.KSP)
            }

            if (extensions.findByType<KotlinMultiplatformExtension>() != null) {
                multiplatformDependencies(commonMain = {
                    bom("koin-bom")
                    bundle("koin")

                })
            } else {
                dependencies {
                    bom("koin-bom")
                    bundle("koin")
                }
            }
            dependencies {
//                ksp("koin-compiler")
//                add("kspCommonMainMetadata", lib("koin-compiler"))
//                add("kspAndroid", lib("koin-compiler"))
//                add("kspIosSimulatorArm64", lib("koin-compiler"))
//                add("kspIosX64", lib("koin-compiler"))
//                add("kspIosArm64", lib("koin-compiler"))
            }
        }
    }
}
