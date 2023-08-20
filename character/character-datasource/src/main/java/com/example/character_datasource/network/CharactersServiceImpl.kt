package com.example.character_datasource.network

import com.example.character_datasource.network.model.GetCharactersDto
import com.example.character_domain.Character
import com.example.core.ApiResponse
import com.example.core.DataState
import com.example.core.safeRequest
import com.example.core.toApiException
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class CharactersServiceImpl(private val httpClient: HttpClient) : CharactersService {

    override suspend fun getCharacters(page: Int): DataState<List<Character>> {
        val response = httpClient.safeRequest<GetCharactersDto> {
            url(CharacterEndpoints.GET_CHARACTERS)
            parameter("page", page)
        }
        return when (response) {
            is ApiResponse.Success -> DataState.Success(data = response.data.characters) // Maps CharacterDto -> Character
            is ApiResponse.Error -> DataState.Error(response.cause.toApiException())
        }
    }
}