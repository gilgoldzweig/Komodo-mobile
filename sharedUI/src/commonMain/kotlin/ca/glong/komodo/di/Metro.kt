package ca.glong.komodo.di

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import ca.glong.komodo.shared.infra.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.createGraph
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@SingleIn(AppScope::class)
@DependencyGraph(AppScope::class)
interface AppComponent {
    val entryBuilders: Set<EntryProviderScope<NavKey>.() -> Unit>
}

fun createAppComponent(): AppComponent = createGraph()

@ContributesTo(AppScope::class)
interface NetworkModule {
    @SingleIn(AppScope::class)
    @Provides
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
}
