plugins {
    id("komodo.multiplatform")
    id("komodo.android.library")
    id("komodo.compose")
    id("komodo.koin")
    id("komodo.nav")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexplicit-backing-fields")
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.sharedInfra)
        }
    }
}
