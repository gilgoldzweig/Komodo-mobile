plugins {
    id("komodo.kotlin.multiplatform")
    id("komodo.android.library")
    id("komodo.compose")
    id("komodo.koin")
    id("komodo.nav")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.sharedInfra)
            implementation(projects.coreDomain)
            implementation(projects.coreNetwork)
            api(projects.featureAuthApi)
            implementation(projects.featureDashboardApi)
            implementation(projects.sharedInfra)

            // Coroutines
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}
