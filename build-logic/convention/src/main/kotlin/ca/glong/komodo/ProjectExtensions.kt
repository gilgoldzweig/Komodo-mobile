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

internal fun VersionCatalog.bundle(name: String): Any {
    val bundle = findBundle(name)
    if (bundle.isPresent) return bundle.get()
    error("Can't find bundle by name `$name` in versions catalog")
}

internal fun Project.lib(name: String): Any {
    val library = libs.findLibrary(name)
    if (library.isPresent) return library.get()
    error("Can't find library by name `$name` in versions catalog")
}

internal fun Project.versionInt(alias: String): Int =
    versionString(alias).toInt()

internal fun Project.versionString(alias: String): String =
    libs.requiredVersion(alias)

internal fun DependencyHandler.project(path: String): Dependency =
    project(mapOf("path" to path))

context(project: Project)
internal fun KotlinDependencyHandler.bom(alias: String): Dependency? =
    implementation(
        project.dependencies.platform(
            project.libs.findLibrary(alias).get()
        )
    )

context(project: Project)
internal fun DependencyHandler.bom(alias: String): Dependency? =
    add("implementation",
        project.dependencies.platform(
            project.libs.findLibrary(alias).get()
        )
    )

context(project: Project)
internal fun KotlinDependencyHandler.bundle(alias: String): Dependency? =
    implementation(project.libs.bundle(alias))


context(project: Project)
internal fun DependencyHandler.bundle(alias: String): Dependency? =
    add("implementation", project.libs.bundle(alias))

context(project: Project)
internal fun KotlinDependencyHandler.implLib(alias: String): Dependency? =
    implementation(project.libs.findLibrary(alias).get())

context(project: Project)
internal fun DependencyHandler.implLib(alias: String): Dependency? =
    add("implementation", project.libs.findLibrary(alias).get())

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