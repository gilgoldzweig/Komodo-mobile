package ca.glong.komodo.di

import ca.glong.komodo.feature.alerts.navigation.AlertsModule
import ca.glong.komodo.feature.auth.navigation.AuthModule
import ca.glong.komodo.feature.dashboard.navigation.DashboardModule
import ca.glong.komodo.feature.resources.navigation.ResourcesModule
import ca.glong.komodo.navigation.CoreNavigationModule
import ca.glong.komodo.navigation.MainNavigationModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.KoinApplication
import org.koin.dsl.KoinAppDeclaration
import org.koin.plugin.module.dsl.startKoin

@KoinApplication(
    modules = [
        AlertsModule::class,
        AuthModule::class,
        CoreNavigationModule::class,
        DashboardModule::class,
        MainNavigationModule::class,
        ResourcesModule::class,
    ]
)
@ComponentScan(
    "ca.glong.komodo"
)
object KomodoKoinApp

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin<KomodoKoinApp>() {
        appDeclaration()
    }
//    KoinApp. {
//        appDeclaration()
//    }
}
