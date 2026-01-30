package ca.glong.komodo

internal object Plugins {
    const val ANDROID_APPLICATION = "com.android.application"
    const val COMPOSE = "org.jetbrains.compose"
    const val COMPOSE_COMPILER = "org.jetbrains.kotlin.plugin.compose"
    const val COMPOSE_HOT_RELOAD = "org.jetbrains.compose.hot-reload"
    const val KSP = "com.google.devtools.ksp"
    const val MULTIPLATFORM = "org.jetbrains.kotlin.multiplatform"
    const val MULTIPLATFORM_LIBRARY = "com.android.kotlin.multiplatform.library"
    const val SERIALIZATION = "org.jetbrains.kotlin.plugin.serialization"
    const val KOIN = "io.insert-koin.compiler.plugin"
    const val METRO = "io.insert-koin.compiler.plugin"
    const val MOKKERY = "dev.mokkery"
}

internal object VersionNames {
    const val MIN_SDK = "android-minSdk"
    const val COMPILE_SDK = "android-compileSdk"
}

internal object Packages {
    const val KOMODO = "ca.glong.komodo"
}