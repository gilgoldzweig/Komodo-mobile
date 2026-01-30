package ca.glong.komodo

import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler


/**
 * Get project's Version Catalog named 'libs' or throw
 */
internal val Project.libs: VersionCatalog
    get() = extensions
        .getByType<VersionCatalogsExtension>()
        .named("libs")

internal fun VersionCatalog.requiredVersion(alias: String): String {
    val version = findVersion(alias)
    if (version.isPresent) return version.get().requiredVersion
    error("Can't find version by alias `$alias` in versions catalog")
}

internal fun DependencyHandler.project(path: String): Dependency =
    project(mapOf("path" to path))

context(project: Project, deps: KotlinDependencyHandler)
internal fun bom(alias: String): Dependency? =
    deps.implementation(
        project.dependencies.platform(
            project.libs.findLibrary(alias).get()
        )
    )

context(project: Project)
internal fun KotlinDependencyHandler.bundle(alias: String): Dependency? =
    implementation(project.libs.findBundle(alias).get())

context(project: Project)
internal fun KotlinDependencyHandler.implLib(alias: String): Dependency? =
    implementation(project.libs.findLibrary(alias).get())

internal fun DependencyHandler.debugImplementation(notation: Any): Dependency? =
    add("debugImplementation", notation)

typealias DepsHandler = KotlinDependencyHandler.() -> Unit

fun Project.multiplatformDependencies(
    commonMain: DepsHandler = {},
    androidMain: DepsHandler = {},
    androidUnitTest: DepsHandler = {},
    iosMain: DepsHandler = {},
    iosTest: DepsHandler = {},
    commonTest: DepsHandler = {}
) {
    extensions.configure<KotlinMultiplatformExtension> {
        sourceSets.apply {
            this.commonMain.dependencies(commonMain)
            this.commonTest.dependencies(commonTest)
            this.androidMain.dependencies(androidMain)
            this.androidUnitTest.dependencies(androidUnitTest)
            this.iosMain.dependencies(iosMain)
            this.iosTest.dependencies(iosTest)
        }
    }
}