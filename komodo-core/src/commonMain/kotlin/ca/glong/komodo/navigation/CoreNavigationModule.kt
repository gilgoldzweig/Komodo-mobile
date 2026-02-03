package ca.glong.komodo.navigation

import ca.glong.komodo.ui.NavigationTestScreen
import org.koin.compose.navigation3.EntryProviderInstaller
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
class CoreNavigationModule {

    @OptIn(KoinExperimentalAPI::class)
    @Single
    fun provideNav(): EntryProviderInstaller = {
        entry<NavigationTestKey> {
            NavigationTestScreen()
        }
    }
}
