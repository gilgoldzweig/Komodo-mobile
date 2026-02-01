plugins {
    id("komodo.kotlin.multiplatform")
    id("komodo.android.library")
    id("komodo.koin")
    id("komodo.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
//            implementation(libs.bundles.nav3)
            implementation(libs.nav3.ui)
//            implementation(libs.nav3.viewmodel)
//            implementation(libs.nav3.ui.)
        }
    }
}
