package com.example.ui_character_list.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.snap
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
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.launch

// TODO: 1. Add "No Characters found" state ✅
// TODO: 2. Hide Bottom Sheet if loading characters ✅
// TODO: 3. Extract Screen content
// TODO: 4. Use FlowRow() for filters
// TODO: 5. Add clarifying text in Bottom Sheet content
// TODO: 6. Add Preview states for Expanded and PartiallyExpanded sheet
// TODO: 7. Filter characters in ViewModel to adhere to Single Source-Of-Truth principle ✅

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

    var enableBottomSheet by rememberSaveable { mutableStateOf(false) }
    val animatedSheetPeekHeight by animateDpAsState(
        targetValue = if (enableBottomSheet) {
            BottomSheetDefaults.SheetPeekHeight
        } else {
            0.dp
        },
        label = "SheetPeekHeightAnimation"
    )

    LaunchedEffect(state) {
        if (!state.isLoading && state.unfilteredCharacters.isNotEmpty()) {
            enableBottomSheet = true
        }
    }

    val statusFilters = remember(state) { state.statusFilters }
    val genderFilters = remember(state) { state.genderFilters }

    LaunchedEffect(
        key1 = statusFilters.toList().map { it.selected.value },
        key2 = genderFilters.toList().map { it.selected.value },
    ) {
        onTriggerEvent(CharactersListEvent.FilterCharacters)
    }

    DefaultScreenUI(
        isLoading = state.isLoading,
        errorQueue = state.errorQueue,
        onRemoveHeadFromQueue = { onTriggerEvent(CharactersListEvent.RemoveHeadFromQueue) },
        onErrorRetry = { onTriggerEvent(CharactersListEvent.GetAllCharacters) }
    ) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = animatedSheetPeekHeight,
            sheetContent = {
                Column(
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    FilterChipsRow(filterStates = statusFilters)
                    FilterChipsRow(filterStates = genderFilters)
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
                        enter = slideInVertically(),
                        exit = slideOutVertically()
                    ) {
                        LazyColumn(
                            modifier = Modifier.padding(vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            state = lazyListState
                        ) {
                            items(
                                items = state.characters,
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
                        visible = !state.isLoading && state.characters.isEmpty(),
                        modifier = Modifier.align(Alignment.Center),
                        enter = scaleIn(),
                        exit = scaleOut(animationSpec = snap())
                    ) {
                        Text(text = "No characters found.", style = MaterialTheme.typography.bodyLarge)
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
        CharactersListScreen(state = CharactersListState(unfilteredCharacters = characters), navigateToDetailScreen = {}, onTriggerEvent = {})
    }
}