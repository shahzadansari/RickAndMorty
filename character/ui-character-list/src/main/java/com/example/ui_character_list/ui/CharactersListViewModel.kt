package com.example.ui_character_list.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.character_interactors.GetCharacters
import com.example.core.ApiException
import com.example.core.DataState
import com.example.core.Queue
import com.example.core.UIComponent
import kotlinx.coroutines.launch

class CharactersListViewModel(private val getCharacters: GetCharacters) : ViewModel() {

    var state by mutableStateOf(CharactersListState())
        private set

    init {
        onTriggerEvent(CharactersListEvent.GetAllCharacters)
    }

    fun onTriggerEvent(event: CharactersListEvent) {
        when (event) {
            is CharactersListEvent.GetAllCharacters -> getCharacters()
            is CharactersListEvent.RemoveHeadFromQueue -> removeHeadMessage()
        }
    }

    private fun getCharacters() {
        viewModelScope.launch {
            getCharacters.invoke().collect { dataState ->
                state = state.copy(isLoading = dataState is DataState.Loading)
                if (dataState is DataState.Success) {
                    state = state.copy(characters = dataState.data)
                } else if (dataState is DataState.Error) {
                    val errorDescription = when (dataState.cause) {
                        is ApiException.HttpError -> "Characters not found. Code: ${dataState.cause.statusCode}"
                        else -> dataState.cause.errorMsg
                    }
                    appendToMessageQueue(
                        uiComponent = UIComponent.Dialog(
                            title = "Error",
                            description = errorDescription
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