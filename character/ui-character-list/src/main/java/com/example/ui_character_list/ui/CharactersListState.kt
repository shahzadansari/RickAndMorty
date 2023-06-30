package com.example.ui_character_list.ui

import com.example.core.Queue
import com.example.core.UIComponent
import com.example.core.UIComponentState
import com.example.domain.Character

data class CharactersListState(
    val isLoading: Boolean = false,
    val characters: List<Character> = listOf(),
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf())
)