package com.example.character_interactors

import com.example.character_datasource.cache.CharactersCache
import com.example.character_domain.Character
import com.example.core.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCharacterFromCache(private val cache: CharactersCache) {

    operator fun invoke(id: Int): Flow<DataState<Character>> = flow {
        val cachedCharacter = cache.getCharacter(id)

        if (cachedCharacter != null) {
            emit(DataState.Success(data = cachedCharacter))
        } else {
            throw Exception("Character with id $id doesn't exist in cache.")
        }
    }.safeDataStateFlow()
}