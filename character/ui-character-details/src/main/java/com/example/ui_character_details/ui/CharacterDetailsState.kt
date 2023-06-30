package com.example.ui_character_details.ui

import com.example.core.Queue
import com.example.core.UIComponent
import com.example.domain.Character

data class CharacterDetailsState(
    val isLoading: Boolean = false,
    val character: Character? = null,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf())
)