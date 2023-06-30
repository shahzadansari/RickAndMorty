package com.example.ui_character_list.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.components.GenericDialog
import com.example.core.UIComponent
import com.example.core.UIComponentState
import com.example.ui_character_list.components.CharacterListItem

@Composable
fun CharactersList(
    state: CharactersListState,
    navigateToDetailScreen: (characterId: Int) -> Unit,
    onTriggerEvent: (event: CharactersListEvent) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        LazyColumn {
            items(state.characters) { character -> // TODO: Check for empty list
                CharacterListItem(
                    character = character,
                    onCharacterSelected = { navigateToDetailScreen(it) }
                )
            }
        }
        if (state.errorQueue.isNotEmpty()) {
            state.errorQueue.peek()?.let { uiComponent ->
                if (uiComponent is UIComponent.Dialog) {
                    GenericDialog(
                        title = uiComponent.title,
                        description = uiComponent.description,
                        onDismiss = { onTriggerEvent(CharactersListEvent.RemoveHeadFromQueue) }
                    )
                }
            }
        }
    }
}