package ca.glong.komodo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey

/**
 * Core navigation entries - maps core NavKeys to composable screens.
 */
fun EntryProviderScope<Any>.coreEntries() {
    entry<LauncherKey> {
        LauncherScreen()
    }
}
