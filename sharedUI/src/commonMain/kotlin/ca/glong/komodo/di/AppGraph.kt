package ca.glong.komodo.di

import ca.glong.komodo.feature.auth.navigation.FeatureAuthNavModule
import ca.glong.komodo.shared.infra.NavContainer
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("ca.glong.komodo")
class AppModule {

    @Single
    fun navContainers(
        authNavModule: FeatureAuthNavModule,
        // TODO add other feature nav modules here
    ): Set<NavContainer> {
        return setOf(
            authNavModule,
        )
    }
}
