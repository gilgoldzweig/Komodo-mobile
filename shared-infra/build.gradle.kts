plugins {
    id("komodo.kotlin.multiplatform")
    id("komodo.android.library")
    id("komodo.koin")
    id("komodo.nav")
    id("komodo.compose")
}

kotlin {
    sourceSets.commonMain.dependencies {
        api(libs.nav3.ui)
    }
}