package com.example.ui_character_details.ui

sealed class CharacterDetailsEvent {
    data class GetCharacterFromCache(val id: Int) : CharacterDetailsEvent()
    data object RemoveHeadFromQueue : CharacterDetailsEvent()
}
