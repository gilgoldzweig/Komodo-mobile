package ca.glong.komodo

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
fun MainViewController() = ComposeUIViewController({
    this.enforceStrictPlistSanityCheck = false
}, { App() })
