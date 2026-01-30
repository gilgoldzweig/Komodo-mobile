package ca.glong.komodo.di

import ca.glong.komodo.feature.alerts.AlertsModule
import ca.glong.komodo.feature.auth.AuthModule
import ca.glong.komodo.feature.dashboard.DashboardModule
import ca.glong.komodo.feature.resources.ResourcesModule
import dev.zacsweers.metro.MetroComponent
import dev.zacsweers.metro.MetroComponentCreate
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@MetroComponent
interface AppComponent {
    val httpClient: HttpClient

    val authModule: AuthModule
    val dashboardModule: DashboardModule
    val resourcesModule: ResourcesModule
    val alertsModule: AlertsModule

    @MetroComponentCreate
    interface Factory {
        fun create(): AppComponent
    }
}

fun createAppComponent(): AppComponent {
    return MetroAppComponent.Factory().create()
}

// Provide HttpClient as a singleton
fun provideHttpClient(): HttpClient {
    return HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            logger = object : Logger {
                private val kLogger = KotlinLogging.logger("HttpClient")
                override fun log(message: String) {
                    kLogger.debug { message }
                }
            }
            level = LogLevel.ALL
        }
    }
}

