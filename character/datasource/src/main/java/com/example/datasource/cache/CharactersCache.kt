package com.example.datasource.cache

import com.example.domain.Character
import com.squareup.sqldelight.db.SqlDriver

interface CharactersCache {

    suspend fun getAllCharacters(): List<Character>

    companion object Factory {
        val schema = CharactersDatabase.Schema
        const val dbName = "characters.db"

        fun build(sqlDriver: SqlDriver): CharactersCache {
            return CharactersCacheImpl(CharactersDatabase(sqlDriver))
        }
    }

}