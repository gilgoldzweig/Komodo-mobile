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
    }
}

includeBuild("build-logic")
include(":core")
include(":androidApp")

// Core modules
include(":core-domain")
include(":core-network")

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

