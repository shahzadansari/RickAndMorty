package com.example.ui_character_list.ui

sealed class CharactersListEvent {
    object GetCharacters : CharactersListEvent()
    object RemoveHeadFromQueue : CharactersListEvent()
}