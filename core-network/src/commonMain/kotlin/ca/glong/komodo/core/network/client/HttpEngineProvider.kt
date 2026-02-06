package ca.glong.komodo.core.network.client

import io.ktor.client.engine.HttpClientEngine

expect fun createPlatformEngine(): HttpClientEngine
