package com.example.ui_character_details.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.character_interactors.GetCharacterFromCache
import com.example.character_interactors.onError
import com.example.character_interactors.onLoading
import com.example.character_interactors.onSuccess
import com.example.core.Queue
import com.example.core.UIComponent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(
    private val getCharacterFromCacheUsecase: GetCharacterFromCache,
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
            getCharacterFromCacheUsecase(id)
                .onLoading { state = state.copy(isLoading = it) }
                .onSuccess { state = state.copy(character = it) }
                .onError { appendToMessageQueue(uiComponent = UIComponent.Dialog(title = "Error", description = it.errorMsg)) }
                .collect()
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