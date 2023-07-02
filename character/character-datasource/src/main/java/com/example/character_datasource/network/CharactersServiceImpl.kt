package com.example.character_datasource.network

import com.example.character_datasource.network.model.GetCharactersDto
import com.example.character_datasource.network.model.toCharacter
import com.example.character_domain.Character
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class CharactersServiceImpl(private val httpClient: HttpClient) : CharactersService {

    override suspend fun getCharacters(page: Int): List<Character> {
        return httpClient
            .get(CharacterEndpoints.GET_CHARACTERS) {
                parameter("page", page)
            }
            .body<GetCharactersDto>()
            .results
            .map { it.toCharacter() }
    }

}