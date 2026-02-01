package ca.glong.komodo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider


/**
 * Aggregates Navigation 3 entry providers from core + all feature modules.
 *
 * This follows the "modularized graph" pattern: features contribute entries, and the app composes them.
 */
@Composable
fun appEntryProvider() = entryProvider {
    coreEntries()
}


fun appEntryProvider(builders: Set<EntryProviderScope<NavKey>.() -> Unit>) = entryProvider {
    builders.forEach { it() }
}