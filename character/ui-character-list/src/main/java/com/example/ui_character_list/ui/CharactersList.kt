package com.example.ui_character_list.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import com.example.components.DefaultScreenUI
import com.example.domain.Character
import com.example.ui_character_list.components.CharacterListItem

@Composable
fun CharactersList(
    state: CharactersListState,
    navigateToDetailScreen: (characterId: Int) -> Unit,
    onTriggerEvent: (event: CharactersListEvent) -> Unit,
    paginatedCharacters: LazyPagingItems<Character>
) {
    DefaultScreenUI(
        isLoading = state.isLoading,
        errorQueue = state.errorQueue,
        onRemoveHeadFromQueue = { onTriggerEvent(CharactersListEvent.RemoveHeadFromQueue) }
    ) {
        LazyColumn {
            items(paginatedCharacters.itemSnapshotList.items) { character ->
                CharacterListItem(
                    character = character,
                    onCharacterSelected = { navigateToDetailScreen(it) }
                )
            }
        }
    }
}