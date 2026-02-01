package ca.glong.komodo.android

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import ca.glong.komodo.di.createAppComponent

@Composable
fun App() {
    // Create the Metro DI component
    val appComponent = remember { createAppComponent() }

}
