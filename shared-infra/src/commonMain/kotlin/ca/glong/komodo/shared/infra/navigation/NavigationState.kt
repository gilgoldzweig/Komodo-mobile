package ca.glong.komodo.shared.infra.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import org.koin.compose.koinInject

class NavigationState(
    val backStack: NavBackStack<NavKey>,
)

@Composable
fun rememberNavigationState(
    startRoute: NavKey,
): NavigationState {
    val serializers: SerializersModule = koinInject()
    val backStack = rememberNavBackStack(SavedStateConfiguration {
        serializersModule = serializers
    }, startRoute)

    return remember(backStack) {
        NavigationState(backStack)
    }
}
