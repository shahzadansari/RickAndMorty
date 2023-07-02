package com.example.character_interactors

import com.example.character_datasource.cache.CharactersCache
import com.example.character_datasource.network.CharactersService
import com.squareup.sqldelight.db.SqlDriver

data class CharacterInteractors(
    val getCharacters: GetCharacters,
    val getCharacterFromCache: GetCharacterFromCache
) {
    companion object Factory {
        val schema = CharactersCache.schema
        const val dbName = CharactersCache.dbName

        fun build(sqlDriver: SqlDriver): CharacterInteractors {
            val service = CharactersService.build()
            val cache = CharactersCache.build(sqlDriver)

            return CharacterInteractors(
                getCharacters = GetCharacters(service, cache),
                getCharacterFromCache = GetCharacterFromCache(cache)
            )
        }
    }
}
