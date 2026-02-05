package ca.glong.komodo

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import ca.glong.komodo.navigation.LauncherKey
import ca.glong.komodo.shared.infra.navigation.Navigator
import ca.glong.komodo.ui.theme.KomodoTheme
import kotlinx.serialization.modules.SerializersModule
import org.koin.compose.koinInject
import org.koin.compose.navigation3.EntryProvider
import org.koin.compose.navigation3.koinEntryProvider
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App(
    serializersModule: SerializersModule = koinInject(),
    navigator: Navigator = koinInject(),
    entryProvider: EntryProvider<NavKey> = koinEntryProvider()
) {
    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            this.serializersModule = serializersModule
        },
        LauncherKey
    )

    navigator.backStack = backStack

    KomodoTheme {
        Scaffold {
            NavDisplay(navigator.backStack, entryProvider = entryProvider)
        }
    }
}
