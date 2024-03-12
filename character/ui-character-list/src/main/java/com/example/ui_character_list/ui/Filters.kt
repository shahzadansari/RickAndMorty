package com.example.ui_character_list.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.character_domain.Character
import com.example.character_domain.CharacterStatus
import com.example.character_domain.Gender
import com.example.character_domain.example
import com.example.components.Previews
import com.example.components.theme.ModularizedRickAndMortyAppTheme

data class FilterChipState(
    val label: String,
    val selected: MutableState<Boolean> = mutableStateOf(true),
    val onClick: () -> Unit = { selected.value = !selected.value }
)

@Composable
fun rememberStatusFilterStates() = remember {
    CharacterStatus.entries
        .map { characterStatus ->
            FilterChipState(label = characterStatus.name)
        }
        .toMutableStateList()
}

@Composable
fun rememberGenderFilterStates() = remember {
    Gender.entries
        .map { characterStatus ->
            FilterChipState(label = characterStatus.name)
        }
        .toMutableStateList()
}

@Composable
fun FilterChipsRow(filterStates: SnapshotStateList<FilterChipState>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(filterStates.toList()) { chipState ->
            FilterChip(
                selected = chipState.selected.value,
                onClick = chipState.onClick,
                label = {
                    Text(chipState.label)
                },
                leadingIcon = {
                    if (chipState.selected.value) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                },
                modifier = Modifier.animateContentSize()
            )
        }
    }
}

@Previews
@Composable
private fun FilterChipsRowPreview() {
    ModularizedRickAndMortyAppTheme {
        val statusFilterStates = rememberStatusFilterStates()
        val statusFilters = statusFilterStates.toList()

        val genderFilterStates = rememberGenderFilterStates()
        val genderFilters = genderFilterStates.toList()

        val statusFilteredList = characters
            .filter { character ->
                statusFilters.any { chipState ->
                    chipState.selected.value && chipState.label == character.status.name
                }
            }

        val genderFilteredList = characters
            .filter { character ->
                genderFilters.any { chipState ->
                    chipState.selected.value && chipState.label == character.gender.name
                }
            }

        val filteredCharacters = characters.apply {
            filter { character ->
                statusFilters.any { chipState ->
                    chipState.selected.value && chipState.label == character.status.name
                }
            }
            filter { character ->
                genderFilters.any { chipState ->
                    chipState.selected.value && chipState.label == character.gender.name
                }
            }
        }

        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FilterChipsRow(filterStates = statusFilterStates)
            FilterChipsRow(filterStates = genderFilterStates)

            Text(text = "Total: ${characters.size}")
            Text(text = "Status filtered: ${statusFilteredList.size}")
            Text(text = "Gender filtered: ${genderFilteredList.size}")

            // TODO: Investigate why filters list is not getting updated
            Text(text = "Status filters: ${statusFilterStates.toList().map { it.label }}")
        }
    }
}

private val characters = listOf(
    Character.example.copy(status = CharacterStatus.Alive, gender = Gender.Male),
    Character.example.copy(status = CharacterStatus.Alive, gender = Gender.Female),
    Character.example.copy(status = CharacterStatus.Alive, gender = Gender.Unknown),
    Character.example.copy(status = CharacterStatus.Alive, gender = Gender.Genderless),
    Character.example.copy(status = CharacterStatus.Dead, gender = Gender.Male),
    Character.example.copy(status = CharacterStatus.Dead, gender = Gender.Female),
    Character.example.copy(status = CharacterStatus.Dead, gender = Gender.Unknown),
    Character.example.copy(status = CharacterStatus.Dead, gender = Gender.Genderless),
    Character.example.copy(status = CharacterStatus.Unknown, gender = Gender.Male),
    Character.example.copy(status = CharacterStatus.Unknown, gender = Gender.Female),
    Character.example.copy(status = CharacterStatus.Unknown, gender = Gender.Unknown),
    Character.example.copy(status = CharacterStatus.Unknown, gender = Gender.Genderless),
)