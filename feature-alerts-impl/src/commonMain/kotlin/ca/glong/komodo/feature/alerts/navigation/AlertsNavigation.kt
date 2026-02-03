package ca.glong.komodo.feature.alerts.navigation

import ca.glong.komodo.feature.alerts.api.AlertsKey
import ca.glong.komodo.feature.alerts.ui.AlertsScreen
import org.koin.compose.navigation3.EntryProviderInstaller
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
@ComponentScan("ca.glong.komodo.feature.alerts")
@Configuration
class AlertsModule {

    @OptIn(KoinExperimentalAPI::class)
    @Single
    @Named("AlertsNavEntryProvider")
    fun provideNav(): EntryProviderInstaller = {
        entry<AlertsKey> {
            AlertsScreen()
        }
    }
}