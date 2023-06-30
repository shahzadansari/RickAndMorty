package com.example.ui_character_details.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.DataState
import com.example.interactors.GetCharacterFromCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterFromCache: GetCharacterFromCache,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state = mutableStateOf(CharacterDetailsState())

    init {
        savedStateHandle.get<Int>("characterId")?.let { characterId ->
            onTriggerEvent(CharacterDetailsEvent.GetCharacterFromCache(characterId))
        }
    }

    fun onTriggerEvent(event: CharacterDetailsEvent) {
        when (event) {
            is CharacterDetailsEvent.GetCharacterFromCache -> getCharacterFromCache(event.id)
        }
    }

    private fun getCharacterFromCache(id: Int) {
        viewModelScope.launch {
            getCharacterFromCache.execute(id).collect { dataState ->
                state.value = state.value.copy(isLoading = dataState is DataState.Loading)
                if (dataState is DataState.Success) {
                    state.value = state.value.copy(character = dataState.data)
                }
            }
        }
    }
}