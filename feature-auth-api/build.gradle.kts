plugins {
    id("komodo.multiplatform")
    id("komodo.android.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.sharedInfra)
        }
    }
}
