package com.example.character_datasource.di

import com.example.character_datasource.cache.CharactersCache
import com.example.character_datasource.cache.CharactersCacheImpl
import com.example.character_datasource.cache.CharactersDatabase
import com.example.character_datasource.network.CharactersService
import com.example.character_datasource.network.CharactersServiceImpl
import com.example.core.HttpExceptions
import com.example.core.toApiException
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val dataModule = module {
    single<CharactersService> {
        CharactersServiceImpl(get())
    }
    single {
        CharactersDatabase(get())
    }
    single<CharactersCache> {
        CharactersCacheImpl(get())
    }

    single {
        HttpClient(Android) {
            expectSuccess = true
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(HttpTimeout) {
                val timeoutDuration: Long = 30_000
                requestTimeoutMillis = timeoutDuration
                connectTimeoutMillis = timeoutDuration
                socketTimeoutMillis = timeoutDuration
            }
            Logging {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
            HttpResponseValidator {
                handleResponseExceptionWithRequest { cause, _ ->
                    (cause as? Exception)?.toApiException()?.let {
                        throw it
                    }
                }
                validateResponse { response ->
                    if (!response.status.isSuccess()) {
                        val failureReason = when (response.status) {
                            HttpStatusCode.Unauthorized -> "Unauthorized request"
                            HttpStatusCode.Forbidden -> "${response.status.value} Missing API key."
                            HttpStatusCode.NotFound -> "Invalid Request"
                            HttpStatusCode.UpgradeRequired -> "Upgrade to VIP"
                            HttpStatusCode.RequestTimeout -> "Network Timeout"
                            in HttpStatusCode.InternalServerError..HttpStatusCode.GatewayTimeout -> "${response.status.value} Server Error"
                            else -> "Network error!"
                        }

                        throw HttpExceptions(response = response, failureReason = failureReason, cachedResponseText = response.bodyAsText())
                    }
                }
            }
        }
    }
}