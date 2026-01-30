plugins {
    id("komodo.android.application")
    id("komodo.compose")
    id("komodo.metro")
}

android {
    namespace = "ca.glong.komodo.android"

    defaultConfig {
        applicationId = "ca.glong.komodo"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.compose.uiToolingPreview)
    debugImplementation(libs.compose.uiTooling)

    implementation(libs.ktor.client.okhttp)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.slf4j.simple)

    implementation(libs.androidx.lifecycle.viewmodelCompose)
    implementation(libs.androidx.lifecycle.runtimeCompose)

    // Logging
    implementation(libs.kotlin.logging)

    // Navigation
    implementation(libs.bundles.nav3)

    // Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
}
