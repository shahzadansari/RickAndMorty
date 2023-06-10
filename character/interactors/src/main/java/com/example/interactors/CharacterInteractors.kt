package com.example.interactors

import com.example.datasource.network.CharacterService

data class CharacterInteractors(
    val getAllCharacters: GetAllCharacters
) {
    companion object Factory {
        fun build(): CharacterInteractors {
            val service = CharacterService.build()
            return CharacterInteractors(
                getAllCharacters = GetAllCharacters(service)
            )
        }
    }
}
