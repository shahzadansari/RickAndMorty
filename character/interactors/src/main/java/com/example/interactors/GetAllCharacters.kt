package com.example.interactors

import com.example.core.DataState
import com.example.datasource.network.CharacterService
import com.example.domain.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllCharacters(private val characterService: CharacterService) {

    fun execute(): Flow<DataState<List<Character>>> = flow {
        emit(DataState.Loading())

        try {
            val characters = characterService.getAllCharacters()
            emit(DataState.Success(data = characters))
        } catch (exception: Exception) {
            println(exception.localizedMessage)
            emit(DataState.Error(exception))
        }
    }
}