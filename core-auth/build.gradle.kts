plugins {
    id("komodo.kotlin.multiplatform")
    id("komodo.android.library")
//    alias(libs.plugins.android.kotlin.multiplatform.library)
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

        commonTest.dependencies {
            implementation(libs.bundles.common.test)

        }

        androidUnitTest.dependencies {
            implementation(libs.junit)
            implementation(libs.bundles.common.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

