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

    operator fun invoke(page: Int = 1) = flow<DataState<List<Character>>> {
        println("MyTag, GetCharacters invoked! Page: $page")
        val characters = mutableListOf<Character>()

        val dataState = service.getCharacters(page)
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
}