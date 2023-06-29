package com.example.ui_character_list.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.DataState
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
        getAllCharacters()
    }

    private fun getAllCharacters() {
        viewModelScope.launch {
            getAllCharacters.execute().collect { dataState ->
                state.value = state.value.copy(isLoading = dataState is DataState.Loading)
                if (dataState is DataState.Success) {
                    state.value = state.value.copy(characters = dataState.data)
                }
            }
        }
    }
}