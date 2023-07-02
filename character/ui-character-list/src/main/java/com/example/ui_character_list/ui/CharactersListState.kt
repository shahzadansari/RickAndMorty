package com.example.ui_character_list.ui

import com.example.character_domain.Character
import com.example.core.Queue
import com.example.core.UIComponent

data class CharactersListState(
    val isLoading: Boolean = false,
    val characters: List<Character> = listOf(),
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf())
)