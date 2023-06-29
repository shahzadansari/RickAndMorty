package com.example.ui_character_details.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CharacterDetails(characterId: Int?) {
    Text(text = "Character Id: $characterId")
}