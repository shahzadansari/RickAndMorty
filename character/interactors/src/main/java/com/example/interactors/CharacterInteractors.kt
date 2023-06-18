package com.example.interactors

import com.example.datasource.cache.CharactersCache
import com.example.datasource.cache.CharactersDatabase
import com.example.datasource.network.CharactersService
import com.squareup.sqldelight.db.SqlDriver

data class CharacterInteractors(
    val getAllCharacters: GetAllCharacters
) {
    companion object Factory {
        val schema = CharactersCache.schema
        const val dbName = CharactersCache.dbName

        fun build(sqlDriver: SqlDriver): CharacterInteractors {
            val service = CharactersService.build()
            val cache = CharactersCache.build(sqlDriver)

            return CharacterInteractors(
                getAllCharacters = GetAllCharacters(service, cache)
            )
        }
    }
}
