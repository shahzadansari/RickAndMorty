package com.example.ui_character_list.ui

sealed class CharactersListEvent {
    object GetAllCharacters: CharactersListEvent()
}
