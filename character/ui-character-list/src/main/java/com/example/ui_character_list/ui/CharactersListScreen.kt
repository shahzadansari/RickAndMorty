package com.example.ui_character_list.ui

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.material3.Surface
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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.character_domain.Character
import com.example.character_domain.CharacterStatus
import com.example.character_domain.example
import com.example.components.DefaultScreenUI
import com.example.components.Previews
import com.example.components.isInPreview
import com.example.components.mapIf
import com.example.components.theme.ModularizedRickAndMortyAppTheme
import com.example.ui_character_list.components.CharacterListItem
import com.example.ui_character_list.ui.components.FilterChipState
import com.example.ui_character_list.ui.components.FilterChipsRow
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

@SuppressLint("ComposeModifierMissing")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersListScreen(
    state: CharactersListState,
    navigateToDetailScreen: (characterId: Int) -> Unit,
    onTriggerEvent: (event: CharactersListEvent) -> Unit
) {
    val characters = state.characters.toImmutableList()
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val showScrollToTopFAB by remember { derivedStateOf { lazyListState.firstVisibleItemIndex > 3 } }

    val scaffoldState = rememberBottomSheetScaffoldState()
    var enableBottomSheet by rememberSaveable { mutableStateOf(false) }
    val animatedSheetPeekHeight by animateDpAsState(
        targetValue = if (enableBottomSheet) BottomSheetDefaults.SheetPeekHeight else 0.dp,
        label = "SheetPeekHeightAnimation"
    )

    LaunchedEffect(state) {
        if (!state.isLoading && state.unfilteredCharacters.isNotEmpty()) {
            enableBottomSheet = true
        }
    }

    val statusFilters = remember(state) { state.statusFilters }
    val genderFilters = remember(state) { state.genderFilters }

    // Observes Chip selected state changes
    LaunchedEffect(
        key1 = statusFilters.toList().map { it.selected },
        key2 = genderFilters.toList().map { it.selected },
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
                CharactersFiltersBottomSheet(statusFilters = statusFilters, genderFilters = genderFilters)
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    AnimatedVisibility(
                        visible = characters.isNotEmpty(),
                        enter = slideInVertically(),
                        exit = slideOutVertically()
                    ) {
                        CharactersList(
                            lazyListStateProvider = { lazyListState },
                            characters = characters,
                            onCharacterSelected = { navigateToDetailScreen(it) }
                        )
                    }

                    AnimatedVisibility(
                        visible = !state.isLoading && characters.isEmpty(),
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
                        ScrollToTopFAB(onClick = { coroutineScope.launch { lazyListState.animateScrollToItem(0) } })
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CharactersList(characters: ImmutableList<Character>, lazyListStateProvider: () -> LazyListState, onCharacterSelected: (characterId: Int) -> Unit) {
    LazyColumn(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = lazyListStateProvider()
    ) {
        items(
            items = characters,
            key = { character: Character ->
                character.id
            }
        ) { character ->
            CharacterListItem(
                character = character,
                onCharacterSelected = onCharacterSelected,
                modifier = Modifier.mapIf(!isInPreview) {
                    // Disabled for Previews as it crashes Preview with "java.lang.IllegalArgumentException: place is called on a deactivated node"
                    animateItemPlacement()
                }
            )
        }
    }
}

@Composable
private fun ScrollToTopFAB(modifier: Modifier = Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        content = {
            Icon(Icons.Filled.KeyboardArrowUp, "Scroll To Top")
        }
    )
}

@Composable
private fun CharactersFiltersBottomSheet(statusFilters: SnapshotStateList<FilterChipState>, genderFilters: SnapshotStateList<FilterChipState>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(start = 24.dp, top = 8.dp, end = 24.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Filter by Status:", style = MaterialTheme.typography.titleLarge)
        FilterChipsRow(filterStates = statusFilters)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Filter by Gender:", style = MaterialTheme.typography.titleLarge)
        FilterChipsRow(filterStates = genderFilters)
    }
}

@Previews
@Composable
private fun CharactersListScreenPreview() {
    var state by remember { mutableStateOf(CharactersListState(unfilteredCharacters = sampleCharacters)) }

    ModularizedRickAndMortyAppTheme {
        CharactersListScreen(
            state = state,
            navigateToDetailScreen = {},
            onTriggerEvent = { event ->
                when (event) {
                    CharactersListEvent.FilterCharacters -> state = state.copy(characters = state.filteredCharacters)
                    else -> {}
                }
            }
        )
    }
}

@Previews
@Composable
private fun CharactersFiltersBottomSheetPreview() {
    val state = remember { CharactersListState() }
    ModularizedRickAndMortyAppTheme {
        // Needs to be wrapped in Surface to apply proper styling i.e. correct color to text in different UI modes
        Surface(modifier = Modifier.fillMaxWidth()) {
            CharactersFiltersBottomSheet(statusFilters = state.statusFilters, genderFilters = state.genderFilters)
        }
    }
}

/** List of characters with their names and status set to Alive, Dead, Unknown for successive items */
private val sampleCharacters = buildList {
    repeat(100) { index ->
        val status = when (index % 3) {
            1 -> CharacterStatus.Dead
            2 -> CharacterStatus.Unknown
            else -> CharacterStatus.Alive
        }
        val character = Character.example.copy(
            id = index, // Replacing "id" with current index as LazyColumn key { } requires a unique identifier for each item
            name = status.name,
            status = status
        )
        add(character)
    }
}.toImmutableList()