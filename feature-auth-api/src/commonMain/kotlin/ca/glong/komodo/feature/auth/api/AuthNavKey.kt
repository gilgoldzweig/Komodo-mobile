package ca.glong.komodo.feature.auth.api

import ca.glong.komodo.shared.infra.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object LoginKey : NavKey

@Serializable
data object ServerSetupKey : NavKey
