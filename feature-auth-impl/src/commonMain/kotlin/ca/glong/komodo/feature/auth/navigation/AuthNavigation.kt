package ca.glong.komodo.feature.auth.navigation

import androidx.navigation3.runtime.EntryProviderScope
import ca.glong.komodo.feature.auth.api.LoginKey
import ca.glong.komodo.feature.auth.api.ServerSetupKey
import ca.glong.komodo.feature.auth.ui.LoginScreen
import ca.glong.komodo.feature.auth.ui.ServerSetupScreen
import ca.glong.komodo.shared.infra.NavContainer
import ca.glong.komodo.shared.infra.NavKey
import org.koin.core.annotation.Module
import org.koin.dsl.*


val a = module {
}


@Module
class FeatureAuthNavModule {


    fun provideNav() {
      navigation {

      }
    }

    override val keys: Set<NavKey> = setOf(
        ServerSetupKey,
        LoginKey
    )

    override fun EntryProviderScope<NavKey>.bind() {
        entry<ServerSetupKey> {
            ServerSetupScreen()
        }

        entry<LoginKey> {
            LoginScreen()
        }
    }
}
