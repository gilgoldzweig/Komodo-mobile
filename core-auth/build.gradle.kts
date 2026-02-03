plugins {
    id("komodo.kotlin.multiplatform")
    id("komodo.android.library")
}

kotlin {

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

        androidUnitTest.configure {
            dependencies {
                implementation(libs.mockk)
                implementation(libs.kotlinx.coroutines.test)
            }
        }
    }
}

