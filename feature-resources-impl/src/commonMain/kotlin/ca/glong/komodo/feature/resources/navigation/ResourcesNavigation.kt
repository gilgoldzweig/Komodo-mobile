package ca.glong.komodo.feature.resources.navigation

import androidx.navigation3.runtime.EntryProviderScope
import ca.glong.komodo.feature.resources.api.ResourceDetailKey
import ca.glong.komodo.feature.resources.api.ResourceListKey
import ca.glong.komodo.feature.resources.ui.ResourceDetailScreen
import ca.glong.komodo.feature.resources.ui.ResourceListScreen

/**
 * Resources feature navigation entries.
 */
fun EntryProviderScope<Any>.featureResourcesEntries() {
    entry<ResourceListKey> {
        ResourceListScreen()
    }

    entry<ResourceDetailKey> { navKey ->
        ResourceDetailScreen(key = navKey)
    }
}
