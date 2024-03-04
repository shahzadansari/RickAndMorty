package com.example.character_interactors

import com.example.character_datasource.cache.CharactersLocal

data class CharacterInteractors(
    val getCharacters: GetCharacters,
    val getCharacterFromCache: GetCharacterFromCache
) {
    companion object Factory {
        val schema = CharactersLocal.schema
        const val dbName = CharactersLocal.dbName
    }
}
