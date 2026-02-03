package ca.glong.komodo.navigation

import androidx.navigation3.runtime.NavKey
import ca.glong.komodo.feature.alerts.api.AlertsKey
import ca.glong.komodo.feature.auth.api.LoginKey
import ca.glong.komodo.feature.auth.api.ServerSetupKey
import ca.glong.komodo.feature.dashboard.api.DashboardKey
import ca.glong.komodo.feature.dashboard.navigation.DashboardModule
import ca.glong.komodo.feature.resources.api.ResourceDetailKey
import ca.glong.komodo.feature.resources.api.ResourceListKey
import ca.glong.komodo.ui.LauncherScreen
import ca.glong.komodo.ui.NavigationTestScreen
import kotlinx.serialization.KSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlinx.serialization.serializer
import org.koin.compose.navigation3.EntryProviderInstaller
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
@Configuration
class MainNavigationModule {

    @OptIn(KoinExperimentalAPI::class)
    @Single
    @Named("MainNavEntryProvider")
    fun provideNav(): EntryProviderInstaller = {
        entry<LauncherKey> {
            LauncherScreen()
        }
        entry<NavigationTestKey> {
            NavigationTestScreen()
        }
    }

    @Single
    fun navKeySerializersModule() = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(NavigationTestKey.serializer())
            subclass(LauncherKey.serializer())
            subclass(LoginKey.serializer())
            subclass(ServerSetupKey.serializer())
            subclass(DashboardKey.serializer())
            subclass(AlertsKey.serializer())
            subclass(ResourceListKey.serializer())
            subclass(ResourceDetailKey.serializer())
        }
    }

    @Single
    fun navKeySerializer(serializersModule: SerializersModule): KSerializer<NavKey> =
        serializersModule.serializer()


}