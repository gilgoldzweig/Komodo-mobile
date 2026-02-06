package ca.glong.komodo.di

import ca.glong.komodo.feature.alerts.navigation.AlertsModule
import ca.glong.komodo.feature.auth.navigation.AuthModule
import ca.glong.komodo.feature.dashboard.navigation.DashboardModule
import ca.glong.komodo.feature.passkeytest.navigation.PasskeyTestModule
import ca.glong.komodo.feature.resources.navigation.ResourcesModule
import ca.glong.komodo.navigation.CoreNavigationModule
import ca.glong.komodo.navigation.LauncherKey
import ca.glong.komodo.navigation.MainNavigationModule
import ca.glong.komodo.shared.infra.navigation.Navigator
import ca.glong.komodo.shared.infra.navigation.NavigatorMain
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Configuration
@Module(
    includes = [
        AlertsModule::class,
        AuthModule::class,
        CoreNavigationModule::class,
        DashboardModule::class,
        MainNavigationModule::class,
        PasskeyTestModule::class,
        ResourcesModule::class,
    ]
)
@ComponentScan("ca.glong.komodo")
class AppModule {

    @Single
    fun provideNavigator(): Navigator =
        NavigatorMain(LauncherKey)
}