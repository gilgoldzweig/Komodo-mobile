package ca.glong.komodo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavKey
//import ca.glong.komodo.shared.infra.navigation.MainNavigator
import ca.glong.komodo.shared.infra.navigation.Navigator
//import ca.glong.komodo.shared.infra.navigation.rememberNavigationState
import ca.glong.komodo.theme.LocalNavigator
import ca.glong.komodo.theme.ProvideNavigator
import org.koin.compose.koinInject

private val DarkColorScheme = darkColorScheme()
private val LightColorScheme = lightColorScheme()

@Composable
fun KomodoTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme
//    val navigationState = rememberNavigationState()
//    val navigator = remember(navigationState) { MainNavigator(navigationState) }

    MaterialTheme(
        colorScheme = colorScheme,
        // typography = Typography,
        content = content
    )
}

/**
 * Helper to access the navigator from any composable.
 */
object KomodoTheme {
    val navigator: Navigator
        @Composable
        get() = LocalNavigator.current
}
