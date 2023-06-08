package com.example.datasource.network

import com.example.datasource.network.model.GetCharactersDto
import com.example.datasource.network.model.toCharacter
import com.example.domain.Character
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CharacterServiceImpl(private val httpClient: HttpClient) : CharacterService {

    override suspend fun getAllCharacters(): List<Character> {
        return httpClient
            .get(CharacterEndpoints.GET_CHARACTERS)
            .body<GetCharactersDto>()
            .results
            .map { it.toCharacter() }
    }

}