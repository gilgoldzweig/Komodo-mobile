plugins {
    `kotlin-dsl`
}

group = "ca.glong.komodo.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.mokkery)
}

gradlePlugin {
    plugins {
        register("kotlinMultiplatform") {
            id = "komodo.kotlin.multiplatform"
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
    }
}
kotlin {
    sourceSets {
        all {
            languageSettings.enableLanguageFeature("ContextParameters")
        }
    }
}