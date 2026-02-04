plugins {
    id("komodo.kotlin.multiplatform")
    id("komodo.android.library")
    id("komodo.koin")
//    alias(libs.plugins.android.kotlin.multiplatform.library)
}

kotlin {


    sourceSets {

        commonMain.dependencies {
            // Coroutines
            implementation(libs.kotlinx.coroutines.core)

            // DateTime for TTL handling
            implementation(libs.kotlinx.datetime)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.bundles.koin)
        }

        androidMain.dependencies {
            // Android Security/Crypto for EncryptedDataStore
            implementation(libs.androidx.security.crypto.ktx)
            implementation(libs.androidx.datastore.preferences)
            implementation("io.insert-koin:koin-annotations:2.3.2-Beta1")
        }

        commonTest.dependencies {
            implementation(libs.bundles.common.test)
        }

        androidHostTest.dependencies {
            implementation(libs.junit)
            implementation(libs.bundles.common.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation("org.robolectric:robolectric:4.11.1")
            implementation("androidx.test:core:1.6.1")
        }
    }
}

