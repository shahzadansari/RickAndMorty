package com.example.datasource.network

import com.example.domain.Character
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface CharacterService {
    suspend fun getAllCharacters(): List<Character>

    companion object Factory {
        fun build(): CharacterServiceImpl {
            return CharacterServiceImpl(
                httpClient = HttpClient(Android) {
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

                    Logging {
                        logger = Logger.SIMPLE
                        level = LogLevel.ALL
                    }
                }
            )
        }
    }
}