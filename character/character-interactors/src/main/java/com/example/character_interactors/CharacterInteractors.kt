package com.example.character_interactors

import com.example.character_datasource.cache.CharactersCache

data class CharacterInteractors(
    val getCharacters: GetCharacters,
    val getCharacterFromCache: GetCharacterFromCache
) {
    companion object Factory {
        val schema = CharactersCache.schema
        const val dbName = CharactersCache.dbName
    }
}
