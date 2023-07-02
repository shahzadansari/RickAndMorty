package com.example.interactors

import com.example.core.DataState
import com.example.datasource.cache.CharactersCache
import com.example.datasource.network.CharactersService
import com.example.domain.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCharacters(
    private val service: CharactersService,
    private val cache: CharactersCache
) {

    fun execute(): Flow<DataState<List<Character>>> = flow {
        try {
            emit(DataState.Loading())

            val characters = try {
                getCharacters(service)
            } catch (exception: Exception) {
                println(exception.localizedMessage)
                listOf()
            }

            cache.insertCharacters(characters)
            val cachedCharacters = cache.getAllCharacters()

            if (cachedCharacters.isNotEmpty()) {
                emit(DataState.Success(data = cachedCharacters))
            } else {
                throw Exception("Couldn't fetch characters")
            }

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    /**
     * getCharacters() method returns 100 characters from API. This API automatically limits each API response to 20 characters only and since pagination is not in place yet, this method loads characters till page 5.
     * */
    private suspend fun getCharacters(service: CharactersService): List<Character> {
        val characters = mutableListOf<Character>()
        characters.addAll(service.getCharacters(page = 1))
        characters.addAll(service.getCharacters(page = 2))
        characters.addAll(service.getCharacters(page = 3))
        characters.addAll(service.getCharacters(page = 4))
        characters.addAll(service.getCharacters(page = 5))
        return characters
    }
}