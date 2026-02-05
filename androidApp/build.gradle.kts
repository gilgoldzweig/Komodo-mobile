
plugins {
    id("komodo.android.application")
    id("komodo.koin")
    id("komodo.nav")
    id("komodo.compose")
}

dependencies {
    implementation(projects.theme)
    implementation(projects.sharedInfra)
    implementation(projects.komodoCore)
    implementation(projects.coreDomain)
    implementation(projects.coreNetwork)

    // Feature modules
    implementation(projects.featureAuthImpl)
    implementation(projects.featureDashboardImpl)
    implementation(projects.featureResourcesImpl)
    implementation(projects.featureAlertsImpl)

    // Compose

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.compose.uiToolingPreview)
    debugImplementation(libs.compose.uiTooling)

    implementation(libs.androidx.lifecycle.viewmodelCompose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    // Use the AndroidX lifecycle runtime KTX library for Android-specific code
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Logging
    implementation(libs.kotlin.logging)

    // Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
}
