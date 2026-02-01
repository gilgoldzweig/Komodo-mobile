package ca.glong.komodo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import ca.glong.komodo.navigation.LauncherKey
import ca.glong.komodo.shared.infra.NavContainer
import ca.glong.komodo.shared.infra.NavKey
import org.koin.compose.KoinContext
import org.koin.compose.getKoin

@Composable
fun App() {
    val koin = getKoin()
    val navContainers = remember { koin.get<Set<NavContainer>>() }

    val backStack = remember { mutableStateListOf<NavKey>(LauncherKey) }

    val entries = entryProvider {
        navContainers.forEach {
            with(it) {
                bind()
            }
        }
    }

    KoinContext {
        MaterialTheme {
            NavDisplay(backStack, entryProvider = entries)
        }
    }
}
