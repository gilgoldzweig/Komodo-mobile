plugins {
    id("komodo.kotlin.multiplatform")
    id("komodo.android.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.sharedInfra)
        }
    }
}
