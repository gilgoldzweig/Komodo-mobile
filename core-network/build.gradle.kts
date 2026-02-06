plugins {
    id("komodo.multiplatform")
    id("komodo.android.library")
    id("komodo.koin")
    id("komodo.detekt")
}

kotlin {

    sourceSets {
        commonMain.dependencies {
            implementation(projects.coreDomain)

            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.serialization.kotlinx.json)

            implementation(libs.kotlinx.coroutines.core)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

    }
}
