package ca.glong.komodo.feature.alerts.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import ca.glong.komodo.feature.alerts.api.AlertsKey
import ca.glong.komodo.feature.alerts.ui.AlertsScreen

/**
 * Alerts feature navigation entries.
 */
fun EntryProviderScope<Any>.featureAlertsEntries() {
    entry<AlertsKey> {
        AlertsScreen {

        }
    }
}
