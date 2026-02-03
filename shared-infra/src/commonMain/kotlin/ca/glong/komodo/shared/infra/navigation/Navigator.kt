package ca.glong.komodo.shared.infra.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Module
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Single

interface Navigator {
    var backStack: NavBackStack<NavKey>
    fun goTo(route: NavKey)
    fun goBack()
}

val LocalNavigator = staticCompositionLocalOf<Navigator> {
    error("No Navigator provided")
}

class NavigatorMain(startDestination: NavKey) : Navigator {
    override var backStack = NavBackStack<NavKey>(startDestination)

    override fun goTo(route: NavKey) {
        backStack.add(route)
    }

    override fun goBack() {
        backStack.removeLastOrNull()
    }
}