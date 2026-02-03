plugins {
    id("komodo.kotlin.multiplatform")
    id("komodo.android.library")
    id("komodo.koin")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.sharedInfra)
        }
    }
}
