package ca.glong.komodo.di

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.KoinApplication
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.dsl.KoinAppDeclaration
import org.koin.plugin.module.dsl.startKoin

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin<KoinApp> {
        appDeclaration()
    }
}

@KoinApplication(
    modules = [SharedModule::class, NetworkModule::class]
)
class KoinApp

@Module
@ComponentScan("ca.glong.komodo")
class SharedModule

@Single
class KtorLoggingLoggerAdapter : Logger {
    private val logger = KotlinLogging.logger("HttpClient")

    override fun log(message: String) {
        logger.debug { message }
    }
}

@Module
class NetworkModule {

    @Single
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
//            install(Auth) {
//                bearer {
//                    loadTokens {
//                        val token = getKCrypt().getString("JWT_TOKEN")
//                        if (!token.isNullOrBlank()) {
//                            BearerTokens(token, token)
//                        } else {
//                            null
//                        }
//                    }
//                }
//            }
        }
    }
}


