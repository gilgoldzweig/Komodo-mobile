plugins {
    id("komodo.kotlin.multiplatform")
    id("komodo.android.library")
    id("komodo.compose")
    id("komodo.koin")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.coreDomain)
            implementation(projects.coreNetwork)
            implementation(projects.featureResourcesApi)
            implementation(projects.sharedUI)

            // Navigation 3
            implementation(libs.bundles.nav3)

            // Coroutines
            implementation(libs.kotlinx.coroutines.core)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}
