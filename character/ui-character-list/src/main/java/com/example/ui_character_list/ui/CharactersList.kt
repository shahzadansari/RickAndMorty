package com.example.ui_character_list.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.components.DefaultScreenUI
import com.example.ui_character_list.components.CharacterListItem

@Composable
fun CharactersList(
    state: CharactersListState,
    navigateToDetailScreen: (characterId: Int) -> Unit,
    onTriggerEvent: (event: CharactersListEvent) -> Unit
) {
    DefaultScreenUI(
        isLoading = state.isLoading,
        errorQueue = state.errorQueue,
        onRemoveHeadFromQueue = { onTriggerEvent(CharactersListEvent.RemoveHeadFromQueue) }
    ) {
        LazyColumn {
            items(state.characters) { character ->
                CharacterListItem(
                    character = character,
                    onCharacterSelected = { navigateToDetailScreen(it) }
                )
            }
        }
    }
}