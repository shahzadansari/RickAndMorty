package com.example.ui_character_list.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.character_domain.Character
import com.example.character_domain.CharacterStatus
import com.example.character_domain.Gender
import com.example.character_domain.example
import com.example.components.Previews
import com.example.components.theme.ModularizedRickAndMortyAppTheme

@Stable
data class FilterChipState(
    val label: String,
    var selected: Boolean = true
)

@Composable
fun rememberStatusFilterState() = remember {
    CharacterStatus.entries
        .map { characterStatus ->
            FilterChipState(label = characterStatus.name)
        }
        .toMutableStateList()
}

@Composable
fun rememberGenderFilterState() = remember {
    Gender.entries
        .map { characterStatus ->
            FilterChipState(label = characterStatus.name)
        }
        .toMutableStateList()
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterChipsRow(filterStates: SnapshotStateList<FilterChipState>, modifier: Modifier = Modifier) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filterStates.forEachIndexed { index, chipState ->
            FilterChip(
                selected = chipState.selected,
                onClick = {
                    filterStates[index] = chipState.copy(selected = !chipState.selected)
                },
                label = {
                    Text(chipState.label)
                },
                leadingIcon = {
                    if (chipState.selected) {
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
        val statusFilterStates = rememberStatusFilterState()
        val statusFilters = statusFilterStates.toList().filter { it.selected }.map { it.label }

        val genderFilterStates = rememberGenderFilterState()
        val genderFilters = genderFilterStates.toList().filter { it.selected }.map { it.label }

        val statusFilteredList = characters.filter { character ->
            statusFilters.contains(character.status.name)
        }

        val genderFilteredList = characters.filter { character ->
            genderFilters.contains(character.gender.name)
        }

        val filteredCharacters = characters
            .filter { character ->
                statusFilters.contains(character.status.name)
            }.filter { character ->
                genderFilters.contains(character.gender.name)
            }

        Surface {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FilterChipsRow(filterStates = statusFilterStates)
                FilterChipsRow(filterStates = genderFilterStates)

                Text(text = "Total: ${characters.size}")
                Text(text = "Filtered: ${filteredCharacters.size}")
                Text(text = "Status filtered: ${statusFilteredList.size}")
                Text(text = "Gender filtered: ${genderFilteredList.size}")

                Text(text = "Status filters: $statusFilters")
                Text(text = "Gender filters: $genderFilters")
            }
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