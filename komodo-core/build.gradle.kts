import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("komodo.multiplatform")
    id("komodo.android.library")
    id("komodo.compose")
    id("komodo.koin")
    id("komodo.nav")
    id("komodo.detekt")
    // id("komodo.skie")  // Disabled - SKIE not needed for Kotlin-based iOS interop
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexplicit-backing-fields")
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.slf4j.simple)
            implementation(libs.androidx.security.crypto.ktx)
            implementation(libs.cryptography.provider.jdk)
        }

        commonMain.dependencies {

            // Feature API modules (for NavKeys)
            implementation(projects.theme)
            implementation(projects.sharedInfra)
            implementation(projects.featureAuthImpl)
            implementation(projects.featureDashboardImpl)
            implementation(projects.featureResourcesImpl)
            implementation(projects.featureAlertsImpl)
            implementation(projects.featurePasskeyTestImpl)

            // Exported Core modules
            api(projects.coreAuth)

//            implementation("org.jetbrains.compose.ui:ui-tooling-preview:1.10.0")
            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.serialization.kotlinx.json)

//            androidRuntimeClasspath("org.jetbrains.compose.ui:ui-tooling:1.10.0")
            // DataStore
            implementation(libs.androidx.datastore.preferences)

            // Coroutines
            implementation(libs.kotlinx.coroutines.core)

            // Cryptography
            implementation(libs.cryptography.core)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.cryptography.provider.apple)
        }
    }

    targets.withType<KotlinNativeTarget>().configureEach {
        binaries.framework {
            baseName = "KomodoKMP"
            isStatic = true
            freeCompilerArgs += "-Xbinary=bundleId=ca.glong.komodo"
        }
    }
//    targets.configureEach {
//        val isIosTarget = (name == "iosArm64" || name == "iosSimulatorArm64") // Simplified check
//        if (isIosTarget && this is org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget) {
//            binaries.framework {
//                baseName = "KomodoCore"
//                isStatic = true
//
//
//                // Add compiler args if needed (e.g. bundleId)
//                 freeCompilerArgs += listOf(
//                    "-Xbinary=bundleId=ca.glong.komodo.core"
//                )
//            }
//        }
//    }
}
