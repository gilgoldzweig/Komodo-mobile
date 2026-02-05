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
            implementation(projects.theme)
            implementation(projects.coreDomain)
            implementation(projects.coreNetwork)
            api(projects.featureAlertsApi)
            implementation(projects.sharedInfra)
        }
    }
}