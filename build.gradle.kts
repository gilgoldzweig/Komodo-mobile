import org.gradle.kotlin.dsl.detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.cocoapods) apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.composeHotReload) apply false
    alias(libs.plugins.koin.compiler) apply false
    alias(libs.plugins.metro) apply false
    alias(libs.plugins.mokkery) apply false
    alias(libs.plugins.detekt.compiler.plugin)
}

detekt {
    config.setFrom(files("$rootDir/codestyle/detekt/detekt.yml"))
    autoCorrect.set(project.hasProperty("ac"))
    enableCompilerPlugin.set(true)
}

dependencies {
    detektPlugins(libs.detekt.compose)
    detektPlugins(libs.detekt.formatting)
}