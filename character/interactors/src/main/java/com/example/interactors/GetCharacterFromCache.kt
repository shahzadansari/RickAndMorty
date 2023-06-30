package com.example.interactors

import com.example.core.DataState
import com.example.datasource.cache.CharactersCache
import com.example.domain.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCharacterFromCache(private val cache: CharactersCache) {

    fun execute(id: Int): Flow<DataState<Character>> = flow {
        try {
            emit(DataState.Loading())
            val cachedCharacter = cache.getCharacter(id)

            if (cachedCharacter != null) {
                emit(DataState.Success(data = cachedCharacter))
            } else {
                throw Exception("Character with id $id doesn't exist in cache.")
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}