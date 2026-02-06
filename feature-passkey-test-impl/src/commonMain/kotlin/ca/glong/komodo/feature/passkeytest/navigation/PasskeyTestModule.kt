package ca.glong.komodo.feature.passkeytest.navigation

import ca.glong.komodo.feature.passkeytest.api.PasskeyTestKey
import ca.glong.komodo.feature.passkeytest.ui.PasskeyTestScreen
import org.koin.compose.navigation3.EntryProviderInstaller
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
@ComponentScan("ca.glong.komodo.feature.passkeytest")
@Configuration
class PasskeyTestModule {

    @OptIn(KoinExperimentalAPI::class)
    @Single
    @Named("PasskeyTestNavEntryProvider")
    fun provideNav(): EntryProviderInstaller = {
        entry<PasskeyTestKey> {
            PasskeyTestScreen()
        }
    }
}
