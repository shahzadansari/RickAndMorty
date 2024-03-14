package com.example.ui_character_list.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.character_domain.Character
import com.example.character_domain.example
import com.example.components.DefaultScreenUI
import com.example.components.Previews
import com.example.components.theme.ModularizedRickAndMortyAppTheme
import com.example.ui_character_list.components.CharacterListItem
import com.example.ui_character_list.ui.components.FilterChipsRow
import com.example.ui_character_list.ui.components.rememberGenderFilterState
import com.example.ui_character_list.ui.components.rememberStatusFilterState
import kotlinx.coroutines.launch

// TODO: 1. Add "No Characters found" state
// TODO: 2. Hide Bottom Sheet if loading characters
// TODO: 3. Extract Screen content
// TODO: 4. Use FlowRow() for filters
// TODO: 5. Add clarifying text in Bottom Sheet content
// TODO: 6. Add Preview states for Expanded and PartiallyExpanded sheet

@SuppressLint("ComposeModifierMissing")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CharactersListScreen(
    state: CharactersListState,
    navigateToDetailScreen: (characterId: Int) -> Unit,
    onTriggerEvent: (event: CharactersListEvent) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val showScrollToTopFAB by remember { derivedStateOf { lazyListState.firstVisibleItemIndex > 3 } }

    val scaffoldState = rememberBottomSheetScaffoldState()
    val statusFilterState = rememberStatusFilterState()
    val genderFilterState = rememberGenderFilterState()

    DefaultScreenUI(
        isLoading = state.isLoading,
        errorQueue = state.errorQueue,
        onRemoveHeadFromQueue = { onTriggerEvent(CharactersListEvent.RemoveHeadFromQueue) },
        onErrorRetry = { onTriggerEvent(CharactersListEvent.GetAllCharacters) }
    ) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
                Column(
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    FilterChipsRow(filterStates = statusFilterState)
                    FilterChipsRow(filterStates = genderFilterState)
                }
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {

                    AnimatedVisibility(
                        visible = state.characters.isNotEmpty(),
                        enter = slideInVertically { -it },
                        exit = slideOutVertically { -it },
                        label = "CharactersListAnimation"
                    ) {
                        val statusFilters = statusFilterState.toList().filter { it.selected.value }.map { it.label }
                        val genderFilters = genderFilterState.toList().filter { it.selected.value }.map { it.label }

                        val filteredCharacters = state.characters
                            .filter { character ->
                                statusFilters.contains(character.status.name)
                            }.filter { character ->
                                genderFilters.contains(character.gender.name)
                            }

                        LazyColumn(
                            modifier = Modifier.padding(vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            state = lazyListState
                        ) {
                            items(
                                items = filteredCharacters,
                                key = { character: Character ->
                                    character.id
                                }
                            ) { character ->
                                CharacterListItem(
                                    character = character,
                                    modifier = Modifier.animateItemPlacement(),
                                    onCharacterSelected = { navigateToDetailScreen(it) }
                                )
                            }
                        }
                    }

                    AnimatedVisibility(
                        visible = showScrollToTopFAB,
                        enter = scaleIn(),
                        exit = scaleOut(),
                        label = "FABAnimation",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(24.dp)
                    ) {
                        FloatingActionButton(
                            onClick = {
                                coroutineScope.launch { lazyListState.animateScrollToItem(0) }
                            },
                            content = {
                                Icon(Icons.Filled.KeyboardArrowUp, "Scroll To Top")
                            }
                        )
                    }
                }
            }
        )
    }
}

@Previews
@Composable
private fun PreviewCharactersListScreen() {
    val characters = buildList {
        repeat(100) {
            add(Character.example.copy(id = it)) // Replacing "id" with current index as LazyColumn key { } requires a unique identifier for each item
        }
    }
    ModularizedRickAndMortyAppTheme {
        CharactersListScreen(state = CharactersListState(characters = characters), navigateToDetailScreen = {}, onTriggerEvent = {})
    }
}