plugins {
    id("komodo.kotlin.multiplatform")
    id("komodo.android.library")
    id("komodo.compose")
    id("komodo.metro")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexplicit-backing-fields")
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.compose.uiTooling)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.slf4j.simple)
            implementation(libs.androidx.security.crypto.ktx)
            implementation(libs.cryptography.provider.jdk)
        }

        commonMain.dependencies {
            implementation(libs.bundles.nav3)

            // Feature modules
            implementation(projects.featureAuthApi)
            implementation(projects.featureAuthImpl)
            implementation(projects.featureDashboardApi)
            implementation(projects.featureDashboardImpl)
            implementation(projects.featureResourcesApi)
            implementation(projects.featureResourcesImpl)
            implementation(projects.featureAlertsApi)
            implementation(projects.featureAlertsImpl)

            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.serialization.kotlinx.json)

            // DataStore
            implementation(libs.androidx.datastore.preferences)

            // Coroutines
            implementation(libs.kotlinx.coroutines.core)

            // Cryptography
            implementation(libs.cryptography.core)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.cryptography.provider.apple)
        }
    }
}
