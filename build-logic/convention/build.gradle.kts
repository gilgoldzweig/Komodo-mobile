import org.gradle.initialization.DependenciesAccessors
import org.gradle.kotlin.dsl.support.serviceOf

plugins {
    `kotlin-dsl`
}

group = "ca.glong.komodo.buildlogic"

dependencies {
    implementation(libs.compile.gradle.plugins.android)
    compileOnly(libs.compile.gradle.plugins.compose)
    compileOnly(libs.compile.gradle.plugins.kotlin)
    compileOnly(libs.compile.gradle.plugins.ksp)
    compileOnly(libs.compile.gradle.plugins.mokkery)
    compileOnly(libs.compile.gradle.plugins.metro)
    implementation(libs.detekt.gradle.plugin)
    compileOnly(files(gradle.serviceOf<DependenciesAccessors>().classes.asFiles))
}

gradlePlugin {
    plugins {
        register("kotlinMultiplatform") {
            id = "komodo.multiplatform"
            implementationClass = "ca.glong.komodo.KotlinMultiplatformConventionPlugin"
        }
        register("androidLibrary") {
            id = "komodo.android.library"
            implementationClass = "ca.glong.komodo.AndroidLibraryConventionPlugin"
        }
        register("androidApplication") {
            id = "komodo.android.application"
            implementationClass = "ca.glong.komodo.AndroidApplicationConventionPlugin"
        }
        register("compose") {
            id = "komodo.compose"
            implementationClass = "ca.glong.komodo.ComposeConventionPlugin"
        }
        register("koin") {
            id = "komodo.koin"
            implementationClass = "ca.glong.komodo.KoinConventionPlugin"
        }
        register("metro") {
            id = "komodo.metro"
            implementationClass = "ca.glong.komodo.MetroConventionPlugin"
        }
        register("navigation") {
            id = "komodo.nav"
            implementationClass = "ca.glong.komodo.NavigationConventionPlugin"
        }
        register("detekt") {
            id = "komodo.detekt"
            implementationClass = "ca.glong.komodo.DetektConventionPlugin"
        }
    }
}
kotlin {
    sourceSets {
        all {
            languageSettings.enableLanguageFeature("ContextParameters")
        }
    }
}