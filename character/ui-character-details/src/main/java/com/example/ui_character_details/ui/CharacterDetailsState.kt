package com.example.ui_character_details.ui

import com.example.domain.Character

data class CharacterDetailsState(
    val isLoading: Boolean = false,
    val character: Character? = null
)