package ca.glong.komodo.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object ServerSetup : Route

    @Serializable
    data object Login : Route

    @Serializable
    data object Dashboard : Route
}
