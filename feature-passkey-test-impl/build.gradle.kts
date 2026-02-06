plugins {
    id("komodo.multiplatform")
    id("komodo.android.library")
    id("komodo.compose")
    id("komodo.koin")
    id("komodo.nav")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.sharedInfra)
            implementation(projects.coreAuth)
            implementation(projects.coreNetwork)
            api(projects.featurePasskeyTestApi)
            
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
        }
    }
}
