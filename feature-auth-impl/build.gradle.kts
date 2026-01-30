plugins {
    id("komodo.kotlin.multiplatform")
    id("komodo.android.library")
    id("komodo.compose")
    id("komodo.metro")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.coreDomain)
            implementation(projects.coreNetwork)
            implementation(projects.featureAuthApi)

            // Coroutines
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}
