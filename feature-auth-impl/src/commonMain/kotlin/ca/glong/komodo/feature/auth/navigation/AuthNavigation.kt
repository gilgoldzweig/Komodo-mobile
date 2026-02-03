package ca.glong.komodo.feature.auth.navigation

import ca.glong.komodo.feature.auth.api.LoginKey
import ca.glong.komodo.feature.auth.api.ServerSetupKey
import ca.glong.komodo.feature.auth.ui.LoginScreen
import ca.glong.komodo.feature.auth.ui.ServerSetupScreen
import org.koin.compose.navigation3.EntryProviderInstaller
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
@ComponentScan("ca.glong.komodo.feature.auth")
@Configuration
class AuthModule {

    @OptIn(KoinExperimentalAPI::class)
    @Single
    @Named("AuthNavEntryProvider")
    fun provideNav(): EntryProviderInstaller = {
        entry<LoginKey> {
            LoginScreen()
        }
        entry<ServerSetupKey> {
            ServerSetupScreen()
        }
    }
}
