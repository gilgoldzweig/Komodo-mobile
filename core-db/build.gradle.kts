plugins {
    id("komodo.multiplatform")
    id("komodo.android.library")
    id("komodo.koin")
    id("komodo.detekt")
    alias(libs.plugins.sqldelight)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core-auth"))
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines)
        }
        
        androidMain.dependencies {
            implementation(libs.sqldelight.android.driver)
            implementation(libs.sqlcipher.android)
            implementation(libs.androidx.sqlite)
        }
        
        iosMain.dependencies {
            implementation(libs.sqldelight.native.driver)
        }
        
        commonTest.dependencies {
            implementation(libs.bundles.common.test)
        }
    }
}

sqldelight {
    databases {
        create("KomodoDatabase") {
            packageName.set("ca.glong.komodo.core.db")
            // Feature modules will provide .sq files
            // This is just driver configuration
        }
    }
    linkSqlite.set(false) // CRITICAL for SQLCipher on iOS
}
