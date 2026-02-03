rootProject.name = "Komodo"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        // Needed for org.jetbrains.androidx.navigation3 (Navigation 3 for KMP)
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

includeBuild("build-logic")
include(":shared-infra")
include(":theme")

include(":komodo-core")
include(":androidApp")

// Core modules
include(":core-domain")
include(":core-network")
include(":core-auth")

// Feature: Auth
include(":feature-auth-api")
include(":feature-auth-impl")

// Feature: Dashboard
include(":feature-dashboard-api")
include(":feature-dashboard-impl")

// Feature: Resources
include(":feature-resources-api")
include(":feature-resources-impl")

// Feature: Alerts
include(":feature-alerts-api")
include(":feature-alerts-impl")
