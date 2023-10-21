package com.example.ui_character_list.ui

sealed class CharactersListEvent {
    data object GetAllCharacters : CharactersListEvent()
    data object RemoveHeadFromQueue : CharactersListEvent()
}