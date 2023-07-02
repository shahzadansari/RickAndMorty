package com.example.character_datasource.cache

import com.example.character_domain.Character
import com.squareup.sqldelight.db.SqlDriver

interface CharactersCache {

    suspend fun getAllCharacters(): List<Character>

    suspend fun insertCharacter(character: Character)

    suspend fun insertCharacters(characters: List<Character>)

    suspend fun getCharacter(id: Int): Character?

    companion object Factory {
        val schema = CharactersDatabase.Schema
        const val dbName = "characters.db"

        fun build(sqlDriver: SqlDriver): CharactersCache {
            return CharactersCacheImpl(CharactersDatabase(sqlDriver))
        }
    }

}