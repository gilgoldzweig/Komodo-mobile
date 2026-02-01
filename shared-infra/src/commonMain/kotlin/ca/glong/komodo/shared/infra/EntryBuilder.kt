package ca.glong.komodo.shared.infra

import androidx.navigation3.runtime.EntryProviderScope

interface NavContainer {

    val keys: Set<NavKey>

    fun EntryProviderScope<NavKey>.bind()
}

