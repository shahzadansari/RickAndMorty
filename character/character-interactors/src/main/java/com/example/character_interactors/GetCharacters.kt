package com.example.character_interactors

import com.example.character_datasource.cache.CharactersLocal
import com.example.character_datasource.network.CharactersRemote
import com.example.character_domain.Character
import com.example.core.DataState
import kotlinx.coroutines.flow.flow

class GetCharacters(
    private val service: CharactersRemote,
    private val cache: CharactersLocal
) {

    operator fun invoke() = flow<DataState<List<Character>>> {
        val characters = mutableListOf<Character>()

        val dataState = getCharacters(service)
        if (dataState is DataState.Success) {
            characters.addAll(dataState.data)
        }

        cache.insertCharacters(characters)
        val cachedCharacters = cache.getAllCharacters()

        if (cachedCharacters.isNotEmpty()) {
            emit(DataState.Success(data = cachedCharacters))
        } else {
            throw dataState.cause as Exception
        }
    }.safeDataStateFlow()

    /**
     * getCharacters() method returns 60 characters from API. This API automatically limits each API response to 20 characters only and since pagination is not in place yet, this method loads characters till page 3.
     * */
    private suspend fun getCharacters(service: CharactersRemote): DataState<List<Character>> {
        val characters = mutableListOf<Character>()
        repeat(3) { index ->
            val dataState = service.getCharacters(index + 1)
            if (dataState is DataState.Success) {
                characters.addAll(dataState.data)
            } else if (dataState is DataState.Error) {
                return DataState.Error(dataState.cause)
            }
        }
        return DataState.Success(data = characters)
    }
}