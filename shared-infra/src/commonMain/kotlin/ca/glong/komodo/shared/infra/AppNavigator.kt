package ca.glong.komodo.shared.infra

interface AppNavigator {
    val currentRoute: NavKey
    fun navigate(route: NavKey)
    fun navigateUp()
    fun popTo(route: NavKey, inclusive: Boolean = false)
}
