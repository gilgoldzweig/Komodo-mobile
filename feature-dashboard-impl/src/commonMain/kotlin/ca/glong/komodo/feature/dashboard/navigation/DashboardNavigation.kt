package ca.glong.komodo.feature.dashboard.navigation

import ca.glong.komodo.feature.dashboard.api.DashboardKey
import ca.glong.komodo.feature.dashboard.ui.DashboardScreen
import org.koin.compose.navigation3.EntryProviderInstaller
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("ca.glong.komodo.feature.dashboard")
class DashboardModule {

    @OptIn(KoinExperimentalAPI::class)
    @Single
    fun provideNav(): EntryProviderInstaller = {
        entry<DashboardKey> {
            DashboardScreen()
        }
    }
}
