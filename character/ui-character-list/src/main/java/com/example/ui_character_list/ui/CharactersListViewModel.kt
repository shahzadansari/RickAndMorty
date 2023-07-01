package com.example.ui_character_list.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.core.DataState
import com.example.core.Queue
import com.example.core.UIComponent
import com.example.interactors.GetCharacters
import com.example.ui_character_list.paging.GetCharactersPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersListViewModel @Inject constructor(
    private val getCharacters: GetCharacters
) : ViewModel() {

    val state = mutableStateOf(CharactersListState())

    val charactersPagingData = Pager(
        config = PagingConfig(20, enablePlaceholders = false),
        pagingSourceFactory = { GetCharactersPagingSource(getCharacters) }
    )
        .flow
        .cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            charactersPagingData.collect()
        }
    }

    fun onTriggerEvent(event: CharactersListEvent) {
        when (event) {
            is CharactersListEvent.GetCharacters -> getCharacters()
            is CharactersListEvent.RemoveHeadFromQueue -> removeHeadMessage()
        }
    }

    private fun getCharacters() {
        viewModelScope.launch {
            getCharacters.execute().collect { dataState ->
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