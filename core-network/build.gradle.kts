plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.koin.compiler)
    alias(libs.plugins.mokkery)
}

kotlin {

    listOf(
       iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "CoreNetwork"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.coreDomain)

            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.serialization.kotlinx.json)

            // Koin
            implementation(project.dependencies.platform(libs.koin.bom))
//            implementation(libs.koin.core)
//            implementation(libs.koin.annotations)

            implementation(libs.bundles.koin)
            // Coroutines
            implementation(libs.kotlinx.coroutines.core)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
    android {
        namespace = "ca.glong.komodo.network"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
