plugins {
    id("komodo.kotlin.multiplatform")
    id("komodo.android.library")
    id("komodo.compose")
    id("komodo.koin")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.sharedInfra)
            implementation(projects.coreDomain)
            implementation(projects.coreNetwork)
            implementation(projects.featureAuthApi)
            implementation(projects.featureDashboardApi)

            // Navigation 3
            implementation(libs.bundles.nav3)

            // Coroutines
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}
