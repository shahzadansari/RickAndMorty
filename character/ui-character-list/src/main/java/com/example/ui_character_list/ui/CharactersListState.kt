package com.example.ui_character_list.ui

import com.example.domain.Character

data class CharactersListState(
    val isLoading: Boolean = false,
    val characters: List<Character> = listOf()
)