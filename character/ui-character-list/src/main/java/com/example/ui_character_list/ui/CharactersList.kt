package com.example.ui_character_list.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.ui_character_list.components.CharacterListItem

@Composable
fun CharactersList(
    state: CharactersListState,
    navigateToDetailScreen: (characterId: Int) -> Unit
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
    }
}