package ca.glong.komodo.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import ca.glong.komodo.shared.infra.navigation.Navigator

/**
 * CompositionLocal for the [Navigator].
 */
val LocalNavigator: ProvidableCompositionLocal<Navigator> = staticCompositionLocalOf {
    error("No Navigator provided")
}

@Composable
fun ProvideNavigator(navigator: Navigator, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalNavigator provides navigator) {
        content()
    }
}