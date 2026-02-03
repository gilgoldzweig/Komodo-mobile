package ca.glong.komodo.shared.infra.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration
import org.koin.compose.koinInject
import kotlinx.serialization.modules.SerializersModule

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
