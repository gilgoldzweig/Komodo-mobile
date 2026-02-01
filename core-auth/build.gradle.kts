plugins {
    id("komodo.kotlin.multiplatform")
    id("komodo.android.library")
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "CoreAuth"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Coroutines
            implementation(libs.kotlinx.coroutines.core)

            // DateTime for TTL handling
            implementation(libs.kotlinx.datetime)
        }

        androidMain.dependencies {
            // Android Security/Crypto for EncryptedDataStore
            implementation(libs.androidx.security.crypto.ktx)
            implementation(libs.androidx.datastore.preferences)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }

    android {
        namespace = "ca.glong.komodo.core.auth"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
