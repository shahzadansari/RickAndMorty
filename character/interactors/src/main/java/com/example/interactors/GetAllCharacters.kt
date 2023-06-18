package com.example.interactors

import com.example.core.DataState
import com.example.datasource.cache.CharactersCache
import com.example.datasource.network.CharactersService
import com.example.domain.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllCharacters(
    private val service: CharactersService,
    private val cache: CharactersCache
) {

    fun execute(): Flow<DataState<List<Character>>> = flow {
        try {
            emit(DataState.Loading())

            val characters = try {
                service.getAllCharacters()
            } catch (exception: Exception) {
                println(exception.localizedMessage)
                listOf()
            }

            cache.insertCharacters(characters)
            val cachedCharacters = cache.getAllCharacters()

            emit(DataState.Success(data = cachedCharacters))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}