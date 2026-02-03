package ca.glong.komodo.feature.resources.navigation

import ca.glong.komodo.feature.resources.api.ResourceDetailKey
import ca.glong.komodo.feature.resources.api.ResourceListKey
import ca.glong.komodo.feature.resources.ui.ResourceDetailScreen
import ca.glong.komodo.feature.resources.ui.ResourceListScreen
import org.koin.compose.navigation3.EntryProviderInstaller
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("ca.glong.komodo.feature.resources")
class ResourcesModule {

    @OptIn(KoinExperimentalAPI::class)
    @Single
    fun provideNav(): EntryProviderInstaller = {
        entry<ResourceListKey> {
            ResourceListScreen()
        }

        entry<ResourceDetailKey> { navKey ->
            ResourceDetailScreen(key = navKey)
        }
    }
}
