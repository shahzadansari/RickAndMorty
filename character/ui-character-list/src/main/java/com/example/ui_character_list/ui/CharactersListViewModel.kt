package com.example.ui_character_list.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.DataState
import com.example.core.Queue
import com.example.core.UIComponent
import com.example.interactors.GetAllCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersListViewModel @Inject constructor(
    private val getAllCharacters: GetAllCharacters
) : ViewModel() {

    val state = mutableStateOf(CharactersListState())

    init {
        onTriggerEvent(CharactersListEvent.GetAllCharacters)
    }

    fun onTriggerEvent(event: CharactersListEvent) {
        when (event) {
            is CharactersListEvent.GetAllCharacters -> getAllCharacters()
            is CharactersListEvent.RemoveHeadFromQueue -> removeHeadMessage()
        }
    }

    private fun getAllCharacters() {
        viewModelScope.launch {
            getAllCharacters.execute().collect { dataState ->
                state.value = state.value.copy(isLoading = dataState is DataState.Loading)
                if (dataState is DataState.Success) {
                    state.value = state.value.copy(characters = dataState.data)
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
        val queue = state.value.errorQueue
        queue.add(uiComponent)
        state.value = state.value.copy(errorQueue = Queue(mutableListOf())) // forces recompose
        state.value = state.value.copy(errorQueue = queue)
    }

    private fun removeHeadMessage() {
        try {
            val queue = state.value.errorQueue
            queue.remove() // can throw exception if empty
            state.value = state.value.copy(errorQueue = Queue(mutableListOf())) // forces recompose
            state.value = state.value.copy(errorQueue = queue)
        } catch (e: Exception) {
            println(e.localizedMessage)
        }
    }
}