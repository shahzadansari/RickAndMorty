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

    fun execute(page: Int = 1): Flow<DataState<List<Character>>> = flow {
        try {
            emit(DataState.Loading())

            val characters = try {
                service.getCharacters(page)
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
}