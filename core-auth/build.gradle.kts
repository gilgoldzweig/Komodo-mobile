plugins {
    id("komodo.multiplatform")
    id("komodo.android.library")
    id("komodo.koin")
    id("komodo.detekt")
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
            implementation(libs.tink.android)
        }

        commonTest.dependencies {
            implementation(libs.bundles.common.test)
        }

        androidHostTest.dependencies {
            implementation(libs.junit)
            implementation(libs.bundles.common.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.robolectric)
            implementation(libs.androidx.core)
        }
    }
}
