plugins {
    id("komodo.kotlin.multiplatform")
    id("komodo.android.library")
    id("komodo.compose")
    id("komodo.koin")
    id("komodo.nav")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexplicit-backing-fields")
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.slf4j.simple)
            implementation(libs.androidx.security.crypto.ktx)
            implementation(libs.cryptography.provider.jdk)
        }

        commonMain.dependencies {

            // Feature API modules (for NavKeys)
            implementation(projects.sharedInfra)
            implementation(projects.featureAuthApi)
            implementation(projects.featureDashboardApi)
            implementation(projects.featureResourcesApi)
            implementation(projects.featureAlertsApi)

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
