package ca.glong.komodo

//import dev.detekt.gradle.Detekt
//import dev.detekt.gradle.extensions.DetektExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceTask
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class DetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply the base Detekt plugin
//            pluginManager.apply(Plugins.DETEKT)

            // Configure the Detekt extension
//            extensions.getByType<DetektExtension>().configure(this)

//            // Configure all tasks of type Detekt
//            tasks.withType<KotlinCompile>().configureEach {
//
//            }
//            tasks.withType<Detekt>().configureEach {
//                configureTask()
//            }

            // Add detekt plugin dependencies
//            configureDependencies()
        }
    }

    /**
     * Configures the Detekt extension for the project.
     * See: https://detekt.dev/docs/gettingstarted/gradle#configuration
     */
//    private fun DetektExtension.configure(project: Project) {
//        val rootDirPath = project.rootDir.path
//        // Point to the detekt configuration file
//        config.setFrom(project.files("$rootDirPath/codestyle/detekt/detekt.yml"))
//
//        // Sets the baseline file to be used.
//        // baseline = project.file("$rootDirPath/codestyle/detekt/baseline.xml")
//
//        // The sources to be analyzed.
//        source.setFrom(
//            project.files(
//                "src/main/java",
//                "src/test/java",
//                "src/main/kotlin",
//                "src/test/kotlin"
//            )
//        )
//
//        // Automatically correct issues
//        autoCorrect.set(project.hasProperty("ac"))
//
//        // Run detekt in parallel
//        parallel.set(true)
//
//        // Fails the build if any rule violations are found.
//        // Not enabled by default to allow for CI to report issues without blocking.
//        // ignoreFailures = false
//
//        basePath.set(project.rootDir)
//
//        // Android: Don't create tasks for the specified build types (e.g. "release")
////        ignoredBuildTypes =
////            listOf("Release", "release", "Proguard", "proguard", "debug", "Debug")
////        ignoredFlavors = listOf("Staging", "staging", "Prod", "prod", "Dev", "dev")
////        ignoredVariants = listOf(
////            "devDebug", "devProguard", "devRelease",
////            "prodDebug", "prodProguard", "prodRelease",
////            "stagingDebug", "stagingProguard", "stagingRelease",
////        )
//    }

    /**
     * Configures a single Detekt task.
     */
//    private fun Detekt.configureTask() {
//        // Set the sources for this specific task
//        configureSources()
//
//        // Set the JVM target for analysis
//        jvmTarget.set(JavaVersion.VERSION_21.toString())
//
//        reports {
//            checkstyle.required.set(true) // For CI/CD systems
//            html.required.set(false) // For local inspection
//            sarif.required.set(false) // For GitHub code scanning
//        }
//    }

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
            add("detektPlugins", libs.findLibrary("detekt.formatting").get())
            add("detektPlugins", libs.findLibrary("detekt.compose").get())
        }
    }
}
