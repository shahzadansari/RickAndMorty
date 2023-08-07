package com.example.ui_character_details.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.DataState
import com.example.core.Queue
import com.example.core.UIComponent
import com.example.character_interactors.GetCharacterFromCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterFromCache: GetCharacterFromCache,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(CharacterDetailsState())
        private set

    init {
        savedStateHandle.get<Int>("characterId")?.let { characterId ->
            onTriggerEvent(CharacterDetailsEvent.GetCharacterFromCache(characterId))
        }
    }

    fun onTriggerEvent(event: CharacterDetailsEvent) {
        when (event) {
            is CharacterDetailsEvent.GetCharacterFromCache -> getCharacterFromCache(event.id)
            is CharacterDetailsEvent.RemoveHeadFromQueue -> removeHeadMessage()
        }
    }

    private fun getCharacterFromCache(id: Int) {
        viewModelScope.launch {
            getCharacterFromCache.execute(id).collect { dataState ->
                state = state.copy(isLoading = dataState is DataState.Loading)
                if (dataState is DataState.Success) {
                    state = state.copy(character = dataState.data)
                } else if (dataState is DataState.Error) {
                    appendToMessageQueue(
                        uiComponent = UIComponent.Dialog(
                            title = "Error",
                            description = dataState.exception?.message
                        )
                    )
                }
            }
        }
    }

    private fun appendToMessageQueue(uiComponent: UIComponent) {
        val queue = state.errorQueue
        queue.add(uiComponent)
        state = state.copy(errorQueue = Queue(mutableListOf())) // forces recompose
        state = state.copy(errorQueue = queue)
    }

    private fun removeHeadMessage() {
        try {
            val queue = state.errorQueue
            queue.remove() // can throw exception if empty
            state = state.copy(errorQueue = Queue(mutableListOf())) // forces recompose
            state = state.copy(errorQueue = queue)
        } catch (e: Exception) {
            println(e.localizedMessage)
        }
    }
}