package ca.glong.komodo

import dev.detekt.gradle.Detekt
import dev.detekt.gradle.DetektCreateBaselineTask
import dev.detekt.gradle.extensions.DetektExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.SourceTask
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

class DetektConventionPlugin : Plugin<Project> {

    val Project.rootDirPath: String
        get() = project.rootDir.path
    val Project.configPath
        get() = files("$rootDirPath/codestyle/detekt/detekt.yaml")

    val Project.baselinePath: Provider<RegularFile>
        get() = layout.buildDirectory.file("baseline/detekt.xml")

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                alias(libs2.plugins.detekt)
//                alias(libs2.plugins.detektCompilerPlugin)
            }
            configureExtension()
            configureTasks()
            configureDependencies()
        }
    }

    /**
     * Configures the Detekt extension for the project.
     * See: https://detekt.dev/docs/gettingstarted/gradle#configuration
     */
    private fun Project.configureExtension() {
        configure<DetektExtension> {
            config.setFrom(configPath)

            // Automatically correct issues
            autoCorrect.set(project.hasProperty("ac"))

            // Run detekt in parallel
            parallel.set(true)

            baseline.set(baselinePath)
            basePath.set(file(rootDirPath))

            ignoredBuildTypes.set(listOf("Release", "release"))
        }
    }

    private fun Project.configureTasks() {
//        tasks.withType<KotlinCompile>().configureEach {
//            extensions.configure<KotlinCompileTaskDetektExtension> {
//                getSarif().enabled.set(true)
//                getHtml().enabled.set(true)
//            }
//        }

        tasks.withType<Detekt>().configureEach {
            jvmTarget.set(JavaVersion.VERSION_21.toString())
            reports {
//                checkstyle.required.set(true) // For CI/CD systems
                html.required.set(true) // For local inspection
                sarif.required.set(true) // For GitHub code scanning
            }
//            configureSources()
            setSource(files(projectDir))

            include("**/*.kt", "**/*.kts")
            exclude(
                "**/build/**",
                "**/resources/**",
                "**/res/**",
                "**/.idea/**"
            )
        }

        tasks.withType<DetektCreateBaselineTask>().configureEach {
            autoCorrect.set(project.hasProperty("ac"))
            jvmTarget.set(JavaVersion.VERSION_21.toString())
//            baseline.set(baselinePath)
//            configureSources()
            setSource(files(projectDir))

            include("**/*.kt", "**/*.kts")
            exclude(
                "**/build/**",
                "**/resources/**",
                "**/res/**",
                "**/.idea/**"
            )
        }
    }

    /**
     * Configures the source sets for Detekt analysis, including and excluding specific patterns.
     */
    private fun SourceTask.configureSources() {
        include("**/*.kt", "**/*.kts")
        exclude(
            "**/build/**",
            "**/.idea/**",
            "**/resources/**",
            "**/res/**"
        )
    }

    /**
     * Adds dependencies for Detekt plugins like formatting and compose rules.
     */
    private fun Project.configureDependencies() {
        dependencies {
            add("detektPlugins", libs2.detekt.compose)
            add("detektPlugins", libs2.detekt.formatting)
        }
    }
}
